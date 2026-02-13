package com.grupos_musicales.grupos_musicales.model;


public class Album {
	
	private String nombre;
	private String fecha;
	
	public Album(String nombre, String fecha) {
		super();
		
		this.nombre = nombre;
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	

}
