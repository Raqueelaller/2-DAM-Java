package com.cliente.cliente.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.cliente.cliente.dto.ClienteDTO;
import com.cliente.cliente.model.Cliente;
import com.cliente.cliente.service.ClienteService;

@RestController
@RequestMapping("/api")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<Cliente> getAllClientes(){
        return clienteService.getAllClientes();
    }

    //
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/email/{email}")
    public Optional<Cliente> getByEmail(@PathVariable String email){
        return clienteService.getClienteByEmail(email);
    }

    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/nombre/{nombre}")
    public List<Cliente> getByNombre(@PathVariable String nombre){
        return clienteService.getClienteByNombre(nombre);
    }

   
    @PostMapping("/crear")
    public void crearCliente(@RequestBody ClienteDTO dto) {
        try {
			clienteService.crearCliente(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/actualizar/{email}")
    public void actualizarCliente(@PathVariable String email, @RequestBody ClienteDTO dto) {
        try {
			clienteService.actulizarCliente(dto, email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // 🔒
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/bajar/{email}")
    public void darBajaCliente(@PathVariable String email) {
        try {
			clienteService.darBajaCliente(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // 🔒
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/eliminar/{email}")
    public void eliminar(@PathVariable String email) throws Exception {
        clienteService.eliminarCliente(email);
    }
}
