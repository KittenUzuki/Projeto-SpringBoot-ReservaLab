package com.example.SistemaReservaLaboratorioSalas.service;

import com.example.SistemaReservaLaboratorioSalas.controller.dto.ReservaDTO;
import com.example.SistemaReservaLaboratorioSalas.exceptions.RegistroNaoEncontradoException;
import com.example.SistemaReservaLaboratorioSalas.model.Recurso;
import com.example.SistemaReservaLaboratorioSalas.model.Reserva;
import com.example.SistemaReservaLaboratorioSalas.model.Status;
import com.example.SistemaReservaLaboratorioSalas.model.Usuario;
import com.example.SistemaReservaLaboratorioSalas.repository.RecursoRepository;
import com.example.SistemaReservaLaboratorioSalas.repository.ReservaRepository;
import com.example.SistemaReservaLaboratorioSalas.repository.StatusRepository;
import com.example.SistemaReservaLaboratorioSalas.repository.UsuarioRepository;
import com.example.SistemaReservaLaboratorioSalas.validator.ReservaValidator;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService
{
    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RecursoRepository recursoRepository;
    private final StatusRepository statusRepository;
    private final ReservaValidator reservaValidator;

    public ReservaService(ReservaRepository reservaRepository,
                           UsuarioRepository usuarioRepository,
                           RecursoRepository recursoRepository,
                           StatusRepository statusRepository,
                           ReservaValidator reservaValidator)
    {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.recursoRepository = recursoRepository;
        this.statusRepository = statusRepository;
        this.reservaValidator = reservaValidator;
    }

    // monta a entidade Reserva a partir do DTO, resolvendo as referências
    // de usuário, recurso e status. Usado tanto no INSERT quanto no UPDATE.
    public Reserva montarEntidade(ReservaDTO dto, Integer idExistente)
    {
        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RegistroNaoEncontradoException(
                        "Não existe Usuário com o id informado: " + dto.idUsuario()));

        Recurso recurso = recursoRepository.findById(dto.idRecurso())
                .orElseThrow(() -> new RegistroNaoEncontradoException(
                        "Não existe Recurso (sala/laboratório) com o id informado: " + dto.idRecurso()));

        Status status = statusRepository.findById(dto.idStatus())
                .orElseThrow(() -> new RegistroNaoEncontradoException(
                        "Não existe Status com o id informado: " + dto.idStatus()));

        Reserva reserva = new Reserva();
        reserva.setId(idExistente);
        reserva.setDataInicial(dto.dataInicial());
        reserva.setDataFinal(dto.dataFinal());
        reserva.setHoraInicial(dto.horaInicial());
        reserva.setHoraFinal(dto.horaFinal());
        reserva.setUsuario(usuario);
        reserva.setRecurso(recurso);
        reserva.setStatus(status);
        return reserva;
    }

    public Reserva inserir(Reserva reserva)
    {
        reservaValidator.validar(reserva);
        return reservaRepository.save(reserva);
    }

    public Optional<Reserva> buscarPorId(Integer id)
    {
        return reservaRepository.findById(id);
    }

    public Reserva atualizar(Reserva reserva)
    {
        reservaValidator.validar(reserva);
        return reservaRepository.save(reserva);
    }

    public void excluirPorId(Integer id)
    {
        reservaRepository.deleteById(id);
    }

    // Consulta combinável por: código/nome do recurso, data ou período,
    // hora, usuário que fez a reserva e status da reserva.
    public List<Reserva> pesquisar(String codigoRecurso, String nomeRecurso,
                                    LocalDate data, LocalDate dataInicio, LocalDate dataFim,
                                    LocalTime hora, Integer idUsuario, Integer idStatus)
    {
        Specification<Reserva> spec = (root, query, cb) ->
        {
            List<Predicate> predicados = new ArrayList<>();
            Join<Object, Object> recursoJoin = root.join("recurso");

            if (codigoRecurso != null)
            {
                predicados.add(cb.equal(recursoJoin.get("codigo"), codigoRecurso));
            }
            if (nomeRecurso != null)
            {
                predicados.add(cb.like(cb.lower(recursoJoin.get("nome")), "%" + nomeRecurso.toLowerCase() + "%"));
            }
            if (data != null)
            {
                predicados.add(cb.and(
                        cb.lessThanOrEqualTo(root.get("dataInicial"), data),
                        cb.greaterThanOrEqualTo(root.get("dataFinal"), data)));
            }
            if (dataInicio != null)
            {
                predicados.add(cb.greaterThanOrEqualTo(root.get("dataInicial"), dataInicio));
            }
            if (dataFim != null)
            {
                predicados.add(cb.lessThanOrEqualTo(root.get("dataFinal"), dataFim));
            }
            if (hora != null)
            {
                predicados.add(cb.and(
                        cb.lessThanOrEqualTo(root.get("horaInicial"), hora),
                        cb.greaterThanOrEqualTo(root.get("horaFinal"), hora)));
            }
            if (idUsuario != null)
            {
                predicados.add(cb.equal(root.get("usuario").get("id"), idUsuario));
            }
            if (idStatus != null)
            {
                predicados.add(cb.equal(root.get("status").get("id"), idStatus));
            }

            return cb.and(predicados.toArray(new Predicate[0]));
        };

        return reservaRepository.findAll(spec);
    }
}
