package com.example.desafio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean salvarUsuario(String usuario, String senha) {
        String hash = passwordEncoder.encode(senha);
        jdbcTemplate.update("INSERT INTO usuarios (usuario, senha) VALUES (?, ?)", usuario, hash);
        return true;
    }
}
