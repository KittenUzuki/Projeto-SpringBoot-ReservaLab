package com.example.SistemaReservaLaboratorioSalas.service;

import com.example.SistemaReservaLaboratorioSalas.model.Status;
import com.example.SistemaReservaLaboratorioSalas.repository.StatusRepository;
import com.example.SistemaReservaLaboratorioSalas.validator.StatusValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService
{
    private final StatusRepository statusRepository;
    private final StatusValidator statusValidator;

    public StatusService(StatusRepository statusRepository, StatusValidator statusValidator)
    {
        this.statusRepository = statusRepository;
        this.statusValidator = statusValidator;
    }

    public Status inserir(Status status)
    {
        statusValidator.validar(status);
        return statusRepository.save(status);
    }

    public Optional<Status> buscarPorId(Integer id)
    {
        return statusRepository.findById(id);
    }

    public Status atualizar(Status status)
    {
        statusValidator.validar(status);
        return statusRepository.save(status);
    }

    public void excluirPorId(Integer id)
    {
        statusRepository.deleteById(id);
    }

    public List<Status> pesquisar(String codigo, String nome)
    {
        if (codigo != null && nome != null)
        {
            return statusRepository.findByCodigoAndNome(codigo, nome);
        }
        if (codigo != null)
        {
            return statusRepository.findByCodigo(codigo).map(List::of).orElse(List.of());
        }
        if (nome != null)
        {
            return statusRepository.findByNome(nome);
        }
        return statusRepository.findAll();
    }
}
