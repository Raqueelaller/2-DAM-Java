package com.demo.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.demo.DemoApplication;
import com.demo.demo.model.Saludo;
import com.demo.demo.service.SaludosService;

@RestController
@RequestMapping("/api")
public class SaludosController {

    private final DemoApplication demoApplication;
	
	private final SaludosService saludosService;
	
	
	
	public SaludosController(SaludosService saludosService, DemoApplication demoApplication) {
		super();
		this.saludosService = saludosService;
		this.demoApplication = demoApplication;
	}


	@PostMapping("/saludos/{nombre}")
	public String guardarNombre(@PathVariable String nombre) {
		String mensaje;
				try{
					saludosService.guardarNombre(nombre);
					mensaje=String.format("hola, buenas tarde %s", nombre);
				}catch(Exception x){
					mensaje= String.format("%s",x.toString());
				}
				
		return mensaje;
	}
	
	
	@GetMapping("/verSaludo/{nombre}")
	public Optional<Saludo> obtenerSaludo(@PathVariable String nombre) {
		return saludosService.obtenerSaludo(nombre);
	}
	
	@GetMapping("/todos10")
	public List<Saludo> listadoSaludos(){
		return saludosService.listadoSaludos();
	}

}
