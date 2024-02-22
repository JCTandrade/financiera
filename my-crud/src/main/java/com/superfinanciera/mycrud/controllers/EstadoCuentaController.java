package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.service.IEstadoCuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "estadoCuenta", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoCuentaController {

    @Autowired
    IEstadoCuentaService estadoCuentaService;
}
