package com.empresa.portaria.controller;

import com.empresa.portaria.model.LogPortaria;
import com.empresa.portaria.service.PortariaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final PortariaService service;

    public LogController(PortariaService service) {
        this.service = service;
    }

    @GetMapping
    public List<LogPortaria> listar(@RequestParam(defaultValue = "20") int limite) {
        return service.ultimosLogs(limite);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> limpar() {
        service.limparLogs();
        return ResponseEntity.ok(Map.of("mensagem", "Logs removidos"));
    }
}
