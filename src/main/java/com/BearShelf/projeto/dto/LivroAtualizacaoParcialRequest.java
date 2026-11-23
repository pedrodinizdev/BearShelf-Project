package com.BearShelf.projeto.dto;

import java.util.Set;

public record LivroAtualizacaoParcialRequest(
        String titulo,
        Integer anoPublicacao,
        String descricao,
        Integer totalPaginas,
        Set<Integer> autorIds,
        Set<Integer> categoriaIds) {
}