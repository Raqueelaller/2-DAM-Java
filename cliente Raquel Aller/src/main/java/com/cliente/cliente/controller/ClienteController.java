package com.cliente.cliente.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import com.cliente.cliente.dto.ClienteDTO;
import com.cliente.cliente.model.Cliente;
import com.cliente.cliente.repository.ClienteRepository;
import com.cliente.cliente.service.ClienteService;

@RestController
@RequestMapping("/api")
public class ClienteController {

	
	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		super();
		this.clienteService = clienteService;
	}
	
	@GetMapping
	public List<Cliente> getAllClientes(){
		return clienteService.getAllClientes();
	}
	
	
	@GetMapping("/email/{email}")
	public Optional<Cliente> getByEmail( @PathVariable String email){
		return clienteService.getClienteByEmail(email);
	}
	
	@GetMapping("/nombre/{nombre}")
	public List<Cliente> getByNombre(@PathVariable String nombre){
		return clienteService.getClienteByNombre(nombre);
	}
	
	@PostMapping("/crear")
	public void crearCliente(@RequestBody ClienteDTO dto) {
		try {
		 clienteService.crearCliente(dto);
		 
		}catch (Exception x){
			System.out.println(x.getMessage());
		}
		
	}
	
	@PutMapping("/actualizar/{email}")
	public void actualizarCliente(@PathVariable String email, @RequestBody ClienteDTO dto) {
		try {
			clienteService.actulizarCliente(dto, email);
		}catch(Exception x) {
			System.out.println(x.getMessage());
		}
	}
	
	@PutMapping("/bajar/{email}")
	public void darBajaCliente(@PathVariable String email) {
		try {
			clienteService.darBajaCliente(email);
		}catch(Exception x) {
			System.out.println(x.getMessage());
		}
	}
	
	@DeleteMapping("/eliminar/{email}")
	public void eliminar(@PathVariable String email) throws Exception {
		clienteService.eliminarCliente(email);
	}
	
	
}
