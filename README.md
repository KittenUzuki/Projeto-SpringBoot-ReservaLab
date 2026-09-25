# Sistema de Reserva de Laboratório e Salas de Aula

API REST em Spring Boot (Java) para o Projeto da API — 2º semestre — Sistemas de Computação.
Segue o mesmo padrão de arquitetura usado no projeto `SisAcademicoAlunos`:

```
model → repository → validator → service → controller (+ controller/dto) → commom/GlobalExceptionHandler
```

## Como rodar

1. Ajuste a conexão com o SQL Server em `src/main/resources/application.yaml`
   (`url`, `username`, `password`).
2. `./mvnw spring-boot:run` (ou rode a classe `SistemaReservaLaboratorioSalasApplication` pela IDE).
3. Com `ddl-auto: update` o Hibernate cria as tabelas automaticamente. O script
   `database/modelo_banco_dados.sql` documenta o modelo e já inclui um `INSERT`
   inicial dos 4 status (Livre, Ocupado, Bloqueado, Reservado) — cadastre-os
   primeiro (via script ou via `POST /status`) antes de criar reservas.
4. Teste os endpoints via Insomnia ou Swagger, como pede o enunciado.

## Modelagem

- `Usuario` — cadastro de usuário (CPF, nome completo, data de aniversário, celular, email, login/senha).
- `Status` — Livre / Ocupado / Bloqueado / Reservado (código + nome).
- `Recurso` — classe base (herança JOINED) para `Laboratorio` e `Sala`, com código, nome, capacidade e localização.
- `Reserva` — data/hora inicial e final, usuário, recurso (laboratório **ou** sala) e status.

Motivo da herança `Recurso → Laboratorio/Sala`: o enunciado trata laboratório e
sala como o mesmo conceito de "recurso reservável", então uma `Reserva` aponta
para um único `id_recurso`, e a consulta unificada (`GET /recursos`) já
devolve os dois tipos juntos. Cadastro/edição/exclusão continuam
separados (`/laboratorios` e `/salas`) porque o enunciado pede cadastros distintos.

## Endpoints

| Recurso | Método/Rota | Descrição |
|---|---|---|
| Usuário | `POST /usuarios` | Cadastro de usuário |
| | `GET /usuarios/{id}` | Buscar por id |
| | `PUT /usuarios/{id}` | Atualizar |
| | `DELETE /usuarios/{id}` | Excluir |
| | `GET /usuarios?email=&dataAniversario=` | Consulta por e-mail e/ou data de aniversário |
| | `POST /usuarios/login` | Tela de login (login + senha) |
| Laboratório | `POST /laboratorios` | Cadastro |
| | `GET /laboratorios/{id}` | Buscar por id |
| | `PUT /laboratorios/{id}` | Atualizar |
| | `DELETE /laboratorios/{id}` | Excluir |
| Sala | `POST /salas` | Cadastro |
| | `GET /salas/{id}` | Buscar por id |
| | `PUT /salas/{id}` | Atualizar |
| | `DELETE /salas/{id}` | Excluir |
| Recursos (unificado) | `GET /recursos?nome=&capacidade=&localizacao=` | Consulta laboratórios + salas juntos |
| Status | `POST /status` | Cadastro |
| | `GET /status/{id}` | Buscar por id |
| | `PUT /status/{id}` | Atualizar |
| | `DELETE /status/{id}` | Excluir |
| | `GET /status?codigo=&nome=` | Consulta dos status cadastrados |
| Reserva | `POST /reservas` | Nova reserva (`idUsuario`, `idRecurso`, `idStatus`, datas e horas) |
| | `GET /reservas/{id}` | Buscar por id |
| | `PUT /reservas/{id}` | Atualizar |
| | `DELETE /reservas/{id}` | Excluir |
| | `GET /reservas?codigoRecurso=&nomeRecurso=&data=&dataInicio=&dataFim=&hora=&idUsuario=&idStatus=` | Consulta combinável por recurso, data/período, hora, usuário e status |

## Regras de validação implementadas

- Campos obrigatórios validados via Bean Validation (`@NotBlank`, `@NotNull`, `@Email`, etc.) nos DTOs.
- CPF, e-mail e login de `Usuario` não podem se repetir (`409 Conflict`).
- Código de `Laboratorio`/`Sala` não pode se repetir, mesmo entre os dois tipos (`409 Conflict`).
- Código de `Status` não pode se repetir (`409 Conflict`).
- `Reserva`: data final não pode ser anterior à inicial; hora final deve ser
  depois da inicial no mesmo dia; e não é permitido reservar um recurso em um
  período/horário que já tenha outra reserva com status diferente de `LIVRE`
  (evita sobreposição/duplicidade).
- Não é permitido excluir `Laboratorio`/`Sala` que já possua reservas associadas.
- Todos os erros de negócio retornam um `ErroResposta` padronizado (`status`,
  `mensagem`, `erros`), tratado centralmente pelo `GlobalExceptionHandler`.
