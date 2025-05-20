package com.example.desafio.controller;

import com.example.desafio.model.Usuario;
import com.example.desafio.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid Usuario usuario) {
        if (usuario.getUsuario() == null || usuario.getSenha() == null) {
            return new ResponseEntity<>("Os campos 'usuario' e 'senha' não podem ser nulos.", HttpStatus.BAD_REQUEST);
        }

        boolean sucesso = usuarioService.salvarUsuario(usuario.getUsuario(), usuario.getSenha());

        if (sucesso) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe ou erro ao salvar.");
        }
    }
}
