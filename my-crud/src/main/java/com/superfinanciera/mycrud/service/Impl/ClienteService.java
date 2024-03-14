package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.dto.ClienteRegistradoDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.model.Clientes;
import com.superfinanciera.mycrud.repositories.ClientesRepository;
import com.superfinanciera.mycrud.repositories.CuentaRepository;
import com.superfinanciera.mycrud.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    CuentaRepository cuentaRepository;

    @Override
    public ResponseDto resgistrarCliente(ClienteRegistradoDto clienteRegistradoDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(true);
        responseDto.setStatus(HttpStatus.OK);
        if (clienteRegistradoDto.getNombreCliente().length() < 2 || clienteRegistradoDto.getApellidoCliente().length() < 2) {
            responseDto.setError(true);
            responseDto.setMensaje("Nombre o apellido no cumple con el numero de caracteres especificos");
            return responseDto;
        }
        try {
            Clientes validaTipoIdentificacion = this.clientesRepository.findByTipoIdentificacionClienteAndNumeroIndetificacionCliente(clienteRegistradoDto.getTipoIdentificacionCliente(), clienteRegistradoDto.getNumeroIndetificacionCliente());
            if (Objects.isNull(validaTipoIdentificacion)) {
                validaTipoIdentificacion = this.crearObjetoCliente(clienteRegistradoDto, true);
                LocalDate fechaActual = LocalDate.now();
                Period periodo = Period.between(clienteRegistradoDto.getFechaNacimientoCliente(), fechaActual);
                int edad = periodo.getYears();
                if (edad < 18) {
                    responseDto.setError(true);
                    responseDto.setMensaje("El cliente debe de ser mayor de edad");
                    return responseDto;
                }
                var data = this.clientesRepository.save(validaTipoIdentificacion);
                responseDto.setMensaje(data);
                return responseDto;
            }else {
                responseDto.setError(false);
                responseDto.setMensaje("La informacion suministrada ya existe");
            }
        }catch (Exception error) {
            responseDto.setMensaje(error.getMessage());
        }
        return responseDto;
    }

    private Clientes crearObjetoCliente(ClienteRegistradoDto clienteRegistradoDto, boolean isCreated) {
        var validaTipoIdentificacion = new Clientes();
        validaTipoIdentificacion.setNombreCliente(clienteRegistradoDto.getNombreCliente());
        validaTipoIdentificacion.setApellidoCliente(clienteRegistradoDto.getApellidoCliente());
        validaTipoIdentificacion.setTipoIdentificacionCliente(clienteRegistradoDto.getTipoIdentificacionCliente());
        validaTipoIdentificacion.setNumeroIndetificacionCliente(clienteRegistradoDto.getNumeroIndetificacionCliente());
        validaTipoIdentificacion.setCorreoCliente(clienteRegistradoDto.getCorreoCliente());
        if (isCreated) {
            validaTipoIdentificacion.setCreatedAt(new Date());
        } else {
            validaTipoIdentificacion.setUpdatedAt(new Date());
        }
        validaTipoIdentificacion.setFechaNacimientoCliente(clienteRegistradoDto.getFechaNacimientoCliente());
        validaTipoIdentificacion.setEliminado(  false);
        return validaTipoIdentificacion;
    }

    @Override
    public ResponseDto actualizarCliente(ClienteRegistradoDto clienteRegistradoDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(true);
        responseDto.setStatus(HttpStatus.OK);
        if (clienteRegistradoDto.getNombreCliente().length() < 2 || clienteRegistradoDto.getApellidoCliente().length() < 2) {
            responseDto.setError(true);
            responseDto.setMensaje("Nombre o apellido no cumple con el numero de caracteres especificos");
            return responseDto;
        }
        var cliente = this.clientesRepository.findById(clienteRegistradoDto.getIdCliente());
        if (cliente.isPresent()) {
            var actualizarCliente = cliente.get();
            actualizarCliente = this.crearObjetoCliente(clienteRegistradoDto, false);
            actualizarCliente.setIdCliente(clienteRegistradoDto.getIdCliente());
            this.clientesRepository.save(actualizarCliente);
            responseDto.setMensaje("Actualizacion realizada de manera correcta");
            return responseDto;
        }
        return this.resgistrarCliente(clienteRegistradoDto);
    }

    @Override
    public ResponseDto eliminarCliente(Long id) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(false);
        responseDto.setStatus(HttpStatus.OK);
        try {
            boolean tieneProductos = cuentaRepository.existsByClienteId(id);
            if (tieneProductos) {
                responseDto.setError(true);
                responseDto.setStatus(HttpStatus.BAD_REQUEST);
                responseDto.setMensaje("El cliente no puede ser eliminado porque tiene productos vinculados");
            } else{
                clientesRepository.deleteById(id);
                responseDto.setMensaje("Cliente eliminado correctamente");
            }
        } catch (Exception e) {
            responseDto.setMensaje(e.getMessage());
        }
        return responseDto;
    }

    @Override
    public ResponseDto buscarClienteId(Long id) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(false);
        responseDto.setStatus(HttpStatus.OK);
        Optional<Clientes> clientes = this.clientesRepository.findById(id);
        if (clientes.isPresent()) {
            responseDto.setMensaje(clientes.get());
        }else {
            responseDto.setError(true);
            responseDto.setMensaje("No existe un cliente con el ID: " + id);
        }
        return responseDto;
    }
}
