package com.example.SistemaReservaLaboratorioSalas.validator;

import com.example.SistemaReservaLaboratorioSalas.exceptions.OperacaoNaoPermitidaException;
import com.example.SistemaReservaLaboratorioSalas.model.Reserva;
import com.example.SistemaReservaLaboratorioSalas.repository.ReservaRepository;
import org.springframework.stereotype.Component;

@Component
public class ReservaValidator
{
    private final ReservaRepository reservaRepository;

    public ReservaValidator(ReservaRepository reservaRepository)
    {
        this.reservaRepository = reservaRepository;
    }

    public void validar(Reserva reserva)
    {
        validarPeriodo(reserva);
        // A checagem de recurso Bloqueado/Ocupado/Reservado no mesmo período
        // é feita em validarConflitoDeHorario, que já ignora apenas reservas
        // com status LIVRE.
        validarConflitoDeHorario(reserva);
    }

    private void validarPeriodo(Reserva reserva)
    {
        if (reserva.getDataFinal().isBefore(reserva.getDataInicial()))
        {
            throw new OperacaoNaoPermitidaException(
                    "A data final da reserva não pode ser anterior à data inicial");
        }

        boolean mesmoDia = reserva.getDataInicial().isEqual(reserva.getDataFinal());
        if (mesmoDia && !reserva.getHoraFinal().isAfter(reserva.getHoraInicial()))
        {
            throw new OperacaoNaoPermitidaException(
                    "A hora final da reserva deve ser posterior à hora inicial");
        }
    }

    private void validarConflitoDeHorario(Reserva reserva)
    {
        var conflitos = reservaRepository.buscarConflitos(
                reserva.getRecurso(),
                reserva.getDataInicial(),
                reserva.getDataFinal(),
                reserva.getHoraInicial(),
                reserva.getHoraFinal(),
                reserva.getId());

        if (!conflitos.isEmpty())
        {
            throw new OperacaoNaoPermitidaException(
                    "O recurso já possui reserva/ocupação nesse período e horário");
        }
    }
}
