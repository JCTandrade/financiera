package com.superfinanciera.mycrud.repositories;

import com.superfinanciera.mycrud.model.EstadoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoCuentaRepository extends JpaRepository<EstadoCuenta, Long> {
}
