package com.example.desafio.dao;


import com.example.desafio.model.Cliente;
import com.example.desafio.util.ConexaoBD;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public boolean emailExist(String email, Long id) {
        String sql;
        Integer count;

        if (id != null) {
            sql = "SELECT COUNT(*) FROM clientes WHERE email = ? AND id != ?";
            count = jdbcTemplate.queryForObject(sql, Integer.class, email, id);
        } else {
            sql = "SELECT COUNT(*) FROM clientes WHERE email = ?";
            count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        }

        return count != null && count > 0;
    }



    public boolean atualizarCliente(Cliente cliente) {
        String sqlUpdate = "UPDATE clientes SET name = ?, email = ?, phoneNumber = ? WHERE id = ?";
        String sqlSelect = "SELECT name, email, phoneNumber FROM clientes WHERE id = ?";
        String sqlAuditoria = "INSERT INTO cliente_auditoria (cliente_id, campo_alterado, valor_antigo, valor_novo, data_alteracao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection()) {

            // Primeiro: buscar o cliente atual do banco
            PreparedStatement selectStmt = conn.prepareStatement(sqlSelect);
            selectStmt.setLong(1, cliente.getId());

            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                return false; // cliente não encontrado
            }

            String nomeAntigo = rs.getString("name");
            String emailAntigo = rs.getString("email");
            String telefoneAntigo = rs.getString("phoneNumber");

            // Segundo: comparar e salvar auditoria
            PreparedStatement auditStmt = conn.prepareStatement(sqlAuditoria);
            Timestamp agora = Timestamp.valueOf(LocalDateTime.now());

            if (!Objects.equals(nomeAntigo, cliente.getName())) {
                auditStmt.setLong(1, cliente.getId());
                auditStmt.setString(2, "name");
                auditStmt.setString(3, nomeAntigo);
                auditStmt.setString(4, cliente.getName());
                auditStmt.setTimestamp(5, agora);
                auditStmt.executeUpdate();
            }

            if (!Objects.equals(emailAntigo, cliente.getEmail())) {
                auditStmt.setLong(1, cliente.getId());
                auditStmt.setString(2, "email");
                auditStmt.setString(3, emailAntigo);
                auditStmt.setString(4, cliente.getEmail());
                auditStmt.setTimestamp(5, agora);
                auditStmt.executeUpdate();
            }

            if (!Objects.equals(telefoneAntigo, cliente.getPhoneNumber())) {
                auditStmt.setLong(1, cliente.getId());
                auditStmt.setString(2, "phoneNumber");
                auditStmt.setString(3, telefoneAntigo);
                auditStmt.setString(4, cliente.getPhoneNumber());
                auditStmt.setTimestamp(5, agora);
                auditStmt.executeUpdate();
            }

            // Terceiro: atualizar o cliente
            PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate);
            updateStmt.setString(1, cliente.getName());
            updateStmt.setString(2, cliente.getEmail());
            updateStmt.setString(3, cliente.getPhoneNumber());
            updateStmt.setLong(4, cliente.getId());

            updateStmt.executeUpdate();

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
