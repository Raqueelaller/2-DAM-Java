package com.cliente.cliente.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cliente.cliente.dto.ClienteDTO;
import com.cliente.cliente.model.Cliente;
import com.cliente.cliente.repository.ClienteRepository;


@Service
public class AuthService {

	private final ClienteRepository clienteRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthService(ClienteRepository clienteRepository,
					   PasswordEncoder passwordEncoder,
					   JwtService jwtService) {
		this.clienteRepository = clienteRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public String login(ClienteDTO dto) {

		
		Cliente cliente = clienteRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new RuntimeException("Email no existe"));

		
		if(!passwordEncoder.matches(dto.getPassword(), cliente.getPasswordHash())) {
			throw new RuntimeException("Password incorrecta");
		}

		
		String token = jwtService.generarToken(cliente.getEmail());
		return token;
	}
}
