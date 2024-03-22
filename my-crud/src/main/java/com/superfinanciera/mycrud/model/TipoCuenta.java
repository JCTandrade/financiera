package com.superfinanciera.mycrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tipo_cuenta")
@NamedQuery(name = "TipoCuenta.findAll", query = "SELECT te FROM TipoCuenta te WHERE te.isEliminado = 'false'")
public class TipoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "eliminado")
    private String isEliminado;
}
