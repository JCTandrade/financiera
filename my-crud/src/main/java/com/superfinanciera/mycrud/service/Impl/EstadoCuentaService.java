package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.model.EstadoCuenta;
import com.superfinanciera.mycrud.repositories.EstadoCuentaRepository;
import com.superfinanciera.mycrud.service.IEstadoCuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstadoCuentaService implements IEstadoCuentaService {

    @Autowired
    EstadoCuentaRepository estadoCuentaRepository;

    @Override
    public EstadoCuenta buscarPorId(Long id) throws Exception {
        Optional<EstadoCuenta> estadoCuenta = this.estadoCuentaRepository.findById(id);
        if (estadoCuenta.isPresent()) {
            return estadoCuenta.get();
        }
        throw new Exception("No existe un estado de cuenta");
    }
}
