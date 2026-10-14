package com.empresa.portaria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Autorização de saída de um bem (notebook) identificado pela plaqueta. */
@Entity
public class AutorizacaoSaida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigoPatrimonio;

    @Column(nullable = false)
    private String nomeFuncionario;

    @Column(nullable = false)
    private String matriculaFuncionario;

    @Column(nullable = false)
    private String autorizadoPor;

    /** Nulo = nunca expira. */
    private LocalDateTime dataValidade;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    private boolean cancelada = false;

    /**
     * Status calculado (não é persistido):
     * CANCELADA > EXPIRADA > ATIVA.
     */
    @Transient
    public String getStatus() {
        if (cancelada) return "CANCELADA";
        if (dataValidade != null && !dataValidade.isAfter(LocalDateTime.now())) return "EXPIRADA";
        return "ATIVA";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoPatrimonio() { return codigoPatrimonio; }
    public void setCodigoPatrimonio(String v) { this.codigoPatrimonio = v; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String v) { this.nomeFuncionario = v; }
    public String getMatriculaFuncionario() { return matriculaFuncionario; }
    public void setMatriculaFuncionario(String v) { this.matriculaFuncionario = v; }
    public String getAutorizadoPor() { return autorizadoPor; }
    public void setAutorizadoPor(String v) { this.autorizadoPor = v; }
    public LocalDateTime getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDateTime v) { this.dataValidade = v; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime v) { this.dataCriacao = v; }
    public boolean isCancelada() { return cancelada; }
    public void setCancelada(boolean v) { this.cancelada = v; }
}
