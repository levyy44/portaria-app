# Portaria App — Controle de Saída de Notebooks (protótipo)

Sistema standalone (sem ERP, sem autenticação) para validar o fluxo de saída de notebooks pela portaria: o admin autoriza, o porteiro bipa a plaqueta, a tela mostra **✅ LIBERADO** ou **❌ BLOQUEADO** e tudo é registrado em log.

**Stack:** Java 17, Spring Boot 3.2, Maven, H2 em memória, HTML + JS puro + Bootstrap (CDN).
> As telas carregam Bootstrap e Font Awesome via CDN — é preciso internet no navegador.

## Como executar
```bash
mvn spring-boot:run
```
1. Admin: http://localhost:8080/admin — cadastre autorizações.
2. Portaria: http://localhost:8080/portaria — bipe ou digite o código e pressione Enter.
3. Console H2: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:portariadb`, usuário `sa`, senha vazia).

Dados de exemplo: rode `dados-teste.sql` no console H2 (ou use os `curl` abaixo).

## Leitor de código de barras
Funciona com leitores USB que emulam teclado (código + Enter). O campo da portaria fica sempre focado (autofocus + refocus a cada 0,7s e a cada clique). Mantenha a aba da portaria em primeiro plano.

## Regras de negócio
- **ATIVA** = não cancelada e validade no futuro (ou sem validade). **EXPIRADA** e **CANCELADA** completam o status.
- Só pode existir uma autorização ATIVA por código (o cadastro retorna 409 se já houver). Na validação, se houver mais de uma, vale a mais recente.
- Código inexistente → bloqueado: "Bem não cadastrado". Também há "Autorização expirada" e "Autorização cancelada".
- Toda leitura gera registro em `log_portaria`.
- Cancelar não apaga: marca como cancelada (histórico preservado).

## Exemplos com curl
```bash
# Criar autorização (com validade)
curl -X POST localhost:8080/api/autorizacoes -H "Content-Type: application/json" -d '{
  "codigoPatrimonio":"NB00123","nomeFuncionario":"João Silva","matriculaFuncionario":"12345",
  "autorizadoPor":"Maria Santos","dataValidade":"2026-12-31T18:00:00"}'

# Criar autorização sem validade
curl -X POST localhost:8080/api/autorizacoes -H "Content-Type: application/json" -d '{
  "codigoPatrimonio":"NB00124","nomeFuncionario":"Ana Souza","matriculaFuncionario":"23456","autorizadoPor":"Carlos Lima"}'

# Listar
curl localhost:8080/api/autorizacoes

# Validar (liberado)
curl -X POST localhost:8080/api/validar-saida -H "Content-Type: application/json" -d '{"codigoPatrimonio":"NB00123"}'

# Validar (bloqueado - não cadastrado)
curl -X POST localhost:8080/api/validar-saida -H "Content-Type: application/json" -d '{"codigoPatrimonio":"NB99999"}'

# Cancelar autorização de id 1
curl -X DELETE localhost:8080/api/autorizacoes/1

# Últimas leituras / limpar logs / reset total
curl "localhost:8080/api/logs?limite=5"
curl -X DELETE localhost:8080/api/logs
curl -X DELETE localhost:8080/api/reset
```

## Endpoints
| Método | Rota | Descrição |
|---|---|---|
| POST | `/api/autorizacoes` | Criar autorização |
| GET | `/api/autorizacoes` | Listar todas |
| DELETE | `/api/autorizacoes/{id}` | Cancelar |
| POST | `/api/validar-saida` | Validar código |
| GET | `/api/logs?limite=N` | Últimas leituras |
| DELETE | `/api/logs` | Limpar logs |
| DELETE | `/api/reset` | Limpar tudo |

## Layout visual
Pasta `layout/`: mockups PNG das telas e do fluxograma + `07-mockups.md` (ASCII, paleta, tipografia, animações, ícones, fluxograma e dicas de exportação para Figma/Canva).

## Observações
- H2 em memória: dados somem ao reiniciar. Para persistir, use a URL de arquivo comentada em `application.properties`.
- CORS aberto e sem autenticação: apenas para protótipo.
