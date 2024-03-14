package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.CuentaRegistradaDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.dto.TransaccionDto;
import com.superfinanciera.mycrud.repositories.EstadoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoTransaccionRespository;
import com.superfinanciera.mycrud.service.Impl.TransaccionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(TransaccionController.class)
class TransaccionControllerTest {

    @InjectMocks
    TransaccionController transaccionController;

    @MockBean
    TransaccionService transaccionService;

    @MockBean
    TipoCuentaRepository tipoCuentaRepository;

    @MockBean
    EstadoCuentaRepository estadoCuentaRepository;

    @MockBean
    TipoTransaccionRespository tipoTransaccionRespository;

    TransaccionDto transaccionDto;

    ResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transaccionDto = new TransaccionDto();
        transaccionDto.setCuentaOrigen(1L);
        transaccionDto.setTipoTransaccion(3L);

        responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK);
        responseDto.isError();
    }

    @Test
    void testRealizarTransaccionOk() {
        transaccionDto.setCuentaOrigen(1l);
        transaccionDto.setCuentaDestino(2L);
        transaccionDto.setMonto(100.0);

        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMensaje("Transaccion realizada exitosamente");

        Mockito.when(transaccionService.realizarTansaccion(transaccionDto)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> responseEntity = transaccionController.realizarTansaccion(transaccionDto);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Transaccion realizada exitosamente", responseEntity.getBody().getMensaje());
    }
}