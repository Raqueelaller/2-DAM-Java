package com.demo.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.demo.model.Saludo;
import com.demo.demo.repository.SaludoRepository;

@Service
public class SaludosService {
		
	private final SaludoRepository saludoRepository;
	
	
	
	public SaludosService(SaludoRepository saludoRepository) {
		super();
		this.saludoRepository = saludoRepository;
	}

	Optional<Saludo> getSaludoByNombre(String nombre){

		
		return saludoRepository.findByNombre(nombre);
	}
	
	
	public Saludo guardarNombre(String nombre) {
		Saludo saludo = new Saludo();

		if(nombre.isBlank() ) {
			throw new IllegalArgumentException("No puede estar el nombre vacío");
		}

		saludo.setNombre(nombre);
		saludo.setFecha(LocalDateTime.now());
		
		return saludoRepository.save(saludo);
		
	}
	
	
	public Optional<Saludo> obtenerSaludo(String nombre)  {
		return saludoRepository.findFirstByNombreOrderByFechaDesc(nombre);
	}
	
	public List<Saludo> listadoSaludos(){
		return saludoRepository.findFirst10ByOrderByFechaDesc();
	}

} 
