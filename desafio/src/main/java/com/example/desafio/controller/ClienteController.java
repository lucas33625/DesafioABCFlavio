package com.example.desafio.controller;

import com.example.desafio.dao.ClienteDAO;
import com.example.desafio.model.Cliente;
import com.example.desafio.relatorios.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private RelatorioService relatorioService;


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

//    @GetMapping("/relatorio/pdf")
//    public ResponseEntity<String> gerarRelatorio() {
//        try {
//            List<Cliente> clientes = clienteDAO.listarClientes();
//            relatorioService.gerarRelatorioClientes(clientes);
//            return ResponseEntity.ok("Relatório gerado com sucesso!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/{id}/relatorio/docx")
//    public ResponseEntity<String> gerarDocx(@PathVariable Long id) {
//        Cliente cliente = clienteDAO.buscarClientePorId(id);
//        if (cliente != null) {
//            try {
//                relatorioService.gerarRelatorioDetalheCliente(cliente);
//                return ResponseEntity.ok("DOCX gerado com sucesso.");
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar DOCX: " + e.getMessage());
//            }
//        } else {
//            return new ResponseEntity<>("Cliente não encontrado.", HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/relatorio/pdf")
    public ResponseEntity<byte[]> gerarRelatorio() {
        try {
            List<Cliente> clientes = clienteDAO.listarClientes();
            byte[] pdfBytes = relatorioService.gerarRelatorioClientes(clientes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "attachment; filename=relatorio-clientes.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // <-- Mostra erro no console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}/relatorio/docx")
    public ResponseEntity<byte[]> gerarDocx(@PathVariable Long id) {
        Cliente cliente = clienteDAO.buscarClientePorId(id);
        if (cliente != null) {
            try {
                byte[] docxBytes = relatorioService.gerarRelatorioDetalheCliente(cliente); // retorna byte[]

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.add("Content-Disposition", "attachment; filename=cliente_" + id + ".docx");

                return new ResponseEntity<>(docxBytes, headers, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
