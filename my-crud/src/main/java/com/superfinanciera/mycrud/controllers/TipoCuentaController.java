package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.service.ITipoCuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "tipoCuenta", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoCuentaController {

    @Autowired
    ITipoCuentaService tipoCuentaService;

    @GetMapping(path = "obtener-tipo-cuenta")
    public ResponseEntity<ResponseDto>obtenerTipoCuenta() {
        var service = tipoCuentaService.obtenerListaTipoCuenta();
        return new ResponseEntity<>(service, service.getStatus());
    }
}
