package com.cliente.cliente.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cliente.cliente.dto.AnuncioDTO;
import com.cliente.cliente.model.Anuncio;
import com.cliente.cliente.model.Cliente;
import com.cliente.cliente.repository.AnuncioRepository;
import com.cliente.cliente.repository.ClienteRepository;
import com.cliente.cliente.service.auth.AuthPrincipal;

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AuthPrincipal principal)) {
            throw new RuntimeException("No autenticado");
        }

        Cliente cliente = clienteRepository.findById(principal.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));

        Anuncio a = new Anuncio();
        a.setCliente(cliente);
        a.setTitulo(dto.getTitulo());
        a.setDescripcion(dto.getDescripcion());

        return anuncioRepository.save(a);
    }
}
