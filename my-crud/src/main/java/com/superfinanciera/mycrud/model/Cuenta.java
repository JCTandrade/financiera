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
@Table(name = "cuenta")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdCuenta;

    @Column(name = "tipo_cuenta")
    private String tipoCuenta;

    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @Column(name = "estado")
    private String estado;

    @Column(name = "saldo")
    private String saldo;

    @Column(name = "exenta_GMF")
    private boolean exentaGMF;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "IdCliente")
    private Clientes clientes;
}
