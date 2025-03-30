package com.example.desafio.controller;

import com.example.desafio.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseController {

    private final DatabaseService databaseService;

    // Injeção de dependência do serviço
    @Autowired
    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // Endpoint para executar a operação no banco
    @GetMapping("/db-operation")
    public String performDatabaseOperation() {
        // Chama o serviço para realizar a operação no banco
        databaseService.someDatabaseOperation();
        return "Operação no banco de dados realizada!";
    }

    // Endpoint para inserir dados no banco
    @GetMapping("/insert")
    public String insertData(String name) {
        databaseService.insertData(name);
        return "Dados inseridos com sucesso!";
    }
}
