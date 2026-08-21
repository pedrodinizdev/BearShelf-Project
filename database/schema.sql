CREATE TABLE Usuario (
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    data_cadastro DATE NOT NULL
);

CREATE TABLE Autor (
    id_autor SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nacionalidade VARCHAR(60)
);

CREATE TABLE Categoria (
    id_categoria SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE Livro (
    id_livro SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    ano_publicacao INT,
    descricao TEXT,
    total_paginas INT
);

CREATE TABLE Livro_Autor (
    id_livro INT NOT NULL REFERENCES Livro(id_livro),
    id_autor INT NOT NULL REFERENCES Autor(id_autor),
    PRIMARY KEY (id_livro, id_autor)
);

CREATE TABLE Livro_Categoria (
    id_livro INT NOT NULL REFERENCES Livro(id_livro),
    id_categoria INT NOT NULL REFERENCES Categoria(id_categoria),
    PRIMARY KEY (id_livro, id_categoria)
);

CREATE TABLE Biblioteca (
    id_usuario INT NOT NULL REFERENCES Usuario(id_usuario),
    id_livro INT NOT NULL REFERENCES Livro(id_livro),
    data_adicao DATE NOT NULL,
    PRIMARY KEY (id_usuario, id_livro)
);

CREATE TABLE Leitura (
    id_usuario INT NOT NULL REFERENCES Usuario(id_usuario),
    id_livro INT NOT NULL REFERENCES Livro(id_livro),
    pagina_atual INT DEFAULT 0,
    ultimo_acesso TIMESTAMP,
    PRIMARY KEY (id_usuario, id_livro)
);
