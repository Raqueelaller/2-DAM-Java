package com.cliente.cliente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cliente.cliente.model.Anuncio;

public interface AnuncioRepository extends JpaRepository<Anuncio, Integer> {
	
	@Query(value = """
			SELECT * 
			FROM anuncio 
			WHERE titulo LIKE CONCAT('%', :texto, '%')
			ORDER BY id DESC
			LIMIT 5
			""", nativeQuery = true)
			List<Anuncio> buscarTop5PorTitulo(@Param("texto") String texto);

	
	
	
}
