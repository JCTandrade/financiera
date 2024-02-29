package com.superfinanciera.mycrud.repositories;

import com.superfinanciera.mycrud.model.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoTransaccionRespository extends JpaRepository<TipoTransaccion, Long> {
}
