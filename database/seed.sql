INSERT INTO Categoria (nome)
VALUES
  ('Fantasia'),
  ('Aventura'),
  ('Mistério'),
  ('Ficção Científica'),
  ('Literatura Clássica'),
  ('Poesia'),
  ('Tecnologia'),
  ('Educação'),
  ('Romance'),
  ('Histórico'),
  ('Ação'),
  ('Infanto-Juvenil');

INSERT INTO Autor (nome, nacionalidade)
VALUES
  ('Rick Riordan', 'Americana'),
  ('J.K. Rowling', 'Britânica'),
  ('Patrick Rothfuss', 'Americana'),
  ('Agatha Christie', 'Britânica'),
  ('Arthur Conan Doyle', 'Britânica'),
  ('Machado de Assis', 'Brasileira'),
  ('Isaac Asimov', 'Americana'),
  ('Homero', 'Grega'),
  ('Gabriel García Márquez', 'Colombiana'),
  ('J.R.R. Tolkien', 'Britânica'),
  ('Castro Alves', 'Brasileira'),
  ('Jane Austen', 'Britânica'),
  ('Terry Pratchett', 'Britânica'),
  ('Neil Gaiman', 'Britânica'),
  ('Steven Levitt', 'Americana'),
  ('Stephen Dubner', 'Americana'),
  ('Aditya Y. Bhargava', 'Americana'),
  ('Ramez Elmasri', 'Americana'),
  ('Shamkant Navathe', 'Americana');

INSERT INTO Usuario (nome, email, data_cadastro)
VALUES
  ('Lucas Ferreira', 'lucas.ferreira@email.com', '2026-01-10'),
  ('Ana Paula Costa', 'ana.costa@email.com', '2026-01-23'),
  ('Mateus Oliveira', 'mateus.oliveira@email.com','2026-02-05'),
  ('Juliana Santos', 'juliana.santos@email.com', '2026-02-18'),
  ('Rafael Almeida', 'rafael.almeida@email.com', '2026-03-02'),
  ('Fernanda Lima', 'fernanda.lima@email.com','2026-03-15'),
  ('Bruno Souza', 'bruno.souza@email.com', '2026-03-28'),
  ('Camila Rodrigues', 'camila.rodrigues@email.com', '2026-04-10'),
  ('Diego Martins', 'diego.martins@email.com', '2026-04-24'),
  ('Isabela Carvalho','isabela.carvalho@email.com', '2026-05-08');

INSERT INTO Livro (titulo, ano_publicacao, descricao, total_paginas)
VALUES
  ('Percy Jackson e o Ladrão de Raios',2005, 'Percy descobre ser filho de Poseidon e parte em uma missão para evitar uma guerra entre os deuses gregos.', 377),
  ('Percy Jackson e o Mar de Monstros', 2006, 'Percy viaja ao Mar de Monstros para salvar seu amigo Grover e o acampamento Meias-Sangue.', 279),
  ('Percy Jackson e a Maldição do Titã',2007, 'Percy e seus amigos enfrentam o exército de Cronos enquanto buscam dois semideuses desaparecidos.', 294),
  ('Percy Jackson e a Batalha do Labirinto', 2008, 'Percy adentra o labirinto de Dédalo para impedir que o exército de Cronos invada o acampamento.', 361),
  ('Percy Jackson e o Último Olimpiano',2009, 'A batalha final entre Percy e Cronos pelo destino do Monte Olimpo e do mundo dos semideuses.', 381),
  ('Harry Potter e a Pedra Filosofal', 1997, 'Harry descobre ser um bruxo e inicia seus estudos em Hogwarts, onde enfrenta seu primeiro grande perigo.', 223),
  ('O Nome do Vento', 2007, 'Kvothe narra sua própria lenda: de órfão nas ruas até o mago mais temido de sua época.', 662),
  ('Assassinato no Expresso Oriente', 1934, 'Hercule Poirot investiga um assassinato ocorrido dentro de um trem bloqueado pela neve.', 256),
  ('Morte no Nilo', 1937, 'Poirot investiga o assassinato de uma jovem herdeira durante um cruzeiro pelo rio Nilo no Egito.', 288),
  ('Um Estudo em Vermelho', 1887, 'A primeira aparição de Sherlock Holmes, que investiga um assassinato misterioso em Londres.', 176),
  ('Dom Casmurro', 1899, 'Bentinho narra sua vida e seu ciúme obsessivo por Capitu, um dos maiores clássicos do Realismo brasileiro.', 256),
  ('Memórias Póstumas de Brás Cubas', 1881, 'Um defunto-autor narra sua própria vida com ironia e pessimismo, inaugurando o Realismo no Brasil.', 141),
  ('Eu, Robô',  1950, 'Coletânea de contos que explora as Três Leis da Robótica e as consequências filosóficas da inteligência artificial.', 253),
  ('A Odisseia', -700, 'O épico retorno de Odisseu para casa após a Guerra de Troia, enfrentando monstros, deuses e tentações.', 541),
  ('Cem Anos de Solidão', 1967, 'A saga da família Buendía ao longo de sete gerações na cidade fictícia de Macondo.', 448),
  ('A Sociedade do Anel',1954, 'Frodo herda o Um Anel e parte em uma jornada para destruí-lo, acompanhado por uma sociedade de companheiros.', 481),
  ('Espumas Flutuantes',   1870, 'Primeiro livro de poesias de Castro Alves, com forte apelo romântico e abolicionista.', 98),
  ('Orgulho e Preconceito',  1813, 'Elizabeth Bennet e Mr. Darcy navegam entre orgulho, preconceito e amor na Inglaterra do século XIX.', 432),
  ('O Bom Presságio',   1990, 'Um anjo e um demônio unem forças para evitar o Apocalipse numa comédia sobre o fim do mundo.', 413),
  ('Freakonomics',   2005, 'Economistas aplicam raciocínio econômico a situações cotidianas surpreendentes, revelando padrões ocultos.', 268),
  ('Entendendo Algoritmos',  2016, 'Introdução visual e acessível a algoritmos fundamentais, com ilustrações que facilitam o aprendizado.', 256),
  ('Sistemas de Banco de Dados', 2010, 'Referência completa sobre modelagem, projeto e implementação de bancos de dados relacionais.', 1024);

