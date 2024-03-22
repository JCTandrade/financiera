package com.superfinanciera.mycrud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CuentaRegistradaDto {
    private Long idCuenta;

    @NotNull(message = "Debe de selecionar un tipo de cuenta")
    private String tipoCuenta;

    @NotNull(message = "La cuenta de ahorros debe estar activa")
    private String estado;

    @NotNull(message = "El campo no puede estar vacío")
    private String exentaGMF;

    @NotNull(message = "El campo de Id del cliente no puede quedar vacio")
    private Long idCliente;
}
