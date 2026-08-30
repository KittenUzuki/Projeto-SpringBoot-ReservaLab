package com.example.SistemaReservaLaboratorioSalas.controller;

import com.example.SistemaReservaLaboratorioSalas.controller.dto.RecursoDTO;
import com.example.SistemaReservaLaboratorioSalas.service.RecursoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Consulta unificada de recursos (sala e laboratório) por nome, capacidade e localização.
// Para incluir/alterar/excluir um recurso, usar /laboratorios ou /salas.
@RestController
@RequestMapping("/recursos")
// http://localhost:8080/recursos?nome=XXX&capacidade=30&localizacao=YYY
public class RecursoController
{
    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService)
    {
        this.recursoService = recursoService;
    }

    @GetMapping
    public List<RecursoDTO> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "capacidade", required = false) Integer capacidade,
            @RequestParam(value = "localizacao", required = false) String localizacao)
    {
        return recursoService.pesquisar(nome, capacidade, localizacao).stream()
                .map(RecursoDTO::deEntidade)
                .toList();
    }
}
