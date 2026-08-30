package com.example.SistemaReservaLaboratorioSalas.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Campo Obrigatório")
        String login,
        @NotBlank(message = "Campo Obrigatório")
        String senha)
{
}
