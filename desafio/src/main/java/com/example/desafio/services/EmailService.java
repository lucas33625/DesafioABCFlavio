package com.example.desafio.services;

import com.example.desafio.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailCadastro(Cliente cliente) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cliente.getEmail());
        message.setSubject("Cadastro realizado com sucesso, seja bem-vindo!");
        message.setText("Olá " + cliente.getName() + ",\n\nSeu cadastro foi realizado com sucesso!\n\nAtenciosamente,\nEquipe Desafio.");

        mailSender.send(message);
    }

}
