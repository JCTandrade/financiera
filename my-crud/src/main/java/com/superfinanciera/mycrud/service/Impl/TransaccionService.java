package com.superfinanciera.mycrud.service.Impl;

import com.superfinanciera.mycrud.dto.ResponseDto;
import com.superfinanciera.mycrud.dto.TransaccionDto;
import com.superfinanciera.mycrud.model.Cuenta;
import com.superfinanciera.mycrud.model.TipoTransaccion;
import com.superfinanciera.mycrud.repositories.CuentaRepository;
import com.superfinanciera.mycrud.repositories.TransaccionRepository;
import com.superfinanciera.mycrud.service.ICuentaService;
import com.superfinanciera.mycrud.service.ITipoTransaccionService;
import com.superfinanciera.mycrud.service.ITransaccionService;
import com.superfinanciera.mycrud.utils.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransaccionService implements ITransaccionService {

    @Autowired
    TransaccionRepository transaccionRepository;

    @Autowired
    ICuentaService cuentaService;

    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    ITipoTransaccionService tipoTransaccionService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseDto realizarTansaccion(TransaccionDto transaccionDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setError(true);
        responseDto.setStatus(HttpStatus.OK);
        try {
            var idTipoTransaccion = transaccionDto.getTipoTransaccion();
            var cuenta = cuentaService.buscarCuentaId(transaccionDto.getCuentaOrigen());
            var tipoTransferencia = tipoTransaccionService.buscarPorId(idTipoTransaccion);
            var cuentaOrigen = modelMapper.map(cuenta.getMensaje(), Cuenta.class);
            var cuentaDestino = cuentaService.buscarCuentaId(transaccionDto.getCuentaDestino());
            if (cuentaDestino == null) {
                throw new Exception("Para realizar una transferencia debe ingresar el Id de la cuenta destino");
            }
            if (tipoTransferencia.getId().equals(Constant.Transaccion.ID_TRANSFERENCIA)) {
                if (transaccionDto.getCuentaDestino() == null) {
                    throw new Exception("Para realizar una transferencia se debe de ingresar una cuenta destino");
                }
                this.transferencia(modelMapper.map(cuentaDestino.getMensaje(), Cuenta.class), cuentaOrigen, transaccionDto.getMonto());
            } else {
                this.validarTipoTransaccion(tipoTransferencia, cuentaOrigen, transaccionDto.getMonto());
            }
            if (cuenta.isError()) {
                responseDto.setMensaje("La cuenta con el Id no existe");
            }
        } catch (Exception e) {
            responseDto.setError(true);
            responseDto.setMensaje("Error al realizar la transaccion: " + e.getMessage());
        }
        return responseDto;
    }

    private void validarTipoTransaccion(TipoTransaccion tipoTransaccion, Cuenta cuenta, Double monto) throws Exception {
        if (monto <= 0) {
            throw new Exception("El monto de debe ser positivo");
        }
        try {
            switch (tipoTransaccion.getTipo()) {
                case Constant.Transaccion.TIPO_CONSIGNACION:
                    consignacion(cuenta, monto);
                    break;
                case Constant.Transaccion.TIPO_RETIRO:
                    retiro(cuenta, monto);
                    break;
                default:
                    System.out.println("Seleccion incorrecta");
            }
        } catch (Exception e) {
            throw new Exception("Error al realizar la transaccion: " + e.getMessage());
        }
    }

    public void consignacion(Cuenta cuenta, Double monto) {
        cuenta.setSaldo(cuenta.getSaldo() + monto);
        cuenta.setUpdatedAt(new Date());
        cuentaRepository.save(cuenta);
    }

    public void retiro(Cuenta cuenta, Double monto) throws Exception {
        if (cuenta.getSaldo() < monto) {
            throw new Exception("Fondos insuficientes para el retiro");
        }
        cuenta.setSaldo(cuenta.getSaldo() - monto);
        cuenta.setUpdatedAt(new Date());
        cuentaRepository.save(cuenta);
    }

    public void transferencia(Cuenta cuentaDestino, Cuenta cuentaOrigen, Double monto) throws Exception {
        try {
            if (cuentaOrigen.getSaldo() < monto) {
                throw new Exception("Fondos insuficientes para la transaccion");
            }
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
            cuentaOrigen.setUpdatedAt(new Date());
            cuentaRepository.save(cuentaOrigen);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
            cuentaDestino.setUpdatedAt(new Date());
            cuentaRepository.save(cuentaDestino);
        } catch (Exception e) {
            throw new Exception("Error al realizar la transferencia: " + e.getMessage());
        }
    }
}
