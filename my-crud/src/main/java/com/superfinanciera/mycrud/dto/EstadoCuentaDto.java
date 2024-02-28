package com.superfinanciera.mycrud.dto;

import com.superfinanciera.mycrud.model.EstadoCuenta;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EstadoCuentaDto {
    private Long idCuenta;

    @NotNull(message = "El estado de la cuenta no puede ser nulo")
    private Long estadoCuenta;

    @NotNull(message = "El campo no puede estar vacío")
    private String exentaGMF;
}
