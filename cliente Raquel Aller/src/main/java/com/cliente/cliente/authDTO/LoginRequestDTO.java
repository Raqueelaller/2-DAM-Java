package com.cliente.cliente.authDTO;



public class LoginRequestDTO {
	
	private String email;
	private String password;
	
	
	public LoginRequestDTO() {
		super();
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws Exception {
		
		if(email==null) {
			throw new Exception("El email no puede ser null");
		}email=email.trim();
		if(email.isBlank()) {
			throw new Exception("El email no puede ser null");
		}
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) throws Exception {
		if(password==null||password.isBlank()) {
			throw new Exception("La contraseña no puede ser null");

		}
		this.password = password;
	}

}
