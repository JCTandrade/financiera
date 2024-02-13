package com.superfinanciera.mycrud.service;

import com.superfinanciera.mycrud.dto.ClienteRegistradoDto;
import com.superfinanciera.mycrud.dto.ResponseDto;

public interface IClienteService {

    ResponseDto resgistrarCliente(ClienteRegistradoDto clienteRegistradoDto);
}
