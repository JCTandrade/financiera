package com.superfinanciera.mycrud.service;

import com.superfinanciera.mycrud.dto.CuentaRegistradaDto;
import com.superfinanciera.mycrud.dto.EstadoCuentaDto;
import com.superfinanciera.mycrud.dto.ResponseDto;

public interface ICuentaService {

    ResponseDto resgistrarCuenta(CuentaRegistradaDto cuentaRegistradaDto);

    ResponseDto actualizarCuenta(EstadoCuentaDto cuentaRegistradaDto) throws Exception;

    ResponseDto buscarCuentaId(Long id);
}