INSERT INTO Livro_Autor (id_livro, id_autor)
VALUES
  (1,  1),  -- Percy Jackson 1       → Rick Riordan
  (2,  1),  -- Percy Jackson 2       → Rick Riordan
  (3,  1),  -- Percy Jackson 3       → Rick Riordan
  (4,  1),  -- Percy Jackson 4       → Rick Riordan
  (5,  1),  -- Percy Jackson 5       → Rick Riordan
  (6,  2),  -- Harry Potter          → J.K. Rowling
  (7,  3),  -- O Nome do Vento       → Patrick Rothfuss
  (8,  4),  -- Expresso Oriente      → Agatha Christie
  (9,  4),  -- Morte no Nilo         → Agatha Christie
  (10, 5),  -- Estudo em Vermelho    → Arthur Conan Doyle
  (11, 6),  -- Dom Casmurro          → Machado de Assis
  (12, 6),  -- Memórias Póstumas     → Machado de Assis
  (13, 7),  -- Eu, Robô              → Isaac Asimov
  (14, 8),  -- A Odisseia            → Homero
  (15, 9),  -- Cem Anos de Solidão   → Gabriel García Márquez
  (16, 10), -- A Sociedade do Anel   → J.R.R. Tolkien
  (17, 11), -- Espumas Flutuantes    → Castro Alves
  (18, 12), -- Orgulho e Preconceito → Jane Austen
  (19, 13), -- O Bom Presságio       → Terry Pratchett
  (19, 14), -- O Bom Presságio       → Neil Gaiman
  (20, 15), -- Freakonomics          → Steven Levitt
  (20, 16), -- Freakonomics          → Stephen Dubner
  (21, 17), -- Entendendo Algoritmos → Aditya Y. Bhargava
  (22, 18); -- Sistemas de BD        → Ramez Elmasri
  (22, 19); -- Sistemas de BD → Shamkant Navathe
  

