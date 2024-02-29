package com.superfinanciera.mycrud;

import com.superfinanciera.mycrud.model.EstadoCuenta;
import com.superfinanciera.mycrud.model.TipoCuenta;
import com.superfinanciera.mycrud.model.TipoTransaccion;
import com.superfinanciera.mycrud.repositories.EstadoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoCuentaRepository;
import com.superfinanciera.mycrud.repositories.TipoTransaccionRespository;
import com.superfinanciera.mycrud.utils.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.PostConstruct;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class MyCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCrudApplication.class, args);
	}

	@Autowired
	private TipoCuentaRepository tipoCuentaRepository;

	@Autowired
	private EstadoCuentaRepository estadoCuentaRepository;

	@Autowired
	private TipoTransaccionRespository tipoTransaccionRespository;

	@PostConstruct
	public void init() {
			System.out.println("Se van a crear los registros");
			TipoCuenta tipoCuenta = new TipoCuenta();
			tipoCuenta.setId(Constant.ID_CUENTA_AHORRO);
			tipoCuenta.setTipo(Constant.NOMBRE_CUENTA_AHORRO);
			tipoCuenta.setIsEliminado("false");
			tipoCuentaRepository.save(tipoCuenta);

			TipoCuenta tipoCuenta1 = new TipoCuenta();
			tipoCuenta1.setId(Constant.ID_CUENTA_CORRIENTE);
			tipoCuenta1.setTipo(Constant.NOMBRE_CUENTA_CORRIENTE);
			tipoCuenta1.setIsEliminado("false");
			tipoCuentaRepository.save(tipoCuenta1);

			this.guardarEstadoCuenta();
			this.iniciar();
	}
	public void guardarEstadoCuenta(){
		System.out.println("Se van a crear los registros de los estados de cuenta");
		EstadoCuenta estadoCuenta = new EstadoCuenta();
		estadoCuenta.setIdEstadoCuenta(Constant.EstadoCuenta.ID_ACTIVA);
		estadoCuenta.setNombre(Constant.EstadoCuenta.ESTADO_CUENTA_ACTIVA);
		estadoCuentaRepository.save(estadoCuenta);

		EstadoCuenta estadoCuenta1 = new EstadoCuenta();
		estadoCuenta1.setIdEstadoCuenta(Constant.EstadoCuenta.ID_INACTIVA);
		estadoCuenta1.setNombre(Constant.EstadoCuenta.ESTADO_CUENTA_INACTIVA);
		estadoCuentaRepository.save(estadoCuenta1);

		EstadoCuenta estadoCuenta2 = new EstadoCuenta();
		estadoCuenta2.setIdEstadoCuenta(Constant.EstadoCuenta.ID_CANCELADA);
		estadoCuenta2.setNombre(Constant.EstadoCuenta.ESTADO_CUENTA_CANCELADA);
		estadoCuentaRepository.save(estadoCuenta2);
	}

	public void iniciar(){
		System.out.println("Se  van a crear los registros de las transacciones");
		TipoTransaccion tipoTransaccion = new TipoTransaccion();
		tipoTransaccion.setId(Constant.Transaccion.ID_CONSIGNACION);
		tipoTransaccion.setTipo(Constant.Transaccion.TIPO_CONSIGNACION);
		tipoTransaccionRespository.save(tipoTransaccion);

		TipoTransaccion tipoTransaccion1 = new TipoTransaccion();
		tipoTransaccion1.setId(Constant.Transaccion.ID_RETIRO);
		tipoTransaccion1.setTipo(Constant.Transaccion.TIPO_RETIRO);
		tipoTransaccionRespository.save(tipoTransaccion1);

		TipoTransaccion tipoTransaccion2 = new TipoTransaccion();
		tipoTransaccion2.setId(Constant.Transaccion.ID_TRANSFERENCIA);
		tipoTransaccion2.setTipo(Constant.Transaccion.TIPO_TRANSFERENCIA);
		tipoTransaccionRespository.save(tipoTransaccion2);
	}

	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
