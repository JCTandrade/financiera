package com.superfinanciera.mycrud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
    private LocalDate fechaNacimientoCliente;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion")
    private Date updatedAt;

    @OneToMany(mappedBy = "clientes")
    private List<Cuenta> cuentas;

}
