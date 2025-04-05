package com.example.desafio.controller;

import com.example.desafio.dao.ClienteDAO;
import com.example.desafio.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;


    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id) {
        Cliente cliente = clienteDAO.buscarClientePorId(id);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<Object> inserirCliente(@RequestBody Cliente cliente) {

        if (cliente.getName() == null || cliente.getEmail() == null || cliente.getPhoneNumber() == null) {
            return new ResponseEntity<>("Os campos nome, email e telefone não podem ser nulos.", HttpStatus.BAD_REQUEST);
        }
        if (clienteDAO.inserirCliente(cliente)) {
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteExistente = clienteDAO.buscarClientePorId(id);
        if (clienteExistente != null) {
            cliente.setId(id);  // Certifique-se de que o ID correto está sendo atualizado
            clienteDAO.atualizarCliente(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        Cliente clienteExistente = clienteDAO.buscarClientePorId(id);
        if (clienteExistente != null) {
            clienteDAO.deletarCliente(id);
            return new ResponseEntity<>(HttpStatus.OK);  // 204 No Content
        } else {
            return new ResponseEntity<>("Id não encontrado!",HttpStatus.NOT_FOUND);
        }
    }
}
