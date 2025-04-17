package com.example.desafio.dao;


import com.example.desafio.model.Cliente;
import com.example.desafio.util.ConexaoBD;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ClienteDAO {


    private final JdbcTemplate jdbcTemplate;

    public ClienteDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (name, email, phoneNumber) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getName());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getPhoneNumber());
            stmt.executeUpdate();


            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getLong(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Cliente buscarClientePorId(Long id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phoneNumber")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public boolean emailExist(String email) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE email = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configura o parâmetro da consulta
            stmt.setString(1, email);

            // Executa a consulta e obtém o resultado
            ResultSet rs = stmt.executeQuery();

            // Verifica se o email existe no banco
            if (rs.next()) {
                int count = rs.getInt(1); // O resultado da contagem estará na primeira coluna
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean atualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET name = ?, email = ?, phoneNumber = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getName());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getPhoneNumber());
            stmt.setLong(4, cliente.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void deletarCliente(Long id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
