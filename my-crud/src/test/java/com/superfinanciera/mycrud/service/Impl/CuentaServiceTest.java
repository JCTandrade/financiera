package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.dto.CuentaRegistradaDto;
import com.superfinanciera.mycrud.dto.EstadoCuentaDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.model.Cuenta;
import com.superfinanciera.mycrud.model.EstadoCuenta;
import com.superfinanciera.mycrud.repositories.CuentaRepository;
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
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CuentaServiceTest {

    @InjectMocks CuentaService cuentaService;

    @Mock
    CuentaRepository cuentaRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock ClienteService clienteService;

    @Mock EstadoCuentaService estadoCuentaService;

    CuentaRegistradaDto cuentaRegistradaDto;

    ResponseDto responseDto;

    Cuenta cuentaCreada;

    EstadoCuentaDto estadoCuentaDto;

    EstadoCuenta estadoCuenta;

    Cuenta cuenta;

    @BeforeEach
    void setUp() {
        cuentaRegistradaDto = new CuentaRegistradaDto();
        cuentaRegistradaDto.setIdCliente(1L);

        cuentaCreada = new Cuenta();
        cuentaCreada.setIdCuenta(1L);

        responseDto = new ResponseDto();
        responseDto.setError(true);
        responseDto.setStatus(HttpStatus.OK);

        estadoCuentaDto = new EstadoCuentaDto();
        estadoCuentaDto.setEstadoCuenta(2L);

        estadoCuenta = new EstadoCuenta();
        estadoCuenta.setNombre(Constant.EstadoCuenta.ESTADO_CUENTA_ACTIVA);

        cuenta = new Cuenta();
        cuenta.setEstadoCuenta(new EstadoCuenta(2L, Constant.EstadoCuenta.ESTADO_CUENTA_INACTIVA));
    }

    @Test
    void testRegistrarCuentaError() {
        Mockito.when(clienteService.buscarClienteId(Mockito.anyLong())).thenReturn(responseDto);

        var servicio = cuentaService.resgistrarCuenta(cuentaRegistradaDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("El cliente con el Id no existe", servicio.getMensaje());
    }

    @Test
    void testRegistrarCuentaCreadaCorrectamente() {
        cuentaRegistradaDto.setIdCliente(1L);
        cuentaRegistradaDto.setTipoCuenta(Constant.NOMBRE_CUENTA_AHORRO);
        responseDto.setError(false);

        Mockito.when(clienteService.buscarClienteId(Mockito.anyLong())).thenReturn(responseDto);
        Mockito.when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuentaCreada);

        var servicio = cuentaService.resgistrarCuenta(cuentaRegistradaDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("La cuenta se ha registrado correctamente", servicio.getMensaje());
    }

    @Test
    void testRegistrarCuentaCreadaIncorrectamente() {
        cuentaRegistradaDto.setIdCliente(5L);
        cuentaRegistradaDto.setTipoCuenta(Constant.NOMBRE_CUENTA_AHORRO);
        responseDto.setError(false);
        cuentaCreada.setIdCuenta(null);

        Mockito.when(clienteService.buscarClienteId(Mockito.anyLong())).thenReturn(responseDto);
        Mockito.when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuentaCreada);

        var servicio = cuentaService.resgistrarCuenta(cuentaRegistradaDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertTrue(servicio.isError());
        Assertions.assertEquals("La cuenta no se puedo crear correctamente", servicio.getMensaje());
    }

    @Test
    void testRegistrarCuentaCreadaGenerarNumeroException() {
        cuentaRegistradaDto.setIdCliente(1L);
        cuentaRegistradaDto.setTipoCuenta(null);
        responseDto.setError(false);

        Mockito.when(clienteService.buscarClienteId(Mockito.anyLong())).thenReturn(responseDto);
        Mockito.when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuentaCreada);

        var servicio = cuentaService.resgistrarCuenta(cuentaRegistradaDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Error al registrar la cuenta: El tipo de cuenta no puede ser vacio", servicio.getMensaje());
    }

    @Test
    void testRegistrarCuentaException() {
        cuentaRegistradaDto.setIdCliente(6L);

        Mockito.when(clienteService.buscarClienteId(Mockito.anyLong())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        var servicio = cuentaService.resgistrarCuenta(cuentaRegistradaDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Error al registrar la cuenta: 404 NOT_FOUND", servicio.getMensaje());
    }

    @Test
    void testActualizarCuentaIsEmpy() throws Exception {
        estadoCuentaDto.setIdCuenta(1L);
        Mockito.when(cuentaRepository.findById(estadoCuentaDto.getIdCuenta())).thenReturn(Optional.empty());

        var servicio = cuentaService.actualizarCuenta(estadoCuentaDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("No se puede actualizar la cuenta por que no existe", servicio.getMensaje());
    }

    @Test
    void testActualizarCuentaIsNull() throws Exception {
        estadoCuentaDto.setIdCuenta(1L);
        estadoCuentaDto.setEstadoCuenta(0L);

        Mockito.when(cuentaRepository.findById(estadoCuentaDto.getIdCuenta())).thenReturn(Optional.of(new Cuenta()));
        Mockito.when(estadoCuentaService.buscarPorId(estadoCuentaDto.getEstadoCuenta())).thenReturn(null);

        var servicio = cuentaService.actualizarCuenta(estadoCuentaDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("No se puede actualizar la cuenta por que el estado asignado no existe", servicio.getMensaje());
    }

    @Test
    void testActualizarCuentaExitosaActiva() throws Exception {
        cuentaCreada.setSaldo(1000.0);
        cuentaCreada.setEstadoCuenta(estadoCuenta);

        Mockito.when(cuentaRepository.findById(estadoCuentaDto.getIdCuenta())).thenReturn(Optional.of(cuentaCreada));
        Mockito.when(estadoCuentaService.buscarPorId(estadoCuentaDto.getEstadoCuenta())).thenReturn(estadoCuenta);

        var servicio = cuentaService.actualizarCuenta(estadoCuentaDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK, servicio.getStatus());
    }

    @Test
    void testActualizarCuentaExitosaInactiva() throws Exception {
        cuentaCreada.setSaldo(1000.0);
        estadoCuenta.setNombre(Constant.EstadoCuenta.ESTADO_CUENTA_INACTIVA);
        cuentaCreada.setEstadoCuenta(estadoCuenta);

        Mockito.when(cuentaRepository.findById(estadoCuentaDto.getIdCuenta())).thenReturn(Optional.of(cuentaCreada));
        Mockito.when(estadoCuentaService.buscarPorId(estadoCuentaDto.getEstadoCuenta())).thenReturn(estadoCuenta);

        var servicio = cuentaService.actualizarCuenta(estadoCuentaDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK, servicio.getStatus());
    }

    @Test
    void testActualizarCuentaExitosaAcitvaInactiva() throws Exception {
        cuentaCreada.setSaldo(500.0);
        estadoCuenta.setNombre(Constant.EstadoCuenta.ESTADO_CUENTA_ACTIVA);

        EstadoCuenta estadoCuenta1 = new EstadoCuenta();
        estadoCuenta1.setNombre(Constant.EstadoCuenta.ESTADO_CUENTA_INACTIVA);

        estadoCuentaDto.setExentaGMF("true");
        cuentaCreada.setEstadoCuenta(estadoCuenta1);


        Mockito.when(cuentaRepository.findById(estadoCuentaDto.getIdCuenta())).thenReturn(Optional.of(cuentaCreada));
        Mockito.when(estadoCuentaService.buscarPorId(estadoCuentaDto.getEstadoCuenta())).thenReturn(estadoCuenta);

        var servicio = cuentaService.actualizarCuenta(estadoCuentaDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK, servicio.getStatus());
    }

    @Test
    void testBuscarPorIdOk() {
        Mockito.when(cuentaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cuentaCreada));
        var cuentaEncontrada = cuentaService.buscarCuentaId(1L);
        Assertions.assertNotNull(cuentaEncontrada);
        Assertions.assertEquals(cuentaCreada, cuentaEncontrada.getMensaje());
    }

    @Test
    void testBuscarPorIdError() {
        Mockito.when(cuentaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        var cuentaEncontrada = cuentaService.buscarCuentaId(1L);
        Assertions.assertNotNull(cuentaEncontrada);
        Assertions.assertEquals("No existe una cuenta con el ID: " + 1
                , cuentaEncontrada.getMensaje());
    }
}
