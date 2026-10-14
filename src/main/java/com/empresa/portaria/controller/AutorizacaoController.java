package com.empresa.portaria.controller;

import com.empresa.portaria.dto.AutorizacaoRequest;
import com.empresa.portaria.model.AutorizacaoSaida;
import com.empresa.portaria.service.AutorizacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/autorizacoes")
public class AutorizacaoController {

    private final AutorizacaoService service;

    public AutorizacaoController(AutorizacaoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutorizacaoSaida criar(@Valid @RequestBody AutorizacaoRequest req) {
        return service.criar(req);
    }

    @GetMapping
    public List<AutorizacaoSaida> listar() {
        return service.listar();
    }

    /** DELETE = cancelar (soft delete). */
    @DeleteMapping("/{id}")
    public AutorizacaoSaida cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }
}
