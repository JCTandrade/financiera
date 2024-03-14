package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.ClienteRegistradoDto;
import com.superfinanciera.mycrud.dto.CuentaRegistradaDto;
import com.superfinanciera.mycrud.dto.EstadoCuentaDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.repositories.EstadoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoTransaccionRespository;
import com.superfinanciera.mycrud.service.Impl.CuentaService;
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

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @InjectMocks CuentaController cuentaController;

    @MockBean
    CuentaService cuentaService;

    @MockBean
    TipoCuentaRepository tipoCuentaRepository;

    @MockBean
    EstadoCuentaRepository estadoCuentaRepository;

    @MockBean
    TipoTransaccionRespository tipoTransaccionRespository;

    CuentaRegistradaDto cuentaRegistradaDto;

    ResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cuentaRegistradaDto = new CuentaRegistradaDto();
        cuentaRegistradaDto.setIdCuenta(2L);
        cuentaRegistradaDto.setTipoCuenta("Activa");

        responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK);
        responseDto.isError();
    }

    @Test
    void testResgistrarCuentaOk() {
        cuentaRegistradaDto.setIdCliente(2l);

        responseDto.setStatus(HttpStatus.CREATED);
        responseDto.setMensaje("Cuenta registrada exitosamente");

        Mockito.when(cuentaService.resgistrarCuenta(any(CuentaRegistradaDto.class))).thenReturn(responseDto);

        ResponseEntity<ResponseDto> responseEntity = cuentaController.registarCuenta(cuentaRegistradaDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals("Cuenta registrada exitosamente", responseEntity.getBody().getMensaje());
    }

    @Test
    void testActualizarCuentaOk() throws Exception {
        cuentaRegistradaDto.setIdCliente(2l);

        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMensaje("Cuenta actualizada de manera exitosa");

        Mockito.when(cuentaService.actualizarCuenta(any(EstadoCuentaDto.class))).thenReturn(responseDto);

        ResponseEntity<ResponseDto> responseEntity = cuentaController.actualizarCliente(new EstadoCuentaDto());

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Cuenta actualizada de manera exitosa", responseEntity.getBody().getMensaje());
    }

    @Test
    void testBuscarCuentaPorIdOk() throws Exception {
        cuentaRegistradaDto.setIdCliente(2l);

        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMensaje("Cuenta encontrada");

        Mockito.when(cuentaService.buscarCuentaId(2L)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> responseEntity = cuentaController.buscarCliente(2L);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Cuenta encontrada", responseEntity.getBody().getMensaje());
    }
}