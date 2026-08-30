package com.example.SistemaReservaLaboratorioSalas.controller;

import com.example.SistemaReservaLaboratorioSalas.controller.dto.StatusDTO;
import com.example.SistemaReservaLaboratorioSalas.model.Status;
import com.example.SistemaReservaLaboratorioSalas.service.StatusService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/status")
// http://localhost:8080/status
public class StatusController
{
    private final StatusService statusService;

    public StatusController(StatusService statusService)
    {
        this.statusService = statusService;
    }

    @PostMapping
    public ResponseEntity<StatusDTO> incluir(@RequestBody @Valid StatusDTO dto)
    {
        Status status = statusService.inserir(dto.mapearParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(StatusDTO.deEntidade(status));
    }

    @GetMapping("{id}")
    public ResponseEntity<StatusDTO> buscarPorId(@PathVariable("id") Integer id)
    {
        return statusService.buscarPorId(id)
                .map(status -> ResponseEntity.ok(StatusDTO.deEntidade(status)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Integer id, @RequestBody @Valid StatusDTO dto)
    {
        Optional<Status> existente = statusService.buscarPorId(id);
        if (existente.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        Status status = dto.mapearParaEntidade();
        status.setId(id);
        statusService.atualizar(status);
        return ResponseEntity.ok(StatusDTO.deEntidade(status));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluir(@PathVariable("id") Integer id)
    {
        if (statusService.buscarPorId(id).isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        statusService.excluirPorId(id);
        return ResponseEntity.ok().build();
    }

    // GET /status?codigo=XXX&nome=YYY
    @GetMapping
    public ResponseEntity<List<StatusDTO>> pesquisar(
            @RequestParam(value = "codigo", required = false) String codigo,
            @RequestParam(value = "nome", required = false) String nome)
    {
        List<StatusDTO> lista = statusService.pesquisar(codigo, nome).stream()
                .map(StatusDTO::deEntidade)
                .toList();
        return ResponseEntity.ok(lista);
    }
}
