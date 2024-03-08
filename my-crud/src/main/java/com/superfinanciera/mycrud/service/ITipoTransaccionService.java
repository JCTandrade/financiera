package com.superfinanciera.mycrud.service;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.model.TipoTransaccion;

public interface ITipoTransaccionService {
    ResponseDto obtenerListaTipoTransacion();

    TipoTransaccion buscarPorId(Long id) throws Exception;
}

