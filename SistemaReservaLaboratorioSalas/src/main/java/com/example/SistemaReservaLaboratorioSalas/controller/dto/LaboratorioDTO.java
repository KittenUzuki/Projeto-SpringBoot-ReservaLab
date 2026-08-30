package com.example.SistemaReservaLaboratorioSalas.controller.dto;

import com.example.SistemaReservaLaboratorioSalas.model.Laboratorio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LaboratorioDTO(
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
    public Laboratorio mapearParaEntidade()
    {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setCodigo(this.codigo);
        laboratorio.setNome(this.nome);
        laboratorio.setCapacidade(this.capacidade);
        laboratorio.setLocalizacao(this.localizacao);
        return laboratorio;
    }

    public static LaboratorioDTO deEntidade(Laboratorio laboratorio)
    {
        return new LaboratorioDTO(
                laboratorio.getId(),
                laboratorio.getCodigo(),
                laboratorio.getNome(),
                laboratorio.getCapacidade(),
                laboratorio.getLocalizacao());
    }
}
