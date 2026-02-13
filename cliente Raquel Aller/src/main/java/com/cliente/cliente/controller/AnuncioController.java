package com.cliente.cliente.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cliente.cliente.dto.AnuncioDTO;
import com.cliente.cliente.model.Anuncio;
import com.cliente.cliente.service.AnuncioService;

@RestController
@RequestMapping("/api/anuncios")
public class AnuncioController {

    private final AnuncioService anuncioService;

    public AnuncioController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }

    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/buscar/{texto}")
    public List<Anuncio> buscar(@PathVariable String texto) {
        return anuncioService.buscarPorTitulo(texto);
    }

   
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/insertar")
    public Anuncio insertar(@RequestBody AnuncioDTO dto) {
        return anuncioService.insertar(dto);
    }
}
