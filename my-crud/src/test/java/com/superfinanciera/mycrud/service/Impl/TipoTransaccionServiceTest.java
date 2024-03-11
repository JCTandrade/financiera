package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.model.TipoTransaccion;
import com.superfinanciera.mycrud.repositories.TipoTransaccionRespository;
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
import java.util.Optional;

@SpringBootTest
public class TipoTransaccionServiceTest {
    @InjectMocks TipoTransaccionService tipoTransaccionService;

    @Mock
    TipoTransaccionRespository tipoTransaccionRespository;

    TipoTransaccion tipoTransaccion;

    List<TipoTransaccion>tipoTransaccionList;

    @BeforeEach
    void setUp() {
        tipoTransaccion = new TipoTransaccion();
        tipoTransaccion.setTipo("Dato prueba");

        tipoTransaccionList = new ArrayList<>();
        tipoTransaccionList.add(tipoTransaccion);
    }

    @Test
    void testObtenerListaTipoCuentaOk() {
        Mockito.when(tipoTransaccionRespository.findAll()).thenReturn(tipoTransaccionList);
        var servicio = tipoTransaccionService.obtenerListaTipoTransacion();
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK,servicio.getStatus());
    }
    @Test
    void testObtenerListaTipoCuentaError() {
        Mockito.when(tipoTransaccionRespository.findAll()).thenReturn(new ArrayList<>());
        var servicio = tipoTransaccionService.obtenerListaTipoTransacion();
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("No existen tipos de transacciones",servicio.getMensaje());
    }

    @Test
    void testBuscarPorIdOk() throws Exception {
        Mockito.when(tipoTransaccionRespository.findById(Mockito.anyLong())).thenReturn(Optional.of(tipoTransaccion));
        var tipoTransaccionEncontrado = tipoTransaccionService.buscarPorId(1L);
        Assertions.assertNotNull(tipoTransaccionEncontrado);
        Assertions.assertEquals(tipoTransaccion.getId(), tipoTransaccionEncontrado.getId());
        Assertions.assertEquals(tipoTransaccion.getTipo(), tipoTransaccionEncontrado.getTipo());
    }

    @Test
    void testBuscarPorIdError() throws Exception {
        Mockito.when(tipoTransaccionRespository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> {
           tipoTransaccionService.buscarPorId(1L);
        });
    }
}
