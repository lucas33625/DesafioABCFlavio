package com.example.desafio.services;

import com.example.desafio.dto.ClienteDTO;
import com.example.desafio.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void enviarEmailCadastro(ClienteDTO cliente) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cliente.getEmail());
        message.setSubject("Cadastro realizado com sucesso, seja bem-vindo!");
        message.setText("Olá " + cliente.getName() + ",\n\nSeu cadastro foi realizado com sucesso!\n\nAtenciosamente,\nEquipe Desafio.");

        mailSender.send(message);
    }

    public void enviarEmailAlteracao(ClienteDTO cliente) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cliente.getEmail());
        message.setSubject("Cadastro alterado com sucesso!");
        message.setText("Olá " + cliente.getName() + ",\n\nSeu cadastro foi atualizado com sucesso!\n\nAtenciosamente,\nEquipe Desafio.");

        mailSender.send(message);
    }

}
