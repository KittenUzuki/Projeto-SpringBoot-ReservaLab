package com.example.SistemaReservaLaboratorioSalas.controller.dto;

import com.example.SistemaReservaLaboratorioSalas.model.Sala;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SalaDTO(
        Integer id,
        @NotBlank(message = "Campo Obrigatório")
        String codigo,
        @NotBlank(message = "Campo Obrigatório")
        String nome,
        @NotNull(message = "Campo Obrigatório")
        @Positive(message = "Capacidade deve ser maior que zero")
        Integer capacidade,
        @NotBlank(message = "Campo Obrigatório")
        String localizacao)
{
    public Sala mapearParaEntidade()
    {
        Sala sala = new Sala();
        sala.setCodigo(this.codigo);
        sala.setNome(this.nome);
        sala.setCapacidade(this.capacidade);
        sala.setLocalizacao(this.localizacao);
        return sala;
    }

    public static SalaDTO deEntidade(Sala sala)
    {
        return new SalaDTO(
                sala.getId(),
                sala.getCodigo(),
                sala.getNome(),
                sala.getCapacidade(),
                sala.getLocalizacao());
    }
}
