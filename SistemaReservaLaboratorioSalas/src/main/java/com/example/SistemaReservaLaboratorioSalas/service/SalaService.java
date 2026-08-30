package com.example.SistemaReservaLaboratorioSalas.service;

import com.example.SistemaReservaLaboratorioSalas.exceptions.OperacaoNaoPermitidaException;
import com.example.SistemaReservaLaboratorioSalas.model.Sala;
import com.example.SistemaReservaLaboratorioSalas.repository.ReservaRepository;
import com.example.SistemaReservaLaboratorioSalas.repository.SalaRepository;
import com.example.SistemaReservaLaboratorioSalas.validator.RecursoValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalaService
{
    private final SalaRepository salaRepository;
    private final RecursoValidator recursoValidator;
    private final ReservaRepository reservaRepository;

    public SalaService(SalaRepository salaRepository,
                        RecursoValidator recursoValidator,
                        ReservaRepository reservaRepository)
    {
        this.salaRepository = salaRepository;
        this.recursoValidator = recursoValidator;
        this.reservaRepository = reservaRepository;
    }

    public Sala inserir(Sala sala)
    {
        recursoValidator.validar(sala);
        return salaRepository.save(sala);
    }

    public Optional<Sala> buscarPorId(Integer id)
    {
        return salaRepository.findById(id);
    }

    public Sala atualizar(Sala sala)
    {
        recursoValidator.validar(sala);
        return salaRepository.save(sala);
    }

    public void excluirPorId(Integer id)
    {
        salaRepository.findById(id).ifPresent(sala ->
        {
            if (reservaRepository.existsByRecurso(sala))
            {
                throw new OperacaoNaoPermitidaException(
                        "Não é permitido excluir uma Sala que possui reservas associadas");
            }
        });
        salaRepository.deleteById(id);
    }
}
