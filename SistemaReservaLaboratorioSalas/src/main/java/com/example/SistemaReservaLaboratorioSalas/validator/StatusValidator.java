package com.example.SistemaReservaLaboratorioSalas.validator;

import com.example.SistemaReservaLaboratorioSalas.exceptions.RegistroDuplicadoException;
import com.example.SistemaReservaLaboratorioSalas.model.Status;
import com.example.SistemaReservaLaboratorioSalas.repository.StatusRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StatusValidator
{
    private final StatusRepository statusRepository;

    public StatusValidator(StatusRepository statusRepository)
    {
        this.statusRepository = statusRepository;
    }

    public void validar(Status status)
    {
        Optional<Status> encontrado = statusRepository.findByCodigo(status.getCodigo());

        boolean duplicado = status.getId() == null
                ? encontrado.isPresent()
                : encontrado.isPresent() && !encontrado.get().getId().equals(status.getId());

        if (duplicado)
        {
            throw new RegistroDuplicadoException("Já existe um Status cadastrado com esse código");
        }
    }
}
