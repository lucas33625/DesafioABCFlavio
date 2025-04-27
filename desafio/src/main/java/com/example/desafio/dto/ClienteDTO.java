package com.example.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
        private Long id;
        @NotNull (message = "Nome é obrigatório")
        private String name;
        @NotNull (message= "Email é obrigatório")
        private String email;
        @NotNull (message = "Telefone é obrigatório")
        private String phoneNumber;
}
