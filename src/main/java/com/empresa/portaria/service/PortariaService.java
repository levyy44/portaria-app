package com.empresa.portaria.service;

import com.empresa.portaria.dto.ValidacaoResponse;
import com.empresa.portaria.dto.ValidacaoResponse.AutorizacaoInfo;
import com.empresa.portaria.model.AutorizacaoSaida;
import com.empresa.portaria.model.LogPortaria;
import com.empresa.portaria.repository.AutorizacaoRepository;
import com.empresa.portaria.repository.LogRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PortariaService {

    private final AutorizacaoRepository autorizacaoRepository;
    private final LogRepository logRepository;

    public PortariaService(AutorizacaoRepository autorizacaoRepository, LogRepository logRepository) {
        this.autorizacaoRepository = autorizacaoRepository;
        this.logRepository = logRepository;
    }

    /** Valida o código lido e SEMPRE grava log (liberado ou bloqueado). */
    @Transactional
    public ValidacaoResponse validar(String codigoLido) {
        String codigo = codigoLido.trim();
        // Lista já vem da mais recente para a mais antiga
        List<AutorizacaoSaida> todas =
                autorizacaoRepository.findByCodigoPatrimonioIgnoreCaseOrderByDataCriacaoDescIdDesc(codigo);

        ValidacaoResponse resposta;
        String nome = null;

        if (todas.isEmpty()) {
            resposta = ValidacaoResponse.bloqueado("Bem não cadastrado");
        } else {
            // Regra: se houver várias ativas, considera a mais recente
            AutorizacaoSaida ativa = todas.stream()
                    .filter(a -> "ATIVA".equals(a.getStatus()))
                    .findFirst().orElse(null);

            if (ativa != null) {
                nome = ativa.getNomeFuncionario();
                resposta = new ValidacaoResponse(true, "Saída autorizada", new AutorizacaoInfo(
                        ativa.getCodigoPatrimonio(), ativa.getNomeFuncionario(),
                        ativa.getMatriculaFuncionario(), ativa.getAutorizadoPor(), ativa.getDataValidade()));
            } else {
                // Motivo baseado na autorização mais recente
                String status = todas.get(0).getStatus();
                resposta = ValidacaoResponse.bloqueado(
                        "CANCELADA".equals(status) ? "Autorização cancelada"
                        : "EXPIRADA".equals(status) ? "Autorização expirada"
                        : "Bem não autorizado para saída");
            }
        }

        logRepository.save(new LogPortaria(codigo, resposta.liberado(), resposta.mensagem(), nome));
        return resposta;
    }

    public List<LogPortaria> ultimosLogs(int limite) {
        return logRepository.findAllByOrderByDataHoraDescIdDesc(PageRequest.of(0, Math.max(1, Math.min(limite, 500))));
    }

    @Transactional
    public void limparLogs() {
        logRepository.deleteAllInBatch();
    }

    @Transactional
    public void limparTudo() {
        logRepository.deleteAllInBatch();
        autorizacaoRepository.deleteAllInBatch();
    }
}
