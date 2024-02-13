package com.superfinanciera.mycrud.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/Health", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthControllers {

    @GetMapping
    public ResponseEntity<String> listarCliente(){
        return new ResponseEntity<>("Aplicacion corriendo", HttpStatus.OK);
    }
}
