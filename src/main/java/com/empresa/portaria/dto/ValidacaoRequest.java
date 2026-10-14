package com.empresa.portaria.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidacaoRequest(@NotBlank(message = "Código é obrigatório") String codigoPatrimonio) {
}
