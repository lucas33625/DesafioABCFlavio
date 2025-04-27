package com.example.desafio.controller;

import com.example.desafio.dao.ClienteDAO;
import com.example.desafio.dto.ClienteDTO;
import com.example.desafio.relatorios.RelatorioService;
import com.example.desafio.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private RelatorioService relatorioService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/buscar")
    public ResponseEntity <List<ClienteDTO>> listarPorNome(@RequestParam String nome) {
        List<ClienteDTO> clientes = clienteDAO.buscarPeloNome(nome);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> clientes = clienteDAO.listarClientes();
        return ResponseEntity.ok(clientes); // Se a lista estiver vazia, retornará []
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable Long id) {
        ClienteDTO cliente = clienteDAO.buscarClientePorId(id);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email-existe")
    public ResponseEntity<Boolean> verificarEmail(@RequestParam String email, @RequestParam(required = false) Long id) {
        boolean emailExistente = clienteDAO.emailExist(email, id);
        return ResponseEntity.ok(emailExistente);
    }

    @PostMapping
    public ResponseEntity<Object> inserirCliente(@RequestBody @Valid ClienteDTO cliente) {
        if (cliente.getName() == null || cliente.getEmail() == null || cliente.getPhoneNumber() == null) {
            return new ResponseEntity<>("Os campos nome, email e telefone não podem ser nulos.", HttpStatus.BAD_REQUEST);
        }

        if (clienteDAO.emailExist(cliente.getEmail(), cliente.getId())) {
            return new ResponseEntity<>("Este e-mail já está cadastrado", HttpStatus.CONFLICT);
        }
        if (clienteDAO.inserirCliente(cliente)) {

            emailService.enviarEmailCadastro(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO cliente) {
        ClienteDTO clienteExistente = clienteDAO.buscarClientePorId(id);

        if (clienteExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

        // Validação simples manual dos campos obrigatórios
        if (cliente.getName() == null || cliente.getEmail() == null || cliente.getPhoneNumber() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Os campos nome, email e telefone não podem ser nulos");
        }

        if (clienteDAO.emailExist(cliente.getEmail() , cliente.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este email já esta cadastrado");
        }

        cliente.setId(id); // Garante que o ID correto seja mantido

        boolean atualizado = clienteDAO.atualizarCliente(cliente);
        if (atualizado) {
            emailService.enviarEmailAlteracao(cliente);
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o cliente");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        ClienteDTO clienteExistente = clienteDAO.buscarClientePorId(id);
        if (clienteExistente != null) {
            clienteDAO.deletarCliente(id);
            return new ResponseEntity<>(HttpStatus.OK);  // 204 No Content
        } else {
            return new ResponseEntity<>("Id não encontrado!",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/relatorio/pdf")
    public ResponseEntity<byte[]> gerarRelatorio() {
        try {
            List<ClienteDTO> clientes = clienteDAO.listarClientes();
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
        try {
            ClienteDTO cliente = clienteDAO.buscarClientePorId(id);

            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            byte[] docxBytes = relatorioService.gerarRelatorioDetalheCliente(cliente); // retorna byte[]

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "cliente_" + id + ".docx");
            headers.setContentLength(docxBytes.length);

            return new ResponseEntity<>(docxBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            // Logar o erro no console ajuda a entender o erro 500
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
