package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.ClienteRegistradoDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientesController {

    @Autowired
    IClienteService clienteService;

    @PostMapping(path = "registrar-cliente")
    public ResponseEntity<ResponseDto>resgistrarCliente(@Valid  @RequestBody ClienteRegistradoDto clienteRegistradoDto) {
        var servicio = this.clienteService.resgistrarCliente(clienteRegistradoDto);
        return new ResponseEntity<>(servicio, servicio.getStatus());
    }

    @PostMapping(path = "actualizar-cliente")
    public ResponseEntity<ResponseDto>actualizarCliente(@Valid @RequestBody ClienteRegistradoDto clienteRegistradoDto) {
        var servicio = this.clienteService.actualizarCliente(clienteRegistradoDto);
        return new ResponseEntity<>(servicio, servicio.getStatus());
    }

    @GetMapping(path = {"buscarCliente","buscarCliente/{id}"})
    public ResponseEntity<ResponseDto>buscarCliente(@PathVariable(required = false) Long id) {
        ResponseDto responseDto = new ResponseDto();
        if (id == null) {
            responseDto.setMensaje("ID no ingresado");
            responseDto.setError(true);
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
        var servicio = this.clienteService.buscarClienteId(id);
        return new ResponseEntity<>(servicio, servicio.getStatus());
    }

    @GetMapping(path = "eliminar-cliente/{id}")
    public ResponseEntity<ResponseDto>eliminarCliente(@PathVariable Long id) {
        var servicio = this.clienteService.eliminarCliente(id);
        return new ResponseEntity<>(servicio, servicio.getStatus());
    }
}
