package com.empresa.portaria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Registro de auditoria: toda leitura na portaria (liberada ou bloqueada) vira um log. */
@Entity
@Table(name = "log_portaria")
public class LogPortaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoLido;
    private boolean liberado;
    private String mensagem;
    private String nomeFuncionario;
    private LocalDateTime dataHora = LocalDateTime.now();

    public LogPortaria() {}

    public LogPortaria(String codigoLido, boolean liberado, String mensagem, String nomeFuncionario) {
        this.codigoLido = codigoLido;
        this.liberado = liberado;
        this.mensagem = mensagem;
        this.nomeFuncionario = nomeFuncionario;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getCodigoLido() { return codigoLido; }
    public boolean isLiberado() { return liberado; }
    public String getMensagem() { return mensagem; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public LocalDateTime getDataHora() { return dataHora; }
}
