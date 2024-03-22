package com.superfinanciera.mycrud.service;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.dto.TransaccionDto;

public interface ITransaccionService {

    ResponseDto realizarTansaccion(TransaccionDto transaccionDto);
}
