package com.example.desafio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validarLogin(String usuario, String senha) {
        try {
            String senhaHash = jdbcTemplate.queryForObject(
                    "SELECT senha FROM usuarios WHERE usuario = ?", new Object[]{usuario}, String.class
            );
            return passwordEncoder.matches(senha, senhaHash);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