INSERT INTO Livro_Categoria (id_livro, id_categoria)
VALUES
  (1,  1),  -- Percy Jackson 1       → Fantasia
  (1,  2),  -- Percy Jackson 1       → Aventura
  (1,  12), -- Percy Jackson 1       → Infanto-Juvenil
  (2,  1),  -- Percy Jackson 2       → Fantasia
  (2,  2),  -- Percy Jackson 2       → Aventura
  (2,  12), -- Percy Jackson 2       → Infanto-Juvenil
  (3,  1),  -- Percy Jackson 3       → Fantasia
  (3,  2),  -- Percy Jackson 3       → Aventura
  (3,  12), -- Percy Jackson 3       → Infanto-Juvenil
  (4,  1),  -- Percy Jackson 4       → Fantasia
  (4,  2),  -- Percy Jackson 4       → Aventura
  (4,  12), -- Percy Jackson 4       → Infanto-Juvenil
  (5,  1),  -- Percy Jackson 5       → Fantasia
  (5,  2),  -- Percy Jackson 5       → Aventura
  (5,  12), -- Percy Jackson 5       → Infanto-Juvenil
  (6,  1),  -- Harry Potter          → Fantasia
  (6,  12), -- Harry Potter          → Infanto-Juvenil
  (7,  1),  -- O Nome do Vento       → Fantasia
  (8,  3),  -- Expresso Oriente      → Mistério
  (9,  3),  -- Morte no Nilo         → Mistério
  (9,  10), -- Morte no Nilo         → Histórico
  (10, 3),  -- Estudo em Vermelho    → Mistério
  (11, 5),  -- Dom Casmurro          → Literatura Clássica
  (11, 9),  -- Dom Casmurro          → Romance
  (12, 5),  -- Memórias Póstumas     → Literatura Clássica
  (13, 4),  -- Eu, Robô              → Ficção Científica
  (14, 5),  -- A Odisseia            → Literatura Clássica
  (14, 2),  -- A Odisseia            → Aventura
  (14, 10), -- A Odisseia            → Histórico
  (15, 5),  -- Cem Anos de Solidão   → Literatura Clássica
  (15, 9),  -- Cem Anos de Solidão   → Romance
  (16, 1),  -- A Sociedade do Anel   → Fantasia
  (16, 2),  -- A Sociedade do Anel   → Aventura
  (17, 6),  -- Espumas Flutuantes    → Poesia
  (17, 5),  -- Espumas Flutuantes    → Literatura Clássica
  (18, 9),  -- Orgulho e Preconceito → Romance
  (18, 5),  -- Orgulho e Preconceito → Literatura Clássica
  (19, 1),  -- O Bom Presságio       → Fantasia
  (19, 11), -- O Bom Presságio       → Ação
  (20, 8),  -- Freakonomics          → Educação
  (21, 7),  -- Entendendo Algoritmos → Tecnologia
  (21, 8),  -- Entendendo Algoritmos → Educação
  (22, 7),  -- Sistemas de BD        → Tecnologia
  (22, 8);  -- Sistemas de BD        → Educação

INSERT INTO Biblioteca (id_usuario, id_livro, data_adicao)
VALUES
  (1, 1,  '2026-01-12'),  -- Lucas    → Percy Jackson 1
  (1, 2,  '2026-01-12'),  -- Lucas    → Percy Jackson 2
  (1, 3,  '2026-01-15'),  -- Lucas    → Percy Jackson 3
  (1, 4,  '2026-01-15'),  -- Lucas    → Percy Jackson 4
  (1, 5,  '2026-01-20'),  -- Lucas    → Percy Jackson 5
  (1, 6,  '2026-01-25'),  -- Lucas    → Harry Potter
  (1, 16, '2026-02-01'),  -- Lucas    → A Sociedade do Anel
  (1, 13, '2026-02-10'),  -- Lucas    → Eu, Robô
  (2, 11, '2026-01-24'),  -- Ana      → Dom Casmurro
  (2, 12, '2026-01-24'),  -- Ana      → Memórias Póstumas
  (2, 15, '2026-02-01'),  -- Ana      → Cem Anos de Solidão
  (2, 18, '2026-02-14'),  -- Ana      → Orgulho e Preconceito
  (2, 17, '2026-03-01'),  -- Ana      → Espumas Flutuantes
  (3, 21, '2026-02-06'),  -- Mateus   → Entendendo Algoritmos
  (3, 22, '2026-02-06'),  -- Mateus   → Sistemas de BD
  (3, 20, '2026-02-20'),  -- Mateus   → Freakonomics
  (4, 8,  '2026-02-19'),  -- Juliana  → Expresso Oriente
  (4, 9,  '2026-02-19'),  -- Juliana  → Morte no Nilo
  (4, 10, '2026-02-25'),  -- Juliana  → Estudo em Vermelho
  (4, 14, '2026-03-05'),  -- Juliana  → A Odisseia
  (5, 1,  '2026-03-03'),  -- Rafael   → Percy Jackson 1
  (5, 7,  '2026-03-10'),  -- Rafael   → O Nome do Vento
  (5, 19, '2026-03-20'),  -- Rafael   → O Bom Presságio
  (5, 16, '2026-04-01'),  -- Rafael   → A Sociedade do Anel
  (5, 6,  '2026-04-05'),  -- Rafael   → Harry Potter
  (6, 11, '2026-03-16'),  -- Fernanda → Dom Casmurro
  (6, 18, '2026-03-16'),  -- Fernanda → Orgulho e Preconceito
  (6, 8,  '2026-03-22'),  -- Fernanda → Expresso Oriente
  (6, 13, '2026-04-01'),  -- Fernanda → Eu, Robô
  (6, 15, '2026-04-10'),  -- Fernanda → Cem Anos de Solidão
  (6, 19, '2026-04-20'),  -- Fernanda → O Bom Presságio
  (7, 21, '2026-03-29'),  -- Bruno    → Entendendo Algoritmos
  (7, 22, '2026-04-05'),  -- Bruno    → Sistemas de BD
  (8, 14, '2026-04-11'),  -- Camila   → A Odisseia
  (8, 17, '2026-04-11'),  -- Camila   → Espumas Flutuantes
  (8, 12, '2026-04-18'),  -- Camila   → Memórias Póstumas
  (8, 15, '2026-04-25'),  -- Camila   → Cem Anos de Solidão
  (9, 1,  '2026-04-25'),  -- Diego    → Percy Jackson 1
  (9, 5,  '2026-04-25'),  -- Diego    → Percy Jackson 5
  (9, 7,  '2026-05-01'),  -- Diego    → O Nome do Vento
  (9, 19, '2026-05-10'),  -- Diego    → O Bom Presságio
  (10, 6,  '2026-05-09'), -- Isabela  → Harry Potter
  (10, 21, '2026-05-09'); -- Isabela  → Entendendo Algoritmos

