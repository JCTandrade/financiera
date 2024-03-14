package com.superfinanciera.mycrud.controllers;

import com.superfinanciera.mycrud.dto.ClienteRegistradoDto;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.repositories.EstadoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoTransaccionRespository;
import com.superfinanciera.mycrud.service.Impl.ClienteService;
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

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(ClientesController.class)
class ClientesControllerTest {

    @InjectMocks ClientesController clientesController;

    @MockBean
    ClienteService clienteService;

    @MockBean
    TipoCuentaRepository tipoCuentaRepository;

    @MockBean
    EstadoCuentaRepository estadoCuentaRepository;

    @MockBean
    TipoTransaccionRespository tipoTransaccionRespository;

    ClienteRegistradoDto clienteRegistradoDto;

    ResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        clienteRegistradoDto = new ClienteRegistradoDto();
        clienteRegistradoDto.setNombreCliente("Marta");
        clienteRegistradoDto.setApellidoCliente("Juarez");

        responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK);
        responseDto.isError();
    }

    @Test
    void testResgistrarClienteOk() {
        clienteRegistradoDto.setNombreCliente("John Doe");

        responseDto.setStatus(HttpStatus.CREATED);
        responseDto.setMensaje("Cliente registrado exitosamente");

        Mockito.when(clienteService.resgistrarCliente(any(ClienteRegistradoDto.class))).thenReturn(responseDto);

        ResponseEntity<ResponseDto> responseEntity = clientesController.resgistrarCliente(clienteRegistradoDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals("Cliente registrado exitosamente", responseEntity.getBody().getMensaje());
    }

    @Test
    void testActualizarClienteOk() {
        clienteRegistradoDto.setIdCliente(2L);

        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMensaje("Cliente actualizado correctamente");

        Mockito.when(clienteService.actualizarCliente(any(ClienteRegistradoDto.class))).thenReturn(responseDto);

        ResponseEntity<ResponseDto> responseEntity = clientesController.actualizarCliente(clienteRegistradoDto);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Cliente actualizado correctamente", responseEntity.getBody().getMensaje());
    }

    @Test
    void testBuscarClientePorIdOk() {
        clienteRegistradoDto.setIdCliente(2L);

        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMensaje("Cliente encontrado correctamente");

        Mockito.when(clienteService.buscarClienteId(2L)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> responseEntity = clientesController.buscarCliente(2L);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Cliente encontrado correctamente", responseEntity.getBody().getMensaje());
    }

    @Test
    void testEliminarClienteOk() {
        clienteRegistradoDto.setIdCliente(2L);

        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMensaje("Cliente eliminado correctamente");

        Mockito.when(clienteService.eliminarCliente(2L)).thenReturn(responseDto);

        ResponseEntity<ResponseDto> responseEntity = clientesController.eliminarCliente(2L);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Cliente eliminado correctamente", responseEntity.getBody().getMensaje());
    }
}