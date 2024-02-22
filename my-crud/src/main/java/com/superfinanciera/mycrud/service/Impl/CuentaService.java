package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.dto.CuentaRegistradaDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.model.Clientes;
import com.superfinanciera.mycrud.model.Cuenta;
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
            }else {
                var crearCuenta = this.crearTipoCuenta(cuentaRegistradaDto,modelMapper.map(cliente.getMensaje(),Clientes.class));
                if (crearCuenta){
                    responseDto.setError(false);
                    responseDto.setMensaje("La cuenta se ha registrado correctamente");
                }else {
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
        if (cuentaRegistradaDto.getSaldo() >= 0) {
            var validarTipoCuenta = new Cuenta();
            validarTipoCuenta.setTipoCuenta(cuentaRegistradaDto.getTipoCuenta());
            validarTipoCuenta.setNumeroCuenta(cuentaRegistradaDto.getTipoCuenta() + this.generarNumeroCuenta(cuentaRegistradaDto.getTipoCuenta()));
            validarTipoCuenta.setEstadoCuenta(this.estadoCuentaService.buscarPorId(Constant.EstadoCuenta.ID_ACTIVA));
            validarTipoCuenta.setSaldo(String.valueOf(cuentaRegistradaDto.getSaldo()));
            validarTipoCuenta.setExentaGMF(cuentaRegistradaDto.isExentaGMF());
            validarTipoCuenta.setCreatedAt(new Date());
            validarTipoCuenta.setClientes(clientes);
            var cuentaCreada = this.cuentaRepository.save(validarTipoCuenta);
            if (cuentaCreada.getIdCuenta() != null){
                isCreada = true;
            }
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
        return  builder.toString();
    }
}
