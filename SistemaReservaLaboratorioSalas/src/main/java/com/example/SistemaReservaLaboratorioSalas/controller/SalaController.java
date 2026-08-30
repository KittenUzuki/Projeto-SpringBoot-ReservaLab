package com.example.SistemaReservaLaboratorioSalas.controller;

import com.example.SistemaReservaLaboratorioSalas.controller.dto.SalaDTO;
import com.example.SistemaReservaLaboratorioSalas.model.Sala;
import com.example.SistemaReservaLaboratorioSalas.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/salas")
// http://localhost:8080/salas
public class SalaController
{
    private final SalaService salaService;

    public SalaController(SalaService salaService)
    {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<SalaDTO> incluir(@RequestBody @Valid SalaDTO dto)
    {
        Sala sala = salaService.inserir(dto.mapearParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(SalaDTO.deEntidade(sala));
    }

    @GetMapping("{id}")
    public ResponseEntity<SalaDTO> buscarPorId(@PathVariable("id") Integer id)
    {
        return salaService.buscarPorId(id)
                .map(sala -> ResponseEntity.ok(SalaDTO.deEntidade(sala)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Integer id, @RequestBody @Valid SalaDTO dto)
    {
        Optional<Sala> existente = salaService.buscarPorId(id);
        if (existente.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        Sala sala = dto.mapearParaEntidade();
        sala.setId(id);
        salaService.atualizar(sala);
        return ResponseEntity.ok(SalaDTO.deEntidade(sala));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluir(@PathVariable("id") Integer id)
    {
        if (salaService.buscarPorId(id).isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        salaService.excluirPorId(id);
        return ResponseEntity.ok().build();
    }
}
