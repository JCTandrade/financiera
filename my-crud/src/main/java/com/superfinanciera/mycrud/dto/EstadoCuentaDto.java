package com.superfinanciera.mycrud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class EstadoCuentaDto {
    private Long idCuenta;

    @NotNull(message = "El estado de la cuenta no puede ser nulo")
    private Long estadoCuenta;

    @NotNull(message = "El campo no puede estar vacío")
    private String exentaGMF;
}
