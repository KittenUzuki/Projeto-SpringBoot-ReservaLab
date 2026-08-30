package com.example.SistemaReservaLaboratorioSalas.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

// DTO de entrada (POST/PUT) para uma Reserva.
// idUsuario, idRecurso e idStatus referenciam registros já cadastrados.
public record ReservaDTO(
        Integer id,
        @NotNull(message = "Campo Obrigatório")
        LocalDate dataInicial,
        @NotNull(message = "Campo Obrigatório")
        LocalDate dataFinal,
        @NotNull(message = "Campo Obrigatório")
        LocalTime horaInicial,
        @NotNull(message = "Campo Obrigatório")
        LocalTime horaFinal,
        @NotNull(message = "Campo Obrigatório")
        Integer idUsuario,
        @NotNull(message = "Campo Obrigatório")
        Integer idRecurso,
        @NotNull(message = "Campo Obrigatório")
        Integer idStatus)
{
}
