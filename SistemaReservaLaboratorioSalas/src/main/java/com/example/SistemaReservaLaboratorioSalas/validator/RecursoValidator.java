package com.example.SistemaReservaLaboratorioSalas.validator;

import com.example.SistemaReservaLaboratorioSalas.exceptions.RegistroDuplicadoException;
import com.example.SistemaReservaLaboratorioSalas.model.Recurso;
import com.example.SistemaReservaLaboratorioSalas.repository.RecursoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Validador comum para Laboratorio e Sala, já que ambos compartilham
// o mesmo namespace de código (tabela "recurso").
@Component
public class RecursoValidator
{
    private final RecursoRepository recursoRepository;

    public RecursoValidator(RecursoRepository recursoRepository)
    {
        this.recursoRepository = recursoRepository;
    }

    public void validar(Recurso recurso)
    {
        Optional<Recurso> encontrado = recursoRepository.findByCodigo(recurso.getCodigo());

        boolean duplicado = recurso.getId() == null
                ? encontrado.isPresent()
                : encontrado.isPresent() && !encontrado.get().getId().equals(recurso.getId());

        if (duplicado)
        {
            throw new RegistroDuplicadoException(
                    "Já existe um recurso (sala ou laboratório) cadastrado com esse código");
        }
    }
}
