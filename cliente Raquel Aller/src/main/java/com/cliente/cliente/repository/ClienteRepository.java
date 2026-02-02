package com.cliente.cliente.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cliente.cliente.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	Optional<Cliente> findByEmail(String email);
	
	List<Cliente> findByNombre(String nombre);
	
	List<Cliente> findByCiudad(String ciudad);
	
	List<Cliente> findByFechaAltaBefore(LocalDateTime fecha);
	
	long countByCiudadIgnoreCase(String ciudad);

}
