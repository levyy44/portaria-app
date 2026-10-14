package com.empresa.portaria.repository;

import com.empresa.portaria.model.AutorizacaoSaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AutorizacaoRepository extends JpaRepository<AutorizacaoSaida, Long> {

    /** Autorizações de um código, da mais recente para a mais antiga. */
    List<AutorizacaoSaida> findByCodigoPatrimonioIgnoreCaseOrderByDataCriacaoDescIdDesc(String codigo);

    List<AutorizacaoSaida> findAllByOrderByDataCriacaoDescIdDesc();
}
