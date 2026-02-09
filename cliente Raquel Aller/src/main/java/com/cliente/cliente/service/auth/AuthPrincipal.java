package com.cliente.cliente.service.auth;

public class AuthPrincipal {
	
	private Integer clienteId;
	private String email;
	
	public AuthPrincipal(Integer clienteId, String email) {
		super();
		this.clienteId = clienteId;
		this.email = email;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
