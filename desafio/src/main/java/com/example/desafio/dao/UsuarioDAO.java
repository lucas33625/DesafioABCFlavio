package com.example.desafio.dao;

import com.example.desafio.model.Usuario;
import com.example.desafio.util.ConexaoBD;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UsuarioDAO {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean cadastrarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuarios (usuario, senha) VALUES (?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getSenha());
            stmt.executeUpdate();


            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getLong(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
