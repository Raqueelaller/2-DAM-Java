package com.cliente.cliente.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cliente.cliente.dto.AnuncioDTO;
import com.cliente.cliente.model.Anuncio;
import com.cliente.cliente.model.Cliente;
import com.cliente.cliente.repository.AnuncioRepository;
import com.cliente.cliente.repository.ClienteRepository;

@Service
public class AnuncioService {

    private final AnuncioRepository anuncioRepository;
    private final ClienteRepository clienteRepository;

    public AnuncioService(AnuncioRepository anuncioRepository,
                          ClienteRepository clienteRepository) {
        this.anuncioRepository = anuncioRepository;
        this.clienteRepository = clienteRepository;
    }

   
    public List<Anuncio> buscarPorTitulo(String texto) {
        if (texto == null || texto.trim().isEmpty()) return List.of();
        return anuncioRepository.buscarTop5PorTitulo(texto.trim());
    }

    
    public Anuncio insertar(AnuncioDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));

        Anuncio a = new Anuncio();
        a.setCliente(cliente);
        a.setTitulo(dto.getTitulo());
        a.setDescripcion(dto.getDescripcion());

        return anuncioRepository.save(a);
    }
}
