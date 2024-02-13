package com.superfinanciera.mycrud.repositories;

import com.superfinanciera.mycrud.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {

    Clientes findByTipoIdentificacionClienteAndNumeroIndetificacionCliente(String tipoIdentificacion, String numeroIdentificacion);
}
