package com.BearShelf.projeto.service;

import com.BearShelf.projeto.entity.Categoria;

public class CategoriaService {

    public Categoria buscarCategoria() {
        return new Categoria(1, "Ficção");
    }
}
