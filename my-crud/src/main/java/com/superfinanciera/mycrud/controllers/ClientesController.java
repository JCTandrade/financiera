package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.ClienteRegistradoDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientesController {

    @Autowired
    IClienteService clienteService;

    @PostMapping(path = "registrar-cliente")
    public ResponseEntity<ResponseDto>resgistrarCliente(@Valid @RequestBody ClienteRegistradoDto clienteRegistradoDto) {
        var servico = this.clienteService.resgistrarCliente(clienteRegistradoDto);
        return new ResponseEntity<>(servico, servico.getStatus());
    }
}
