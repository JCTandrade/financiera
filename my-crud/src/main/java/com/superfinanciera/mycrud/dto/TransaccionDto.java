package com.superfinanciera.mycrud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class TransaccionDto {

    @NotNull(message = "Debe de ingresar el tipo de transaccion a realizar")
    private Long tipoTransaccion;

    @NotNull(message = "El monto no debe de estar vacion")
    private Double monto;

    private Long cuentaOrigen;

    private Long cuentaDestino;
}
