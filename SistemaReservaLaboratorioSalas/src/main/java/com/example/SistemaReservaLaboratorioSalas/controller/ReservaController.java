package com.example.SistemaReservaLaboratorioSalas.controller;

import com.example.SistemaReservaLaboratorioSalas.controller.dto.ReservaDTO;
import com.example.SistemaReservaLaboratorioSalas.controller.dto.ReservaRespostaDTO;
import com.example.SistemaReservaLaboratorioSalas.model.Reserva;
import com.example.SistemaReservaLaboratorioSalas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
// http://localhost:8080/reservas
public class ReservaController
{
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService)
    {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaRespostaDTO> incluir(@RequestBody @Valid ReservaDTO dto)
    {
        Reserva reserva = reservaService.montarEntidade(dto, null);
        reserva = reservaService.inserir(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservaRespostaDTO.deEntidade(reserva));
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservaRespostaDTO> buscarPorId(@PathVariable("id") Integer id)
    {
        return reservaService.buscarPorId(id)
                .map(reserva -> ResponseEntity.ok(ReservaRespostaDTO.deEntidade(reserva)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Integer id, @RequestBody @Valid ReservaDTO dto)
    {
        Optional<Reserva> existente = reservaService.buscarPorId(id);
        if (existente.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        Reserva reserva = reservaService.montarEntidade(dto, id);
        reserva = reservaService.atualizar(reserva);
        return ResponseEntity.ok(ReservaRespostaDTO.deEntidade(reserva));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluir(@PathVariable("id") Integer id)
    {
        if (reservaService.buscarPorId(id).isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        reservaService.excluirPorId(id);
        return ResponseEntity.ok().build();
    }

    // Consulta de reservas com filtros combináveis por: código/nome do recurso,
    // data (ou período: dataInicio/dataFim), hora, usuário e status.
    // http://localhost:8080/reservas?codigoRecurso=&nomeRecurso=&data=&dataInicio=&dataFim=&hora=&idUsuario=&idStatus=
    @GetMapping
    public ResponseEntity<List<ReservaRespostaDTO>> pesquisar(
            @RequestParam(value = "codigoRecurso", required = false) String codigoRecurso,
            @RequestParam(value = "nomeRecurso", required = false) String nomeRecurso,
            @RequestParam(value = "data", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(value = "dataInicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(value = "dataFim", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(value = "hora", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora,
            @RequestParam(value = "idUsuario", required = false) Integer idUsuario,
            @RequestParam(value = "idStatus", required = false) Integer idStatus)
    {
        List<ReservaRespostaDTO> lista = reservaService.pesquisar(
                        codigoRecurso, nomeRecurso, data, dataInicio, dataFim, hora, idUsuario, idStatus)
                .stream()
                .map(ReservaRespostaDTO::deEntidade)
                .toList();
        return ResponseEntity.ok(lista);
    }
}
