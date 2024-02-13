package com.superfinanciera.mycrud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clientes")
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "nombre")
    private String nombreCliente;

    @Column(name = "apellido")
    private String apellidoCliente;

    @Column(name = "Identificacio")
    private String tipoIdentificacionCliente;

    @Column(name = "num_identificacion")
    private String numeroIndetificacionCliente;

    @Column(name = "correo")
    private String correoCliente;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimientoCliente;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

}
