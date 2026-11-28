package com.BearShelf.projeto.dto;

import java.util.Set;

public record LivroResponse(
        int id,
        String titulo,
        Integer anoPublicacao,
        String descricao,
        Integer totalPaginas,
        Set<AutorResumoResponse> autores,
        Set<CategoriaResumoResponse> categorias) {
}
