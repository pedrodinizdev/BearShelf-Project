package com.BearShelf.projeto.dto;

import java.util.Set;

public record LivroAtualizacaoRequest(
        String titulo,
        Integer anoPublicacao,
        String descricao,
        Integer totalPaginas,
        Set<Integer> autorIds,
        Set<Integer> categoriaIds) {
}
