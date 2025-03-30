package com.example.desafio.dao;


import com.example.desafio.model.Cliente;
import com.example.desafio.util.ConexaoBD;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ClienteDAO {

    // CREATE - Inserir Cliente
    public void inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (name, email, phoneNumber) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getName());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getPhoneNumber());
            stmt.executeUpdate();

            // Obtém o ID gerado pelo banco
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ - Buscar Cliente por ID
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

    // READ - Listar Todos os Clientes
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

    // UPDATE - Atualizar Cliente
    public void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET name = ?, email = ?, phoneNumber = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getName());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getPhoneNumber());
            stmt.setLong(4, cliente.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE - Remover Cliente
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
