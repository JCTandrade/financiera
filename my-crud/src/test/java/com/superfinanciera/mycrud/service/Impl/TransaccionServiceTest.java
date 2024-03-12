package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.dto.TransaccionDto;
import com.superfinanciera.mycrud.model.Cuenta;
import com.superfinanciera.mycrud.model.TipoTransaccion;
import com.superfinanciera.mycrud.repositories.CuentaRepository;
import com.superfinanciera.mycrud.repositories.TransaccionRepository;
import com.superfinanciera.mycrud.utils.Constant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class TransaccionServiceTest {
    @InjectMocks TransaccionService transaccionService;

    @Mock
    TransaccionRepository transaccionRepository;

    @Mock
    CuentaService cuentaService;

    @Mock
    CuentaRepository cuentaRepository;

    @Mock
    TipoTransaccionService tipoTransaccionService;

    @Mock
    ModelMapper modelMapper;

    TransaccionDto transaccionDto;
    ResponseDto responseDto;
    TipoTransaccion tipoTransaccion;

    Cuenta cuentaOrigen;

    @BeforeEach
    void setUp() {
        transaccionDto = new TransaccionDto();
        transaccionDto.setTipoTransaccion(1L);
        transaccionDto.setCuentaOrigen(1L);
        transaccionDto.setCuentaDestino(2L);
        transaccionDto.setMonto(100.0);

        responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK);

        tipoTransaccion = new TipoTransaccion();
        tipoTransaccion.setId(1L);
        tipoTransaccion.setTipo(Constant.Transaccion.TIPO_CONSIGNACION);

        cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(1L);
        cuentaOrigen.setSaldo(500.0);
    }

    @Test
    void testRealizarTransaccionConsignacion() throws Exception {
        Mockito.when(cuentaService.buscarCuentaId(Mockito.any())).thenReturn(responseDto);
        Mockito.when(tipoTransaccionService.buscarPorId(Mockito.any())).thenReturn(tipoTransaccion);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(Cuenta.class))).thenReturn(cuentaOrigen);
        Mockito.when(cuentaService.buscarCuentaId(transaccionDto.getCuentaDestino())).thenReturn(responseDto);
        var servicio = transaccionService.realizarTansaccion(transaccionDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK,servicio.getStatus());
    }

    @Test
    void testRealizarRetiroOk() throws Exception {
        transaccionDto.setTipoTransaccion(2L);
        tipoTransaccion.setTipo(Constant.Transaccion.TIPO_RETIRO);
        transaccionDto.setCuentaOrigen(1L);
        Mockito.when(cuentaService.buscarCuentaId(Mockito.any())).thenReturn(responseDto);
        Mockito.when(tipoTransaccionService.buscarPorId(Mockito.any())).thenReturn(tipoTransaccion);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(Cuenta.class))).thenReturn(cuentaOrigen);
        Mockito.when(cuentaService.buscarCuentaId(transaccionDto.getCuentaDestino())).thenReturn(responseDto);
        var servicio = transaccionService.realizarTansaccion(transaccionDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK,servicio.getStatus());
    }

    @Test
    void testRealizarRetiroFondosInsuficientes() throws Exception {
        transaccionDto.setTipoTransaccion(2L);
        tipoTransaccion.setTipo(Constant.Transaccion.TIPO_RETIRO);
        transaccionDto.setCuentaOrigen(1L);
        cuentaOrigen.setSaldo(10.0);
        Mockito.when(cuentaService.buscarCuentaId(Mockito.any())).thenReturn(responseDto);
        Mockito.when(tipoTransaccionService.buscarPorId(Mockito.any())).thenReturn(tipoTransaccion);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(Cuenta.class))).thenReturn(cuentaOrigen);
        Mockito.when(cuentaService.buscarCuentaId(transaccionDto.getCuentaDestino())).thenReturn(responseDto);
        var servicio = transaccionService.realizarTansaccion(transaccionDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Error al realizar la transaccion: Error al realizar la transaccion: Fondos insuficientes para el retiro", servicio.getMensaje());
    }

    @Test
    void testRealizarTransaccionCuentaDesstinoNull() throws Exception {
        Mockito.when(cuentaService.buscarCuentaId(Mockito.any())).thenReturn(responseDto);
        Mockito.when(tipoTransaccionService.buscarPorId(Mockito.any())).thenReturn(tipoTransaccion);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(Cuenta.class))).thenReturn(cuentaOrigen);
        Mockito.when(cuentaService.buscarCuentaId(transaccionDto.getCuentaDestino())).thenReturn(null);
        var servicio = transaccionService.realizarTansaccion(transaccionDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK,servicio.getStatus());
    }

    @Test
    void testRealizarTransferenciaError() throws Exception {
        tipoTransaccion.setId(3L);
        transaccionDto.setCuentaDestino(null);
        Mockito.when(cuentaService.buscarCuentaId(Mockito.any())).thenReturn(responseDto);
        Mockito.when(tipoTransaccionService.buscarPorId(Mockito.any())).thenReturn(tipoTransaccion);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(Cuenta.class))).thenReturn(cuentaOrigen);
        Mockito.when(cuentaService.buscarCuentaId(transaccionDto.getCuentaDestino())).thenReturn(responseDto);
        var servicio = transaccionService.realizarTansaccion(transaccionDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Error al realizar la transaccion: Para realizar una transferencia se debe de ingresar una cuenta destino",servicio.getMensaje());
    }

    @Test
    void testRealizarTransferenciaOk() throws Exception {
        tipoTransaccion.setId(3L);
        Mockito.when(cuentaService.buscarCuentaId(Mockito.any())).thenReturn(responseDto);
        Mockito.when(tipoTransaccionService.buscarPorId(Mockito.any())).thenReturn(tipoTransaccion);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(Cuenta.class))).thenReturn(cuentaOrigen);
        Mockito.when(cuentaService.buscarCuentaId(transaccionDto.getCuentaDestino())).thenReturn(responseDto);
        var servicio = transaccionService.realizarTansaccion(transaccionDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Se realizo la transferencia de manera correcta",servicio.getMensaje());
    }

    @Test
    void testRealizarTransferenciaCuentaError() throws Exception {
        tipoTransaccion.setId(3L);
        responseDto.setError(true);
        Mockito.when(cuentaService.buscarCuentaId(Mockito.any())).thenReturn(responseDto);
        Mockito.when(tipoTransaccionService.buscarPorId(Mockito.any())).thenReturn(tipoTransaccion);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(Cuenta.class))).thenReturn(cuentaOrigen);
        Mockito.when(cuentaService.buscarCuentaId(transaccionDto.getCuentaDestino())).thenReturn(responseDto);
        var servicio = transaccionService.realizarTansaccion(transaccionDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("La cuenta con el Id no existe",servicio.getMensaje());
    }

    @Test
    void testRealizarTransferenciaFondosInsuficientes() throws Exception {
        tipoTransaccion.setId(3L);
        cuentaOrigen.setSaldo(30.0);
        Mockito.when(cuentaService.buscarCuentaId(Mockito.any())).thenReturn(responseDto);
        Mockito.when(tipoTransaccionService.buscarPorId(Mockito.any())).thenReturn(tipoTransaccion);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(Cuenta.class))).thenReturn(cuentaOrigen);
        Mockito.when(cuentaService.buscarCuentaId(transaccionDto.getCuentaDestino())).thenReturn(responseDto);
        var servicio = transaccionService.realizarTansaccion(transaccionDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Error al realizar la transaccion: Error al realizar la transferencia: Fondos insuficientes para la transaccion",servicio.getMensaje());
    }

    //FALTA HACER PARA VALIDAR TIPO TRANSACCION CUANDO EL MONTO ES NEGATIVO Y CUANDO SE INGRESA
    //SE SELECCIONA UNA OPCION MAL PARA EL TIPO DE TRANSACCION
}
