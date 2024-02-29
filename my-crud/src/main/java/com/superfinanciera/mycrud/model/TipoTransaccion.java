package com.superfinanciera.mycrud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tipo_transaccion")
public class TipoTransaccion {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo")
    private String tipo;
}
