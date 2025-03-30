package com.example.desafio.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static HikariDataSource dataSource;

    static {
        // Carregar configurações do arquivo de propriedades
        HikariConfig config = new HikariConfig();


        config.setJdbcUrl("jdbc:mysql://localhost:3306/desafiodb");
        config.setUsername("lucas");
        config.setPassword("1234567");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Configurações do pool de conexões
        config.setMaximumPoolSize(10); // Definir o tamanho máximo do pool

        // Criar o pool de conexões
        dataSource = new HikariDataSource(config);
    }

    // Método para obter uma conexão
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Método para fechar o pool de conexões
    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public static void main(String[] args) {
        // Teste de conexão
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Conexão bem-sucedida com o banco de dados!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
