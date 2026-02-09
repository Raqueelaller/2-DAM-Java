package com.cliente.cliente.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cliente.cliente.dto.ClienteDTO;
import com.cliente.cliente.model.Cliente;
import com.cliente.cliente.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {
	
	private final ClienteRepository clienteRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public ClienteService(ClienteRepository clienteRepo) {
		this.clienteRepo=clienteRepo;
	}
	
	public Optional<Cliente> getClienteByEmail(String email){
		return clienteRepo.findByEmail(email);
	}
	
	public List<Cliente> getClienteByNombre(String nombre){
		return clienteRepo.findByNombre(nombre);	
	}
	
	public List<Cliente> getClienteByCiudad(String ciudad){
		return clienteRepo.findByCiudad(ciudad);
	}
	
	public List<Cliente> getAllClientes(){
		return clienteRepo.findAll();
	}
	
	public Cliente crearCliente(ClienteDTO dto) throws Exception {
		
		Cliente cliente = new Cliente();
		if(dto.getNombre().isBlank()|| dto.getNombre()==null) {
			throw new Exception("el nombre tiene que existir");
		}else {
			cliente.setNombre(dto.getNombre());
		}
	
		if(dto.getCiudad().isBlank()|| dto.getCiudad()==null) {
			throw new Exception("la ciudad tiene que existir");
		}else {
			cliente.setCiudad(dto.getCiudad());
		}
		
		if(dto.getEmail().isBlank()|| dto.getEmail()==null) {
			throw new Exception("el email tiene que existir");
		}else {
			cliente.setEmail(dto.getEmail());
		}
		
		cliente.setFechaAlta(LocalDateTime.now());
		
		if(dto.getSaldo().compareTo(BigDecimal.ZERO)<0) {
			throw new Exception("El saldo no puede ir en negativo");
		}else {
			cliente.setSaldo(dto.getSaldo());
		}
		String hash = passwordEncoder.encode(dto.getPassword());
		cliente.setPasswordHash(hash);
		return clienteRepo.save(cliente);
		
	}
	
	
	public Cliente actulizarCliente(ClienteDTO dto, String email) throws Exception {
		
		Cliente cliente = clienteRepo.findByEmail(email).orElseThrow(()-> new Exception("cliente no encontrado"));
		if(dto.getNombre().isBlank()|| dto.getNombre()==null) {
			throw new Exception("el nombre tiene que existir");
		}else {
			cliente.setNombre(dto.getNombre());
		}
	
		if(dto.getCiudad().isBlank()|| dto.getCiudad()==null) {
			throw new Exception("la ciudad tiene que existir");
		}else {
			cliente.setCiudad(dto.getCiudad());
		}
		
		if(dto.getEmail().isBlank()|| dto.getEmail()==null) {
			throw new Exception("el email tiene que existir");
		}else {
			cliente.setEmail(dto.getEmail());
		}
		
		cliente.setFechaActualizacion(LocalDateTime.now());
		
		if(dto.getSaldo().compareTo(BigDecimal.ZERO)<0) {
			throw new Exception("El saldo no puede ir en negativo");
		}else {
			cliente.setSaldo(dto.getSaldo());
		}
		
		
		return clienteRepo.save(cliente);
		
	} 
	
	public Cliente darBajaCliente(String email) throws Exception {
		
		Cliente cliente = clienteRepo.findByEmail(email).orElseThrow(()-> new Exception("cliente no encontrado"));
		
		cliente.setFechaBaja(LocalDateTime.now());
		
		return clienteRepo.save(cliente);
		
	} 
	
	@Transactional
	public void eliminarCliente(String email) throws Exception {
		
		Cliente cliente = clienteRepo.findByEmail(email).orElseThrow(()-> new Exception("cliente no encontrado"));
		
		
		 clienteRepo.delete(cliente);
		
	} 
	
	
}
