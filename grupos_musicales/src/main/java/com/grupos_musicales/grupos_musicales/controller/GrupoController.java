package com.grupos_musicales.grupos_musicales.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.grupos_musicales.grupos_musicales.model.Grupo;
import com.grupos_musicales.grupos_musicales.service.GrupoService;

@RestController
public class GrupoController {
	
	private final GrupoService grupoService;

	public GrupoController(GrupoService grupoService) {
		super();
		this.grupoService = grupoService;
	}
	
	@GetMapping("/nombre/{nombre}")
	public Grupo getGrupoByNombre (@PathVariable String nombre) {
		return grupoService.getGrupoByNombre(nombre);
	}
	
	@GetMapping("/fecha/{fecha}")
	public List<Grupo> getGruposByAlbumesFecha (@PathVariable String fecha){
		return grupoService.getGruposByAlbumesFecha(fecha);
	}
	
	@GetMapping("/subcadena/{subcadena}")
	public List<Grupo> buscarAlbumPorSubcadena(@PathVariable String subcadena){
		return grupoService.buscarAlbumPorSubcadena(subcadena);
	}

}
