package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.dto.TransaccionDto;
import com.superfinanciera.mycrud.service.ITransaccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value =  "transaccion", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransaccionController {

    @Autowired
    ITransaccionService transaccionService;

    @PostMapping(path = "realizar-transaccion")
    public ResponseEntity<ResponseDto>realizarTansaccion(@Valid @RequestBody TransaccionDto transaccionDto) {
        var service = this.transaccionService.realizarTansaccion(transaccionDto);
        return new ResponseEntity<>(service, service.getStatus());
    }
}
