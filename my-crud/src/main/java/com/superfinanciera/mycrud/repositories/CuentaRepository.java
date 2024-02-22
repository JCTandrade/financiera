package com.superfinanciera.mycrud.repositories;

import com.superfinanciera.mycrud.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query("SELECT COUNT(c) > 0 FROM Cuenta c WHERE c.clientes.idCliente = :clienteId")
    boolean existsByClienteId(Long clienteId);
}
