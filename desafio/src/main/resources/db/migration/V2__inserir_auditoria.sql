CREATE TABLE IF NOT EXISTS cliente_auditoria (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   cliente_id INT NOT NULL,
                                   campo_alterado VARCHAR(255) NOT NULL,
                                   valor_antigo VARCHAR(255),
                                   valor_novo VARCHAR(255),
                                   data_alteracao DATETIME NOT NULL
);
