package com.superfinanciera.mycrud.service;

import com.superfinanciera.mycrud.controllers.TransaccionController;
import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.dto.TransaccionDto;
import com.superfinanciera.mycrud.model.TipoTransaccion;
import com.superfinanciera.mycrud.model.Transaccion;

public interface ITransaccionService {

    ResponseDto realizarTansaccion(TransaccionDto transaccionDto);
}
