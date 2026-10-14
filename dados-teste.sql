-- Dados de exemplo. Cole no console H2 (http://localhost:8080/h2-console)
-- JDBC URL: jdbc:h2:mem:portariadb  | usuário: sa | senha: (vazia)
-- A aplicação precisa estar rodando (as tabelas são criadas na inicialização).

-- 1) ATIVA com validade futura -> LIBERADO
INSERT INTO autorizacao_saida (codigo_patrimonio, nome_funcionario, matricula_funcionario, autorizado_por, data_validade, data_criacao, cancelada)
VALUES ('NB00123', 'João Silva', '12345', 'Maria Santos', DATEADD('DAY', 30, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, FALSE);

-- 2) ATIVA sem validade (não expira) -> LIBERADO
INSERT INTO autorizacao_saida (codigo_patrimonio, nome_funcionario, matricula_funcionario, autorizado_por, data_validade, data_criacao, cancelada)
VALUES ('NB00124', 'Ana Souza', '23456', 'Carlos Lima', NULL, CURRENT_TIMESTAMP, FALSE);

-- 3) EXPIRADA -> BLOQUEADO (Autorização expirada)
INSERT INTO autorizacao_saida (codigo_patrimonio, nome_funcionario, matricula_funcionario, autorizado_por, data_validade, data_criacao, cancelada)
VALUES ('NB00125', 'Pedro Alves', '34567', 'Maria Santos', DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('DAY', -10, CURRENT_TIMESTAMP), FALSE);

-- 4) CANCELADA -> BLOQUEADO (Autorização cancelada)
INSERT INTO autorizacao_saida (codigo_patrimonio, nome_funcionario, matricula_funcionario, autorizado_por, data_validade, data_criacao, cancelada)
VALUES ('NB00126', 'Luiza Costa', '45678', 'Carlos Lima', DATEADD('DAY', 10, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, TRUE);

-- 5) Código NB99999 não existe -> BLOQUEADO (Bem não cadastrado)
