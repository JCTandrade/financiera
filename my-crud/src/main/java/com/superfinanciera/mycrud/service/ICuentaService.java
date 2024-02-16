package com.superfinanciera.mycrud.service;

import com.superfinanciera.mycrud.dto.CuentaRegistradaDto;
import com.superfinanciera.mycrud.dto.ResponseDto;

public interface ICuentaService {

    ResponseDto resgistrarCuenta(CuentaRegistradaDto cuentaRegistradaDto);
}
