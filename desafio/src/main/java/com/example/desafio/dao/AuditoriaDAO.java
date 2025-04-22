package com.example.desafio.dao;

import com.example.desafio.model.ClienteAudit;
import com.example.desafio.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaDAO {

    public List<ClienteAudit> buscarAuditoriasPorCliente(Long clienteId) {
        List<ClienteAudit> auditorias = new ArrayList<>();
        String sql = "SELECT * FROM cliente_auditoria WHERE cliente_id = ? ORDER BY data_alteracao DESC";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ClienteAudit audit = new ClienteAudit();

                audit.setCampoAlterado(rs.getString("campo_alterado"));
                audit.setValorAntigo(rs.getString("valor_antigo"));
                audit.setValorNovo(rs.getString("valor_novo"));
                audit.setDataAlteracao(rs.getTimestamp("data_alteracao").toLocalDateTime());

                auditorias.add(audit);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auditorias;
    }
}

