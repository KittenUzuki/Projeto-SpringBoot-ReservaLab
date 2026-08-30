package com.example.SistemaReservaLaboratorioSalas.controller;

import com.example.SistemaReservaLaboratorioSalas.controller.dto.ErroResposta;
import com.example.SistemaReservaLaboratorioSalas.controller.dto.LoginDTO;
import com.example.SistemaReservaLaboratorioSalas.controller.dto.UsuarioDTO;
import com.example.SistemaReservaLaboratorioSalas.controller.dto.UsuarioRespostaDTO;
import com.example.SistemaReservaLaboratorioSalas.model.Usuario;
import com.example.SistemaReservaLaboratorioSalas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
// http://localhost:8080/usuarios
public class UsuarioController
{
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService)
    {
        this.usuarioService = usuarioService;
    }

    // cadastro de usuário do sistema (login/senha)
    @PostMapping
    public ResponseEntity<UsuarioRespostaDTO> incluir(@RequestBody @Valid UsuarioDTO dto)
    {
        Usuario usuario = usuarioService.inserir(dto.mapearParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDTO.paraResposta(usuario));
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioRespostaDTO> buscarPorId(@PathVariable("id") Integer id)
    {
        return usuarioService.buscarPorId(id)
                .map(usuario -> ResponseEntity.ok(UsuarioDTO.paraResposta(usuario)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Integer id, @RequestBody @Valid UsuarioDTO dto)
    {
        Optional<Usuario> existente = usuarioService.buscarPorId(id);
        if (existente.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = dto.mapearParaEntidade();
        usuario.setId(id);
        usuarioService.atualizar(usuario);
        return ResponseEntity.ok(UsuarioDTO.paraResposta(usuario));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluir(@PathVariable("id") Integer id)
    {
        if (usuarioService.buscarPorId(id).isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        usuarioService.excluirPorId(id);
        return ResponseEntity.ok().build();
    }

    // consulta dos dados dos usuários cadastrados por e-mail e data de aniversário
    // GET /usuarios?email=XXX&dataAniversario=2000-01-31
    @GetMapping
    public ResponseEntity<List<UsuarioRespostaDTO>> pesquisar(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "dataAniversario", required = false)
            @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
            LocalDate dataAniversario)
    {
        List<UsuarioRespostaDTO> lista = usuarioService.pesquisar(email, dataAniversario).stream()
                .map(UsuarioDTO::paraResposta)
                .toList();
        return ResponseEntity.ok(lista);
    }

    // tela de login de acesso ao sistema
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO dto)
    {
        return usuarioService.autenticar(dto.login(), dto.senha())
                .<ResponseEntity<Object>>map(usuario -> ResponseEntity.ok(UsuarioDTO.paraResposta(usuario)))
                .orElseGet(() -> {
                    var erro = ErroResposta.naoAutorizado("Login ou senha inválidos");
                    return ResponseEntity.status(erro.status()).body(erro);
                });
    }
}
