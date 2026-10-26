-- Baseado no diagrama do banco (MySQL Workbench), adaptado para H2:
--  * INT  -> BIGINT (as entities usam Long, como nas lições)
--  * TINYINT (ativo) -> BOOLEAN
--  * ENUM(...) -> VARCHAR (valores controlados pelo enum Java com @Enumerated(STRING))

CREATE TABLE IF NOT EXISTS mesas (
    id            BIGINT       PRIMARY KEY AUTO_INCREMENT,
    numero        INT          NOT NULL,
    qr_code_hash  VARCHAR(255) NOT NULL,
    status        VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS usuarios (
    id         BIGINT       PRIMARY KEY AUTO_INCREMENT,
    nome       VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    senha      VARCHAR(255) NOT NULL,
    cargo      VARCHAR(20)  NOT NULL,
    ativo      BOOLEAN      NOT NULL DEFAULT TRUE,
    criado_em  TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categorias (
    id    BIGINT       PRIMARY KEY AUTO_INCREMENT,
    nome  VARCHAR(50)  NOT NULL
);

CREATE TABLE IF NOT EXISTS produtos (
    id            BIGINT         PRIMARY KEY AUTO_INCREMENT,
    id_categoria  BIGINT         NOT NULL,
    nome          VARCHAR(100)   NOT NULL,
    descricao     VARCHAR(1000),
    preco         DECIMAL(10, 2) NOT NULL,
    ativo         BOOLEAN        NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_produtos_categoria FOREIGN KEY (id_categoria) REFERENCES categorias (id)
);

CREATE TABLE IF NOT EXISTS ingredientes (
    id             BIGINT       PRIMARY KEY AUTO_INCREMENT,
    nome           VARCHAR(100) NOT NULL,
    unidade_medida VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS fichas_tecnicas (
    id_produto       BIGINT         NOT NULL,
    id_ingrediente   BIGINT         NOT NULL,
    quantidade_usada DECIMAL(10, 3) NOT NULL,
    PRIMARY KEY (id_produto, id_ingrediente),
    CONSTRAINT fk_ficha_produto     FOREIGN KEY (id_produto)     REFERENCES produtos (id),
    CONSTRAINT fk_ficha_ingrediente FOREIGN KEY (id_ingrediente) REFERENCES ingredientes (id)
);

CREATE TABLE IF NOT EXISTS pedidos (
    id                 BIGINT      PRIMARY KEY AUTO_INCREMENT,
    id_usuario_garcom  BIGINT,
    status             VARCHAR(20) NOT NULL,
    criado_em          TIMESTAMP   NOT NULL,
    atualizado_em      TIMESTAMP,
    mesas_id           BIGINT      NOT NULL,
    CONSTRAINT fk_pedidos_garcom FOREIGN KEY (id_usuario_garcom) REFERENCES usuarios (id),
    CONSTRAINT fk_pedidos_mesa   FOREIGN KEY (mesas_id)          REFERENCES mesas (id)
);

CREATE TABLE IF NOT EXISTS itens_pedido (
    id             BIGINT         PRIMARY KEY AUTO_INCREMENT,
    id_produto     BIGINT         NOT NULL,
    id_pedido      BIGINT         NOT NULL,
    quantidade     INT            NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    observacao     VARCHAR(255),
    CONSTRAINT fk_itens_produto FOREIGN KEY (id_produto) REFERENCES produtos (id),
    CONSTRAINT fk_itens_pedido  FOREIGN KEY (id_pedido)  REFERENCES pedidos (id)
);
