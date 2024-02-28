package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.CuentaRegistradaDto;
import com.superfinanciera.mycrud.dto.EstadoCuentaDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.service.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "cuenta", produces = MediaType.APPLICATION_JSON_VALUE)
public class CuentaController {

    @Autowired
    ICuentaService cuentaService;

    @PostMapping(path = "registrar-cuenta")
    public ResponseEntity<ResponseDto>registarCuenta(@Valid @RequestBody CuentaRegistradaDto cuentaRegistradaDto) {
        var service = this.cuentaService.resgistrarCuenta(cuentaRegistradaDto);
        return new ResponseEntity<>(service, service.getStatus());
    }

    @PostMapping(path = "actualizar-cuenta")
    public ResponseEntity<ResponseDto>actualizarCliente(@Valid @RequestBody EstadoCuentaDto estadoCuentaDto) throws Exception {
        var service = this.cuentaService.actualizarCuenta(estadoCuentaDto);
        return new ResponseEntity<>(service, service.getStatus());
    }

    @GetMapping(path = "buscarCuenta/{id}")
    public ResponseEntity<ResponseDto>buscarCliente(@PathVariable Long id) {
        var servicio = this.cuentaService.buscarCuentaId(id);
        return new ResponseEntity<>(servicio, servicio.getStatus());
    }
}
