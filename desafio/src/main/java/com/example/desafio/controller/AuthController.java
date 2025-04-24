package com.example.desafio.controller;

import com.example.desafio.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        if ("admin".equals(usuario.getUsuario()) && "admin".equals(usuario.getSenha())) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }


}
