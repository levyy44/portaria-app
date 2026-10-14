package com.empresa.portaria.controller;

import com.empresa.portaria.dto.ValidacaoRequest;
import com.empresa.portaria.dto.ValidacaoResponse;
import com.empresa.portaria.service.PortariaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PortariaController {

    private final PortariaService service;

    public PortariaController(PortariaService service) {
        this.service = service;
    }

    @PostMapping("/validar-saida")
    public ValidacaoResponse validar(@Valid @RequestBody ValidacaoRequest req) {
        return service.validar(req.codigoPatrimonio());
    }

    /** Limpa TODOS os dados (autorizações + logs). Útil para testes. */
    @DeleteMapping("/reset")
    public ResponseEntity<Map<String, String>> reset() {
        service.limparTudo();
        return ResponseEntity.ok(Map.of("mensagem", "Todos os dados foram removidos"));
    }
}
