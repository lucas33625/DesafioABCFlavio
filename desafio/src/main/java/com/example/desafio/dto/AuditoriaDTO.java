package com.example.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuditoriaDTO {
    private Long id;
    private Long clienteId;
    private String campoAlterado;
    private String valorAntigo;
    private String valorNovo;
    private LocalDateTime dataAlteracao;
}
