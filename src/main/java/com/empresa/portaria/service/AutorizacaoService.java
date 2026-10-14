package com.empresa.portaria.service;

import com.empresa.portaria.dto.AutorizacaoRequest;
import com.empresa.portaria.model.AutorizacaoSaida;
import com.empresa.portaria.repository.AutorizacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AutorizacaoService {

    private final AutorizacaoRepository repository;

    public AutorizacaoService(AutorizacaoRepository repository) {
        this.repository = repository;
    }

    /** Cria uma autorização. Só pode haver UMA autorização ATIVA por código. */
    @Transactional
    public AutorizacaoSaida criar(AutorizacaoRequest req) {
        String codigo = req.codigoPatrimonio().trim();

        boolean jaAtiva = repository.findByCodigoPatrimonioIgnoreCaseOrderByDataCriacaoDescIdDesc(codigo)
                .stream().anyMatch(a -> "ATIVA".equals(a.getStatus()));
        if (jaAtiva) {
            throw new IllegalStateException("Já existe uma autorização ATIVA para o código " + codigo
                    + ". Cancele-a antes de cadastrar outra.");
        }

        AutorizacaoSaida a = new AutorizacaoSaida();
        a.setCodigoPatrimonio(codigo);
        a.setNomeFuncionario(req.nomeFuncionario().trim());
        a.setMatriculaFuncionario(req.matriculaFuncionario().trim());
        a.setAutorizadoPor(req.autorizadoPor().trim());
        a.setDataValidade(req.dataValidade());
        return repository.save(a);
    }

    public List<AutorizacaoSaida> listar() {
        return repository.findAllByOrderByDataCriacaoDescIdDesc();
    }

    /** "Cancelar" = marcar como cancelada (mantém histórico para auditoria). */
    @Transactional
    public AutorizacaoSaida cancelar(Long id) {
        AutorizacaoSaida a = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Autorização não encontrada: " + id));
        a.setCancelada(true);
        return repository.save(a);
    }
}
