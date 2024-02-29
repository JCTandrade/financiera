package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.service.ITipoTransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "tipoTransaccion", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoTransaccionController {
    @Autowired
    ITipoTransaccionService tipoTransaccionService;

    @GetMapping(path = "obtener-tipo-transaccion")
    public ResponseEntity<ResponseDto>obtenerTipoTransaccion() {
        var service = tipoTransaccionService.obtenerListaTipoTransacion();
        return new ResponseEntity<>(service, service.getStatus());
    }
}
