
package com.cliente.cliente.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class ClienteDTO {
	
	private String nombre;
	private String email;
	
	private LocalDateTime fechaAlta;
	

	private BigDecimal saldo;

	
	private String ciudad;
	
	
	private LocalDateTime fechaActualizacion;
	
	
	private LocalDateTime fechaBaja;
	
	private String password;


	public ClienteDTO() {
		super();
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}


	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public BigDecimal getSaldo() {
		return saldo;
	}


	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}


	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}


	public LocalDateTime getFechaBaja() {
		return fechaBaja;
	}


	public void setFechaBaja(LocalDateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
