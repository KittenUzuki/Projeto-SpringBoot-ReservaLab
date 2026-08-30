package com.example.SistemaReservaLaboratorioSalas.service;

import com.example.SistemaReservaLaboratorioSalas.exceptions.OperacaoNaoPermitidaException;
import com.example.SistemaReservaLaboratorioSalas.model.Laboratorio;
import com.example.SistemaReservaLaboratorioSalas.repository.LaboratorioRepository;
import com.example.SistemaReservaLaboratorioSalas.repository.ReservaRepository;
import com.example.SistemaReservaLaboratorioSalas.validator.RecursoValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaboratorioService
{
    private final LaboratorioRepository laboratorioRepository;
    private final RecursoValidator recursoValidator;
    private final ReservaRepository reservaRepository;

    public LaboratorioService(LaboratorioRepository laboratorioRepository,
                               RecursoValidator recursoValidator,
                               ReservaRepository reservaRepository)
    {
        this.laboratorioRepository = laboratorioRepository;
        this.recursoValidator = recursoValidator;
        this.reservaRepository = reservaRepository;
    }

    public Laboratorio inserir(Laboratorio laboratorio)
    {
        recursoValidator.validar(laboratorio);
        return laboratorioRepository.save(laboratorio);
    }

    public Optional<Laboratorio> buscarPorId(Integer id)
    {
        return laboratorioRepository.findById(id);
    }

    public Laboratorio atualizar(Laboratorio laboratorio)
    {
        recursoValidator.validar(laboratorio);
        return laboratorioRepository.save(laboratorio);
    }

    public void excluirPorId(Integer id)
    {
        laboratorioRepository.findById(id).ifPresent(laboratorio ->
        {
            if (reservaRepository.existsByRecurso(laboratorio))
            {
                throw new OperacaoNaoPermitidaException(
                        "Não é permitido excluir um Laboratório que possui reservas associadas");
            }
        });
        laboratorioRepository.deleteById(id);
    }
}
