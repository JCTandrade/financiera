package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.repositories.EstadoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoTransaccionRespository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(HealthControllersTest.class)
class HealthControllersTest {

    @InjectMocks HealthControllers healthControllers;

    @MockBean
    TipoCuentaRepository tipoCuentaRepository;

    @MockBean
    EstadoCuentaRepository estadoCuentaRepository;

    @MockBean
    TipoTransaccionRespository tipoTransaccionRespository;

    @Autowired
    MockMvc mockMvc;

    ResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK);
    }

    @Test
    void testListarCliente(){

        ResponseEntity<String> responseEntity = healthControllers.listarCliente();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Aplicacion corriendo", responseEntity.getBody());
    }
}