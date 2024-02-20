package com.superfinanciera.mycrud.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CuentaRegistradaDto {

    @NotNull(message = "Debe de selecionar un tipo de cuenta")
    private String tipoCuenta;

    @NotNull(message = "Se debe asignar un estado a la cuenta [Activa, Inactiva, Cancelada]")
    private String estado;

    @NotNull(message = "Debe ingresar un saldo")
    private String saldo;

    @NotNull(message = "El campo no puede estar vacío")
    private boolean exentaGMF;

    @NotNull(message = "El campo de Id del cliente no puede quedar vacio")
    private Long idCliente;

}
