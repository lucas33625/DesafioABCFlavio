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
    public ResponseEntity<Cliente> inserirCliente(@RequestBody Cliente cliente) {
        clienteDAO.inserirCliente(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
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
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        Cliente clienteExistente = clienteDAO.buscarClientePorId(id);
        if (clienteExistente != null) {
            clienteDAO.deletarCliente(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
