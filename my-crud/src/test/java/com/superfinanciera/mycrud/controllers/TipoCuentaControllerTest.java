package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.repositories.EstadoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoTransaccionRespository;
import com.superfinanciera.mycrud.service.Impl.TipoCuentaService;
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

@WebMvcTest(TipoCuentaController.class)
class TipoCuentaControllerTest {

    @InjectMocks TipoCuentaController tipoCuentaController;

    @MockBean
    TipoCuentaService tipoCuentaService;

    @MockBean
    TransaccionService transaccionService;

    @MockBean
    TipoCuentaRepository tipoCuentaRepository;

    @MockBean
    EstadoCuentaRepository estadoCuentaRepository;

    @MockBean
    TipoTransaccionRespository tipoTransaccionRespository;

    ResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK);
    }

    @Test
    void testObtenerTipoCuenta() {
        Mockito.when(tipoCuentaService.obtenerListaTipoCuenta()).thenReturn(responseDto);

        ResponseEntity<ResponseDto> responseEntity = tipoCuentaController.obtenerTipoCuenta();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(responseDto, responseEntity.getBody());
    }
}