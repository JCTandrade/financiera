package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.model.TipoCuenta;
import com.superfinanciera.mycrud.repositories.TipoCuentaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TipoCuentaServiceTest {
    @InjectMocks TipoCuentaService tipoCuentaService;

    @Mock
    TipoCuentaRepository tipoCuentaRepository;

    TipoCuenta tipoCuentaUno;
    TipoCuenta tipoCuentaDos;
    TipoCuenta tipoCuentaTres;

    List<TipoCuenta>listaCuentas;
    @BeforeEach
    void setUp() {
        tipoCuentaUno = new TipoCuenta(1L,"tipo cuenta uno test","false");
        tipoCuentaDos = new TipoCuenta(2L,"tipo cuenta dos test","true");
        tipoCuentaTres = new TipoCuenta(3L,"tipo cuenta tres test","false");

        listaCuentas = new ArrayList<>();
        listaCuentas.add(tipoCuentaUno);
        listaCuentas.add(tipoCuentaDos);
        listaCuentas.add(tipoCuentaTres);
    }

    @Test
    void testIsCorrect() {
        Mockito.when(tipoCuentaRepository.findAll()).thenReturn(listaCuentas);
        var service = tipoCuentaService.obtenerListaTipoCuenta();

        Assertions.assertNotNull(service);
        Assertions.assertEquals(HttpStatus.OK,service.getStatus());
    }

    @Test
    void testIsError() {
        Mockito.when(tipoCuentaRepository.findAll()).thenReturn(new ArrayList<>());
        var service = tipoCuentaService.obtenerListaTipoCuenta();

        Assertions.assertNotNull(service);
        Assertions.assertEquals("No existen tipos cuentas disponibles",service.getMensaje());
    }

}