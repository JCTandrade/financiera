package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.dto.ClienteRegistradoDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.model.Clientes;
import com.superfinanciera.mycrud.repositories.ClientesRepository;
import com.superfinanciera.mycrud.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    ClientesRepository clientesRepository;

    @Override
    public ResponseDto resgistrarCliente(ClienteRegistradoDto clienteRegistradoDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(true);
        responseDto.setStatus(HttpStatus.OK);
        try {
            Clientes validaTipoIdentificacion = this.clientesRepository.findByTipoIdentificacionClienteAndNumeroIndetificacionCliente(clienteRegistradoDto.getTipoIdentificacionCliente(), clienteRegistradoDto.getNumeroIndetificacionCliente());
            if (Objects.isNull(validaTipoIdentificacion)) {
                validaTipoIdentificacion = this.crearObjetoCliente(clienteRegistradoDto);
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

    private Clientes crearObjetoCliente(ClienteRegistradoDto clienteRegistradoDto) {
        var validaTipoIdentificacion = new Clientes();
        validaTipoIdentificacion.setNombreCliente(clienteRegistradoDto.getNombreCliente());
        validaTipoIdentificacion.setApellidoCliente(clienteRegistradoDto.getApellidoCliente());
        validaTipoIdentificacion.setTipoIdentificacionCliente(clienteRegistradoDto.getTipoIdentificacionCliente());
        validaTipoIdentificacion.setNumeroIndetificacionCliente(clienteRegistradoDto.getNumeroIndetificacionCliente());
        validaTipoIdentificacion.setCorreoCliente(clienteRegistradoDto.getCorreoCliente());
        validaTipoIdentificacion.setCreatedAt(new Date());
        validaTipoIdentificacion.setFechaNacimientoCliente(clienteRegistradoDto.getFechaNacimientoCliente());

        return validaTipoIdentificacion;
    }
}
