package com.superfinanciera.mycrud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ClienteRegistradoDto {
    private Long idCliente;

    @NotNull(message = "El nombre no puede estar vacio")
    private String nombreCliente;

    @NotNull(message = "El apellido no puede estar vacion")
    private String apellidoCliente;

    @NotNull(message = "El tipo de identificacion no puede estar vacio")
    private String tipoIdentificacionCliente;

    @NotNull(message = "El numero de identificacion no puede estar vacion")
    @Pattern(regexp = "\\d+", message = "El campo debe de contener solo numeros")
    private String numeroIndetificacionCliente;

    @NotNull(message = "El correo electronico o email no pueden quedar vacios")
    @Email(message = "Debe de ingresar un correo electronico valido")
    private String correoCliente;

    @NotNull(message = "La fecha no puede estar vacia")
    private LocalDate fechaNacimientoCliente;
}
