package com.grupos_musicales.grupos_musicales.service;

import org.springframework.stereotype.Service;

import com.grupos_musicales.grupos_musicales.repository.GrupoRepository;
import java.util.List;
import com.grupos_musicales.grupos_musicales.model.Grupo;

@Service
public class GrupoService {

	private final GrupoRepository grupoRepository;

	public GrupoService(GrupoRepository grupoRepository) {
		super();
		this.grupoRepository = grupoRepository;
	}
	
	public List<Grupo> getGruposByEstilosContaining(String estilo){
		return grupoRepository.findByEstilosContaining(estilo);
		
	}
	
	
	public List<Grupo> getGruposByAlbumesNombre(String nombreAlbum){
		return grupoRepository.findByAlbumesNombre(nombreAlbum);
	}
	
	public List<Grupo> getGruposByAlbumesFecha(String fecha){
		return grupoRepository.findByAlbumesFecha( fecha);
	}
	
	
	public Grupo getGrupoByNombre(String nombre) {
		return grupoRepository.findByNombre(nombre);
	}
	
	public List<Grupo> buscarAlbumPorSubcadena(String subcadena){
		return grupoRepository.buscarAlbumPorSubcadena(subcadena);
	}

	
	
}
