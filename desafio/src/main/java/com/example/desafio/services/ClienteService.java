package com.example.desafio.services;

import com.example.desafio.model.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    public boolean validarCliente(Cliente cliente) {
        return cliente.getName() != null && cliente.getEmail() != null && cliente.getPhoneNumber() != null;
    }
}