INSERT INTO Leitura (id_usuario, id_livro, pagina_atual, ultimo_acesso)
VALUES
  (1, 1,  377, '2026-02-01 20:15:00'),  -- Lucas    → Percy Jackson 1    (concluído)
  (1, 2,  279, '2026-02-08 21:00:00'),  -- Lucas    → Percy Jackson 2    (concluído)
  (1, 3,  150, '2026-02-20 19:30:00'),  -- Lucas    → Percy Jackson 3    (no meio)
  (1, 6,  80,  '2026-03-05 22:00:00'),  -- Lucas    → Harry Potter       (começou)
  (1, 13, 50,  '2026-04-01 18:45:00'),  -- Lucas    → Eu, Robô           (começou)
  (2, 11, 256, '2026-02-10 21:30:00'),  -- Ana      → Dom Casmurro       (concluído)
  (2, 12, 100, '2026-02-20 20:00:00'),  -- Ana      → Memórias Póstumas  (no meio)
  (2, 18, 432, '2026-03-15 22:15:00'),  -- Ana      → Orgulho e Preconceit (concluído)
  (2, 17, 40,  '2026-04-02 19:00:00'),  -- Ana      → Espumas Flutuantes (começou)
  (3, 21, 256, '2026-03-01 20:00:00'),  -- Mateus   → Entendendo Alg.    (concluído)
  (3, 22, 300, '2026-04-10 21:00:00'),  -- Mateus   → Sistemas de BD     (no meio)
  (3, 20, 100, '2026-05-01 19:30:00'),  -- Mateus   → Freakonomics       (no meio)
  (4, 8,  256, '2026-03-10 20:30:00'),  -- Juliana  → Expresso Oriente   (concluído)
  (4, 9,  150, '2026-03-20 21:00:00'),  -- Juliana  → Morte no Nilo      (no meio)
  (4, 10, 0,   '2026-03-25 18:00:00'),  -- Juliana  → Estudo em Vermelho (não começou)
  (5, 1,  377, '2026-03-15 22:00:00'),  -- Rafael   → Percy Jackson 1    (concluído)
  (5, 7,  400, '2026-04-20 21:30:00'),  -- Rafael   → O Nome do Vento    (no meio)
  (5, 19, 200, '2026-05-10 20:00:00'),  -- Rafael   → O Bom Presságio    (no meio)
  (6, 11, 200, '2026-04-05 19:45:00'),  -- Fernanda → Dom Casmurro       (no meio)
  (6, 8,  256, '2026-04-15 21:00:00'),  -- Fernanda → Expresso Oriente   (concluído)
  (6, 13, 253, '2026-05-20 20:30:00'),  -- Fernanda → Eu, Robô           (concluído)
  (6, 19, 100, '2026-06-01 22:00:00'),  -- Fernanda → O Bom Presságio    (no meio)
  (7, 21, 120, '2026-04-20 20:00:00'),  -- Bruno    → Entendendo Alg.    (no meio)
  (7, 22, 0,   '2026-04-25 18:30:00'),  -- Bruno    → Sistemas de BD     (não começou)
  (8, 14, 541, '2026-05-05 21:00:00'),  -- Camila   → A Odisseia         (concluído)
  (8, 17, 98,  '2026-05-12 19:30:00'),  -- Camila   → Espumas Flutuantes (concluído)
  (8, 15, 200, '2026-05-25 22:00:00'),  -- Camila   → Cem Anos de Solidão (no meio)
  (9, 1,  200, '2026-05-15 20:30:00'),  -- Diego    → Percy Jackson 1    (no meio)
  (9, 7,  0,   '2026-05-20 18:00:00'),  -- Diego    → O Nome do Vento    (não começou)
  (9, 19, 413, '2026-06-01 21:00:00'),  -- Diego    → O Bom Presságio    (concluído)
  (10, 6,  50, '2026-05-20 19:00:00'),  -- Isabela  → Harry Potter       (começou)
  (10, 21, 30, '2026-06-01 20:00:00');  -- Isabela  → Entendendo Alg.    (começou)


