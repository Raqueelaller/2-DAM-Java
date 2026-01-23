package com.demo.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.demo.model.Saludo;


public interface SaludoRepository extends JpaRepository<Saludo ,Integer> {
	
	Optional<Saludo> findByNombre(String nombre);
	
	Optional<Saludo> findFirstByNombreOrderByFechaDesc(String nombre);
	
		
	List<Saludo> findFirst10ByOrderByFechaDesc();
 

}
