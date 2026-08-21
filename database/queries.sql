-- 1. Lista todos os usuários cadastrados.
SELECT id_usuario, nome, email, data_cadastro
FROM Usuario;

-- 2. Lista todos os livros cadastrados.
SELECT id_livro, titulo, ano_publicacao, total_paginas
FROM Livro;

-- 3. Lista todos os autores cadastrados.
SELECT id_autor, nome, nacionalidade
FROM Autor;

-- 4. Lista cada livro com o nome do seu autor.
SELECT l.titulo, a.nome AS autor
FROM Livro l
JOIN Livro_Autor la ON la.id_livro = l.id_livro
JOIN Autor a ON a.id_autor = la.id_autor
ORDER BY l.titulo ASC;

-- 5. Lista os livros escritos por Rick Riordan.
SELECT l.titulo, l.ano_publicacao
FROM Livro l
JOIN Livro_Autor la ON la.id_livro = l.id_livro
JOIN Autor a ON a.id_autor = la.id_autor
WHERE a.nome = 'Rick Riordan';

-- 6. Lista as categorias de cada livro.
SELECT l.titulo, c.nome AS categoria
FROM Livro l
JOIN Livro_Categoria lc ON lc.id_livro = l.id_livro
JOIN Categoria c ON c.id_categoria = lc.id_categoria
ORDER BY l.titulo ASC;

-- 7. Conta quantos livros cada usuário tem na sua biblioteca.
SELECT u.nome, COUNT(b.id_livro) AS total_livros
FROM Usuario u
JOIN Biblioteca b ON b.id_usuario = u.id_usuario
GROUP BY u.nome
ORDER BY total_livros DESC;

-- 8. Conta quantos livros existem em cada categoria.
SELECT c.nome AS categoria, COUNT(lc.id_livro) AS total_livros
FROM Categoria c
JOIN Livro_Categoria lc ON lc.id_categoria = c.id_categoria
GROUP BY c.nome
ORDER BY total_livros DESC;

-- 9. Lista os livros escritos por autores de nacionalidade brasileira.
SELECT l.titulo, a.nome AS autor
FROM Livro l
JOIN Livro_Autor la ON la.id_livro = l.id_livro
JOIN Autor a ON a.id_autor = la.id_autor
WHERE a.nacionalidade = 'Brasileira';

-- 10. Mostra o livro que está na biblioteca do maior número de usuários.
SELECT l.titulo, COUNT(b.id_usuario) AS total_usuarios
FROM Livro l
JOIN Biblioteca b ON b.id_livro = l.id_livro
GROUP BY l.titulo
ORDER BY total_usuarios DESC
LIMIT 1;

-- 11. Mostra o último acesso de cada usuário em qualquer livro.
SELECT u.nome, MAX(le.ultimo_acesso) AS ultimo_acesso
FROM Leitura le
JOIN Usuario u ON u.id_usuario = le.id_usuario
GROUP BY u.nome
ORDER BY ultimo_acesso DESC;

-- 12. Lista os livros publicados antes do ano 1900, do mais antigo para o mais recente.
SELECT titulo, ano_publicacao
FROM Livro
WHERE ano_publicacao < 1900
ORDER BY ano_publicacao ASC;

-- 13. Mostra o progresso de leitura de cada usuário em cada livro.
SELECT u.nome, l.titulo, le.pagina_atual, l.total_paginas
FROM Leitura le
JOIN Usuario u ON u.id_usuario = le.id_usuario
JOIN Livro l ON l.id_livro = le.id_livro
ORDER BY u.nome ASC;

-- 14. Lista os usuários que já concluíram pelo menos um livro.
SELECT u.nome, l.titulo
FROM Leitura le
JOIN Usuario u ON u.id_usuario = le.id_usuario
JOIN Livro l ON l.id_livro = le.id_livro
WHERE le.pagina_atual = l.total_paginas;

-- 15. Lista os livros que pertencem a mais de uma categoria.
SELECT l.titulo, COUNT(lc.id_categoria) AS total_categorias
FROM Livro l
JOIN Livro_Categoria lc ON lc.id_livro = l.id_livro
GROUP BY l.titulo
HAVING COUNT(lc.id_categoria) > 1
ORDER BY total_categorias DESC;
