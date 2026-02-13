package com.grupos_musicales.grupos_musicales.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="grupos")
public class Grupo {
	
	@Id
	private String id;
	
	private String nombre;
	
	private String pais;
	
	private ArrayList<String> estilos;
	
	private ArrayList<Album> albumes;
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public ArrayList<String> getEstilos() {
		return estilos;
	}

	public void setEstilos(ArrayList<String> estilos) {
		this.estilos = estilos;
	}

	public String getId() {
		return id;
	}

	public ArrayList<Album> getAlbumes() {
		return albumes;
	}

	public void setAlbumes(ArrayList<Album> albumes) {
		this.albumes = albumes;
	}
	
	
	
	

}
