insert genero (codigo_genero, nome_genero) values (1, 'luta');
insert desenvolvedor (codigo_dev, nome_dev) values (1, 'Midway Games');

insert jogo (codigo_jogo, preco_jogo, titulo_jogo, desenvolvedor_codigo_dev, genero_codigo_genero) values (1, 99, 'Mortal Kombat 11', 1, 1);
insert usuario (codigo, cpf, email, nome, senha) values (1, 41727683854, 'danielddamiao@icloud.com', 'Daniel Dutra', 123456);
insert compra (codigo_compra, jogo_codigo_jogo, usuario_codigo) values (1, 1, 1);