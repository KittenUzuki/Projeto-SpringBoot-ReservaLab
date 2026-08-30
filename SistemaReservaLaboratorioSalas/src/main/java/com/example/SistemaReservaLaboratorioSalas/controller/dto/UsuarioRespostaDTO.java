package com.example.SistemaReservaLaboratorioSalas.controller.dto;

import java.time.LocalDate;

// DTO de resposta: nunca inclui a senha do usuário
public record UsuarioRespostaDTO(
        Integer id,
        String cpf,
        String nomeCompleto,
        LocalDate dataAniversario,
        String celular,
        String email,
        String login)
{
}
