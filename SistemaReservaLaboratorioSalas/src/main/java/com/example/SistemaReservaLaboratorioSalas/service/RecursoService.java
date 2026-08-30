package com.example.SistemaReservaLaboratorioSalas.service;

import com.example.SistemaReservaLaboratorioSalas.model.Recurso;
import com.example.SistemaReservaLaboratorioSalas.repository.RecursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Consulta unificada de recursos (Sala + Laboratório) por nome, capacidade e localização
@Service
public class RecursoService
{
    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository)
    {
        this.recursoRepository = recursoRepository;
    }

    public List<Recurso> pesquisar(String nome, Integer capacidade, String localizacao)
    {
        if (nome != null && capacidade != null && localizacao != null)
        {
            return recursoRepository.findByNomeContainingIgnoreCaseAndCapacidadeAndLocalizacaoContainingIgnoreCase(
                    nome, capacidade, localizacao);
        }
        if (nome != null && capacidade != null)
        {
            return recursoRepository.findByNomeContainingIgnoreCaseAndCapacidade(nome, capacidade);
        }
        if (nome != null && localizacao != null)
        {
            return recursoRepository.findByNomeContainingIgnoreCaseAndLocalizacaoContainingIgnoreCase(nome, localizacao);
        }
        if (capacidade != null && localizacao != null)
        {
            return recursoRepository.findByCapacidadeAndLocalizacaoContainingIgnoreCase(capacidade, localizacao);
        }
        if (nome != null)
        {
            return recursoRepository.findByNomeContainingIgnoreCase(nome);
        }
        if (capacidade != null)
        {
            return recursoRepository.findByCapacidade(capacidade);
        }
        if (localizacao != null)
        {
            return recursoRepository.findByLocalizacaoContainingIgnoreCase(localizacao);
        }
        return recursoRepository.findAll();
    }
}
