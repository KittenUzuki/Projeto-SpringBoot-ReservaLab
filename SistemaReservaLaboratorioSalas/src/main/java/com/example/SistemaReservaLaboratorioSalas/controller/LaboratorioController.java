package com.example.SistemaReservaLaboratorioSalas.controller;

import com.example.SistemaReservaLaboratorioSalas.controller.dto.LaboratorioDTO;
import com.example.SistemaReservaLaboratorioSalas.model.Laboratorio;
import com.example.SistemaReservaLaboratorioSalas.service.LaboratorioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/laboratorios")
// http://localhost:8080/laboratorios
public class LaboratorioController
{
    private final LaboratorioService laboratorioService;

    public LaboratorioController(LaboratorioService laboratorioService)
    {
        this.laboratorioService = laboratorioService;
    }

    @PostMapping
    public ResponseEntity<LaboratorioDTO> incluir(@RequestBody @Valid LaboratorioDTO dto)
    {
        Laboratorio laboratorio = laboratorioService.inserir(dto.mapearParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(LaboratorioDTO.deEntidade(laboratorio));
    }

    @GetMapping("{id}")
    public ResponseEntity<LaboratorioDTO> buscarPorId(@PathVariable("id") Integer id)
    {
        return laboratorioService.buscarPorId(id)
                .map(laboratorio -> ResponseEntity.ok(LaboratorioDTO.deEntidade(laboratorio)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Integer id, @RequestBody @Valid LaboratorioDTO dto)
    {
        Optional<Laboratorio> existente = laboratorioService.buscarPorId(id);
        if (existente.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        Laboratorio laboratorio = dto.mapearParaEntidade();
        laboratorio.setId(id);
        laboratorioService.atualizar(laboratorio);
        return ResponseEntity.ok(LaboratorioDTO.deEntidade(laboratorio));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluir(@PathVariable("id") Integer id)
    {
        if (laboratorioService.buscarPorId(id).isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        laboratorioService.excluirPorId(id);
        return ResponseEntity.ok().build();
    }
}
