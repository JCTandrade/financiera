package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.dto.ClienteRegistradoDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.model.Clientes;
import com.superfinanciera.mycrud.repositories.ClientesRepository;
import com.superfinanciera.mycrud.repositories.CuentaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class ClienteServiceTest {

    @InjectMocks ClienteService clienteService;

    @Mock
    ClientesRepository clientesRepository;

    @Mock
    CuentaRepository cuentaRepository;

    ClienteRegistradoDto clienteRegistradoDto;

    ResponseDto responseDto;

    @BeforeEach
    void setUp() {
        clienteRegistradoDto = new ClienteRegistradoDto();
        clienteRegistradoDto.setNombreCliente("j");

        responseDto = new ResponseDto();
        responseDto.setError(true);
        responseDto.setStatus(HttpStatus.OK);
    }

    @Test
    void testRegistrarClientePrimerIfNombre() {
        clienteRegistradoDto.setNombreCliente("J");
        clienteRegistradoDto.setApellidoCliente("Tovar");

        responseDto.setError(false);

        Mockito.when(clientesRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        var servicio =clienteService.resgistrarCliente(clienteRegistradoDto);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Nombre o apellido no cumple con el numero de caracteres especificos",servicio.getMensaje());
    }
    @Test
    void testRegistrarClientePrimerIfApellido() {
        clienteRegistradoDto.setNombreCliente("Juan");
        clienteRegistradoDto.setApellidoCliente("T");

        responseDto.setError(false);

        Mockito.when(clientesRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        var servicio =clienteService.resgistrarCliente(clienteRegistradoDto);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Nombre o apellido no cumple con el numero de caracteres especificos",servicio.getMensaje());
    }

    @Test
    void testRegistrarClienteException() {
        clienteRegistradoDto.setNombreCliente("Juan");
        clienteRegistradoDto.setApellidoCliente("Tovar");
        responseDto.setError(responseDto.isError());

        Mockito.when(clienteService.buscarClienteId(Mockito.anyLong())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        var servicio = clienteService.resgistrarCliente(clienteRegistradoDto);

        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK, servicio.getStatus());
    }

    @Test
    void testResgistrarClienteTryIfValidarTipoIdentificacionOk() {
        clienteRegistradoDto.setNombreCliente("Juan");
        clienteRegistradoDto.setApellidoCliente("Tovar");
        clienteRegistradoDto.setFechaNacimientoCliente(LocalDate.now().minusYears(20));

        Mockito.when(clientesRepository.findByTipoIdentificacionClienteAndNumeroIndetificacionCliente(Mockito.anyString(), Mockito.anyString())).thenReturn(new Clientes());

        var servicio =clienteService.resgistrarCliente(clienteRegistradoDto);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK, servicio.getStatus());
    }

    @Test
    void testResgistrarClienteTryIf() {
        clienteRegistradoDto.setNombreCliente("Juan");
        clienteRegistradoDto.setApellidoCliente("Tovar");
        clienteRegistradoDto.setFechaNacimientoCliente(LocalDate.now());

        Mockito.when(clientesRepository.findByTipoIdentificacionClienteAndNumeroIndetificacionCliente(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

        var servicio =clienteService.resgistrarCliente(clienteRegistradoDto);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("El cliente debe de ser mayor de edad", servicio.getMensaje());
    }

    @Test
    void testResgistrarClienteTryElse() {
        clienteRegistradoDto.setNombreCliente("Juan");
        clienteRegistradoDto.setApellidoCliente("Tovar");

        Mockito.when(clientesRepository.findByTipoIdentificacionClienteAndNumeroIndetificacionCliente(clienteRegistradoDto.getTipoIdentificacionCliente(), clienteRegistradoDto.getNumeroIndetificacionCliente())).thenReturn(new Clientes());

        var servicio =clienteService.resgistrarCliente(clienteRegistradoDto);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("La informacion suministrada ya existe", servicio.getMensaje());
    }

    @Test
    void testActualizarClientePrimerIfNombre() {
        clienteRegistradoDto.setNombreCliente("A");
        clienteRegistradoDto.setApellidoCliente("Diaz");
        responseDto.setError(false);

        var servicio =clienteService.actualizarCliente(clienteRegistradoDto);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Nombre o apellido no cumple con el numero de caracteres especificos",servicio.getMensaje());
    }

    @Test
    void testActualizarClientePrimerIfApellido() {
        clienteRegistradoDto.setNombreCliente("Andres");
        clienteRegistradoDto.setApellidoCliente("D");
        responseDto.setError(false);

        var servicio =clienteService.actualizarCliente(clienteRegistradoDto);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Nombre o apellido no cumple con el numero de caracteres especificos",servicio.getMensaje());
    }

    @Test
    void testActualizarClienteExitosa() {
        clienteRegistradoDto.setNombreCliente("Juan");
        clienteRegistradoDto.setApellidoCliente("Tovar");
        clienteRegistradoDto.setIdCliente(2L);
        responseDto.setError(false);

        Mockito.when(clientesRepository.findById(2L)).thenReturn(Optional.of(new Clientes()));

        var servicio =clienteService.actualizarCliente(clienteRegistradoDto);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Actualizacion realizada de manera correcta",servicio.getMensaje());
    }

    @Test
    void testActualizarClienteError() {
        clienteRegistradoDto.setNombreCliente("Juan");
        clienteRegistradoDto.setApellidoCliente("Tovar");
        clienteRegistradoDto.setIdCliente(2L);
        responseDto.setError(false);

        Mockito.when(clientesRepository.findById(null)).thenReturn(Optional.of(new Clientes()));

        var servicio =clienteService.actualizarCliente(clienteRegistradoDto);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK,servicio.getStatus());
    }

    @Test
    void testEliminarClienteOk() {
        clienteRegistradoDto.setIdCliente(2L);
        responseDto.setError(false);
        Mockito.when(cuentaRepository.existsByClienteId(2L)).thenReturn(false);
        var servicio = clienteService.eliminarCliente(2L);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Cliente eliminado correctamente", servicio.getMensaje());
    }

    @Test
    void testEliminarClienteError() {
        clienteRegistradoDto.setIdCliente(2L);
        responseDto.setError(true);
        Mockito.when(cuentaRepository.existsByClienteId(2L)).thenReturn(true);
        var servicio = clienteService.eliminarCliente(2L);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("El cliente no puede ser eliminado porque tiene productos vinculados", servicio.getMensaje());
    }

    @Test
    void testEliminarClienteException() {
        Mockito.when(cuentaRepository.existsByClienteId(Mockito.anyLong())).thenThrow(new RuntimeException("Error al buscar productos vinculados"));

        var servicio = clienteService.eliminarCliente(2L);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("Error al buscar productos vinculados", servicio.getMensaje());
    }

    @Test
    void testBuscarClienteIdOk() {
        clienteRegistradoDto.setIdCliente(3L);

        Mockito.when(clientesRepository.findById(3L)).thenReturn(Optional.of(new Clientes()));

        var servicio = clienteService.buscarClienteId(3L);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals(HttpStatus.OK, servicio.getStatus());
    }

    @Test
    void testBuscarClienteIdError() {
        Mockito.when(clientesRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        var servicio = clienteService.buscarClienteId(1L);
        Assertions.assertNotNull(servicio);
        Assertions.assertEquals("No existe un cliente con el ID: " + 1
                , servicio.getMensaje());
    }
}
