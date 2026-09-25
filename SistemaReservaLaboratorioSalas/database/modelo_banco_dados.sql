--- Modelo do BD do projeto se precisar criar manualmente


CREATE TABLE usuario (
    id                INT IDENTITY(1,1) PRIMARY KEY,
    cpf               VARCHAR(11)  NOT NULL UNIQUE,
    nome_completo     VARCHAR(100) NOT NULL,
    data_aniversario  DATE         NOT NULL,
    celular           VARCHAR(15)  NOT NULL,
    email             VARCHAR(80)  NOT NULL UNIQUE,
    login             VARCHAR(30)  NOT NULL UNIQUE,
    senha             VARCHAR(100) NOT NULL
);

CREATE TABLE status (
    id      INT IDENTITY(1,1) PRIMARY KEY,
    codigo  VARCHAR(20) NOT NULL,
    nome    VARCHAR(30) NOT NULL
);


CREATE TABLE recurso (
    id           INT IDENTITY(1,1) PRIMARY KEY,
    codigo       VARCHAR(20) NOT NULL UNIQUE,
    nome         VARCHAR(80) NOT NULL,
    capacidade   INT         NOT NULL,
    localizacao  VARCHAR(60) NOT NULL
);

CREATE TABLE laboratorio (
    id INT PRIMARY KEY,
    CONSTRAINT fk_laboratorio_recurso FOREIGN KEY (id) REFERENCES recurso(id)
);

CREATE TABLE sala (
    id INT PRIMARY KEY,
    CONSTRAINT fk_sala_recurso FOREIGN KEY (id) REFERENCES recurso(id)
);

CREATE TABLE reserva (
    id             INT IDENTITY(1,1) PRIMARY KEY,
    data_inicial   DATE NOT NULL,
    data_final     DATE NOT NULL,
    hora_inicial   TIME NOT NULL,
    hora_final     TIME NOT NULL,
    id_usuario     INT  NOT NULL,
    id_recurso     INT  NOT NULL,
    id_status      INT  NOT NULL,
    CONSTRAINT fk_reserva_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    CONSTRAINT fk_reserva_recurso FOREIGN KEY (id_recurso) REFERENCES recurso(id),
    CONSTRAINT fk_reserva_status  FOREIGN KEY (id_status)  REFERENCES status(id)
);

-- carga inicial sugerida para a tabela de status
INSERT INTO status (codigo, nome) VALUES ('LIVRE', 'Livre');
INSERT INTO status (codigo, nome) VALUES ('OCUPADO', 'Ocupado');
INSERT INTO status (codigo, nome) VALUES ('BLOQUEADO', 'Bloqueado');
INSERT INTO status (codigo, nome) VALUES ('RESERVADO', 'Reservado');
