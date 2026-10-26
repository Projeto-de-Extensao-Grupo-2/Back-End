INSERT INTO mesas (numero, qr_code_hash, status)
SELECT 1, 'hash-mesa-1', 'OCUPADA' WHERE NOT EXISTS (SELECT 1 FROM mesas WHERE numero = 1);
INSERT INTO mesas (numero, qr_code_hash, status)
SELECT 2, 'hash-mesa-2', 'OCUPADA' WHERE NOT EXISTS (SELECT 1 FROM mesas WHERE numero = 2);
INSERT INTO mesas (numero, qr_code_hash, status)
SELECT 3, 'hash-mesa-3', 'LIVRE' WHERE NOT EXISTS (SELECT 1 FROM mesas WHERE numero = 3);

INSERT INTO usuarios (nome, email, senha, cargo, ativo, criado_em)
SELECT 'Carlos Garçom', 'carlos@smartplate.com', '123456', 'GARCOM', TRUE, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'carlos@smartplate.com');
INSERT INTO usuarios (nome, email, senha, cargo, ativo, criado_em)
SELECT 'Marina Garçonete', 'marina@smartplate.com', '123456', 'GARCOM', TRUE, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'marina@smartplate.com');
INSERT INTO usuarios (nome, email, senha, cargo, ativo, criado_em)
SELECT 'João Inativo', 'joao@smartplate.com', '123456', 'GARCOM', FALSE, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'joao@smartplate.com');

INSERT INTO categorias (nome)
SELECT 'Pratos' WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nome = 'Pratos');

INSERT INTO produtos (id_categoria, nome, descricao, preco, ativo)
SELECT (SELECT id FROM categorias WHERE nome = 'Pratos'), 'Hambúrguer Artesanal', 'Pão brioche, blend 180g e queijo', 32.90, TRUE
WHERE NOT EXISTS (SELECT 1 FROM produtos WHERE nome = 'Hambúrguer Artesanal');
INSERT INTO produtos (id_categoria, nome, descricao, preco, ativo)
SELECT (SELECT id FROM categorias WHERE nome = 'Pratos'), 'Porção de Batata Frita', 'Porção individual', 18.50, TRUE
WHERE NOT EXISTS (SELECT 1 FROM produtos WHERE nome = 'Porção de Batata Frita');

-- Pedidos de exemplo (cada INSERT só entra na ordem certa, evitando duplicar se rodar de novo)
INSERT INTO pedidos (id_usuario_garcom, status, criado_em, atualizado_em, mesas_id)
SELECT (SELECT id FROM usuarios WHERE email = 'carlos@smartplate.com'), 'RECEBIDO', CURRENT_TIMESTAMP, NULL,
       (SELECT id FROM mesas WHERE numero = 1)
WHERE (SELECT COUNT(*) FROM pedidos) = 0;

INSERT INTO pedidos (id_usuario_garcom, status, criado_em, atualizado_em, mesas_id)
SELECT (SELECT id FROM usuarios WHERE email = 'marina@smartplate.com'), 'EM_PREPARO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
       (SELECT id FROM mesas WHERE numero = 2)
WHERE (SELECT COUNT(*) FROM pedidos) = 1;

INSERT INTO pedidos (id_usuario_garcom, status, criado_em, atualizado_em, mesas_id)
SELECT NULL, 'PRONTO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
       (SELECT id FROM mesas WHERE numero = 2)
WHERE (SELECT COUNT(*) FROM pedidos) = 2;

-- Itens do primeiro pedido (para provar que o DELETE /pedidos/{id} remove os itens antes)
INSERT INTO itens_pedido (id_produto, id_pedido, quantidade, preco_unitario, observacao)
SELECT (SELECT id FROM produtos WHERE nome = 'Hambúrguer Artesanal'), (SELECT MIN(id) FROM pedidos), 2, 32.90, 'Sem cebola'
WHERE (SELECT COUNT(*) FROM itens_pedido) = 0;
INSERT INTO itens_pedido (id_produto, id_pedido, quantidade, preco_unitario, observacao)
SELECT (SELECT id FROM produtos WHERE nome = 'Porção de Batata Frita'), (SELECT MIN(id) FROM pedidos), 1, 18.50, NULL
WHERE (SELECT COUNT(*) FROM itens_pedido) = 1;
