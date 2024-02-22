package com.superfinanciera.mycrud.service;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.model.EstadoCuenta;

public interface IEstadoCuentaService {

    EstadoCuenta buscarPorId(Long id) throws Exception;
}
