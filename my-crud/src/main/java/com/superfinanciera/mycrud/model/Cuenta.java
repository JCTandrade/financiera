package com.superfinanciera.mycrud.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuenta")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdCuenta;

    @Column(name = "tipo_cuenta")
    private String tipoCuenta;

    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @ManyToOne
    @JoinColumn(name = "id_estado_cuenta")
    private EstadoCuenta estadoCuenta;

    @Column(name = "saldo")
    private String saldo;

    @Column(name = "exenta_GMF")
    private String exentaGMF;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion")
    private Date updatedAt;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_cliente")
    private Clientes clientes;
}
