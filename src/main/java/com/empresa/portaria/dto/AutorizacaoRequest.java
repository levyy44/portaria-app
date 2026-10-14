package com.empresa.portaria.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/** Payload de criação de autorização. dataValidade é opcional. */
public record AutorizacaoRequest(
        @NotBlank(message = "Código da plaqueta é obrigatório") String codigoPatrimonio,
        @NotBlank(message = "Nome do funcionário é obrigatório") String nomeFuncionario,
        @NotBlank(message = "Matrícula é obrigatória") String matriculaFuncionario,
        @NotBlank(message = "Nome de quem autorizou é obrigatório") String autorizadoPor,
        LocalDateTime dataValidade) {
}
