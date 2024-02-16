package com.superfinanciera.mycrud.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CuentaRegistradaDto {

    private Long idCuenta;

    @NotNull(message = "Debe de selecionar un tipo de cuenta")
    private String tipoCuenta;
}
