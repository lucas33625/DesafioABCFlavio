package com.example.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private final JdbcTemplate jdbcTemplate;

    // Injeção de dependência do JdbcTemplate
    @Autowired
    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Método de exemplo para realizar uma operação no banco
    public void someDatabaseOperation() {
        // Exemplo de uma operação simples no banco de dados
        String sql = "SELECT COUNT(*) FROM sua_tabela";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

        // Aqui você pode manipular o resultado ou retornar algum valor
        System.out.println("Número de registros: " + count);
    }

    // Método para inserir um novo dado (exemplo)
    public void insertData(String name) {
        String sql = "INSERT INTO sua_tabela (name) VALUES (?)";
        jdbcTemplate.update(sql, name);
    }
}
