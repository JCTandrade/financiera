package com.superfinanciera.mycrud.repositories;

import com.superfinanciera.mycrud.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}
