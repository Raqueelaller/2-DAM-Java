package com.cliente.cliente.controller;

import org.springframework.web.bind.annotation.*;

import com.cliente.cliente.dto.ClienteDTO;
import com.cliente.cliente.service.auth.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public String login(@RequestBody ClienteDTO dto){
		return authService.login(dto);
	}
}
