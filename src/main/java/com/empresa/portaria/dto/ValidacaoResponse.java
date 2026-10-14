package com.empresa.portaria.dto;

import java.time.LocalDateTime;

/** Resposta da validação de saída. */
public record ValidacaoResponse(boolean liberado, String mensagem, AutorizacaoInfo autorizacao) {

    public record AutorizacaoInfo(String codigoPatrimonio, String nomeFuncionario,
                                  String matriculaFuncionario, String autorizadoPor,
                                  LocalDateTime dataValidade) {
    }

    public static ValidacaoResponse bloqueado(String mensagem) {
        return new ValidacaoResponse(false, mensagem, null);
    }
}
