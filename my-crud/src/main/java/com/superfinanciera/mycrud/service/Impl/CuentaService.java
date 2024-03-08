package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.dto.CuentaRegistradaDto;
import com.superfinanciera.mycrud.dto.EstadoCuentaDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.model.Clientes;
import com.superfinanciera.mycrud.model.Cuenta;
import com.superfinanciera.mycrud.model.EstadoCuenta;
import com.superfinanciera.mycrud.repositories.CuentaRepository;
import com.superfinanciera.mycrud.service.IClienteService;
import com.superfinanciera.mycrud.service.ICuentaService;
import com.superfinanciera.mycrud.service.IEstadoCuentaService;
import com.superfinanciera.mycrud.utils.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class CuentaService implements ICuentaService {

    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    IClienteService clienteService;

    @Autowired
    IEstadoCuentaService estadoCuentaService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseDto resgistrarCuenta(CuentaRegistradaDto cuentaRegistradaDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(true);
        responseDto.setStatus(HttpStatus.OK);
        try {
            var cliente = clienteService.buscarClienteId(cuentaRegistradaDto.getIdCliente());
            if (cliente.isError()) {
                responseDto.setMensaje("El cliente con el Id no existe");
            } else {
                var crearCuenta = this.crearTipoCuenta(cuentaRegistradaDto, modelMapper.map(cliente.getMensaje(), Clientes.class));
                if (crearCuenta) {
                    responseDto.setError(false);
                    responseDto.setMensaje("La cuenta se ha registrado correctamente");
                } else {
                    responseDto.setError(true);
                    responseDto.setMensaje("La cuenta no se puedo crear correctamente");
                }
            }
        } catch (Exception e) {
            responseDto.setError(true);
            responseDto.setMensaje("Error al registrar la cuenta: " + e.getMessage());
        }
        return responseDto;
    }

    private boolean crearTipoCuenta(CuentaRegistradaDto cuentaRegistradaDto, Clientes clientes) throws Exception {
        boolean isCreada = false;
        var validarTipoCuenta = new Cuenta();
        validarTipoCuenta.setTipoCuenta(cuentaRegistradaDto.getTipoCuenta());
        validarTipoCuenta.setNumeroCuenta(cuentaRegistradaDto.getTipoCuenta() + this.generarNumeroCuenta(cuentaRegistradaDto.getTipoCuenta()));
        validarTipoCuenta.setEstadoCuenta(this.estadoCuentaService.buscarPorId(Constant.EstadoCuenta.ID_ACTIVA));
        validarTipoCuenta.setSaldo(0.0);
        validarTipoCuenta.setExentaGMF(cuentaRegistradaDto.getExentaGMF());
        validarTipoCuenta.setCreatedAt(new Date());
        validarTipoCuenta.setClientes(clientes);
        var cuentaCreada = this.cuentaRepository.save(validarTipoCuenta);
        if (cuentaCreada.getIdCuenta() != null) {
            isCreada = true;
        }
        return isCreada;
    }

    private String generarNumeroCuenta(String tipoCuenta) throws Exception {
        if (tipoCuenta == null) {
            throw new Exception("El tipo de cuenta no puede ser vacio");
        }
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append(random.nextInt(8));
        }
        return builder.toString();
    }

    @Override
    public ResponseDto actualizarCuenta(EstadoCuentaDto estadoCuentaDto) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(true);
        responseDto.setStatus(HttpStatus.OK);
        try {
            responseDto.setMensaje(this.validarCuenta(estadoCuentaDto));
        } catch (Exception e) {
            responseDto.setMensaje(e.getMessage());
        }
        return responseDto;
    }


    private Cuenta validarCuenta(EstadoCuentaDto estadoCuentaDto) throws Exception {
        var getCuenta = this.cuentaRepository.findById(estadoCuentaDto.getIdCuenta());
        if (getCuenta.isEmpty()) {
            throw new Exception("No se puede actualizar la cuenta por que no existe");
        }
        var estado = this.estadoCuentaService.buscarPorId(estadoCuentaDto.getEstadoCuenta());
        if (Objects.isNull(estado)) {
            throw new Exception("No se puede actualizar la cuenta por que el estado asignado no existe");
        }

       return this.validarEstado(getCuenta.get(), estado, estadoCuentaDto);
    }

    private Cuenta validarEstado(Cuenta cuenta, EstadoCuenta estadoCuenta, EstadoCuentaDto estadoCuentaDto) throws Exception {
        var saldo = cuenta.getSaldo();
        var estado = estadoCuenta.getNombre();
        if (estado.equals(Constant.EstadoCuenta.ESTADO_CUENTA_ACTIVA) &&
                cuenta.getEstadoCuenta().getNombre().equals(Constant.EstadoCuenta.ESTADO_CUENTA_INACTIVA)) {
            cuenta.setExentaGMF(estadoCuentaDto.getExentaGMF());
            cuenta.setUpdatedAt(new Date());
            cuenta.setEstadoCuenta(estadoCuenta);
        } else if (estado.equals(Constant.EstadoCuenta.ESTADO_CUENTA_INACTIVA) ||
                estado.equals(Constant.EstadoCuenta.ESTADO_CUENTA_CANCELADA) && saldo.equals(0.0)) {
            cuenta.setExentaGMF(estadoCuentaDto.getExentaGMF());
            cuenta.setUpdatedAt(new Date());
            cuenta.setEstadoCuenta(estadoCuenta);
        } else if (estado.equals(Constant.EstadoCuenta.ESTADO_CUENTA_ACTIVA)) {
            cuenta.setExentaGMF(estadoCuentaDto.getExentaGMF());
            cuenta.setUpdatedAt(new Date());
            cuenta.setEstadoCuenta(estadoCuenta);
        }
        return this.cuentaRepository.save(cuenta);
    }

    @Override
    public ResponseDto buscarCuentaId(Long id) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(false);
        responseDto.setStatus(HttpStatus.OK);
        Optional<Cuenta> cuenta = this.cuentaRepository.findById(id);
        if (cuenta.isPresent()) {
            responseDto.setMensaje(cuenta.get());
        } else {
            responseDto.setError(true);
            responseDto.setMensaje("No existe una cuenta con el ID: " + id);
        }
        return responseDto;
    }
}
