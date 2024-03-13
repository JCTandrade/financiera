package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.model.EstadoCuenta;
import com.superfinanciera.mycrud.repositories.EstadoCuentaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class EstadoCuentaServiceTest {

    @InjectMocks EstadoCuentaService estadoCuentaService;

    @Mock
    EstadoCuentaRepository estadoCuentaRepository;

    EstadoCuenta estadoCuenta;

    @BeforeEach
    void setUp() {
        estadoCuenta = new EstadoCuenta();
        estadoCuenta.setIdEstadoCuenta(1L);
    }

    @Test
    void testBuscarPorIdOk() throws Exception {
        Mockito.when(estadoCuentaRepository.findById(estadoCuenta.getIdEstadoCuenta())).thenReturn(Optional.of(estadoCuenta));

        var estadoCuentaEncontrada = estadoCuentaService.buscarPorId(1L);
        Assertions.assertNotNull(estadoCuentaEncontrada);
        Assertions.assertEquals(1L, estadoCuentaEncontrada.getIdEstadoCuenta());
    }

    @Test
    void testBuscarPorIdException() throws Exception {
        Mockito.when(estadoCuentaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> {
           estadoCuentaService.buscarPorId(1L);
        }, "No existe un estado de cuenta");
    }
}
