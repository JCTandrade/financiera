package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.model.TipoCuenta;
import com.superfinanciera.mycrud.repositories.TipoCuentaRepository;
import com.superfinanciera.mycrud.service.ITipoCuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoCuentaService implements ITipoCuentaService {

    @Autowired
    TipoCuentaRepository tipoCuentaRepository;

    @Override
    public ResponseDto obtenerListaTipoCuenta() {
        List<TipoCuenta>obtenerTipoCuenta = tipoCuentaRepository.findAll();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(true);
        responseDto.setStatus(HttpStatus.OK);
        if (obtenerTipoCuenta.isEmpty()) {
            responseDto.setMensaje("No existen tipos cuentas disponibles");
        }else {
            responseDto.setMensaje(obtenerTipoCuenta);
        }
        return responseDto;
    }
}
