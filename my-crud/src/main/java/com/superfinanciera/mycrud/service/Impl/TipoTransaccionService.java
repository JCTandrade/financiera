package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.model.TipoTransaccion;
import com.superfinanciera.mycrud.repositories.TipoTransaccionRespository;
import com.superfinanciera.mycrud.service.ITipoTransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoTransaccionService implements ITipoTransaccionService {

    @Autowired
    TipoTransaccionRespository tipoTransaccionRespository;

    @Override
    public ResponseDto obtenerListaTipoTransacion() {
        List<TipoTransaccion>obtenerTipoTransaccion = tipoTransaccionRespository.findAll();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(true);
        responseDto.setStatus(HttpStatus.OK);
        if (obtenerTipoTransaccion.isEmpty()) {
            responseDto.setMensaje("No existen tipos de transacciones");
        } else {
            responseDto.setMensaje(obtenerTipoTransaccion);
        }
        return responseDto;
    }

    @Override
    public TipoTransaccion buscarPorId(Long id) throws Exception {
        var tipoTransaccion = tipoTransaccionRespository.findById(id);
        if (tipoTransaccion.isPresent()) {
            return tipoTransaccion.get();
        }
        throw new Exception("El id del tipo transaccion no existe");
    }
}
