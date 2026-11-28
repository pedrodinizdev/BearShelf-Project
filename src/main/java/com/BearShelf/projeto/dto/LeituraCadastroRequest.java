package com.BearShelf.projeto.dto;

public record LeituraCadastroRequest(
        Integer usuarioId,
        Integer livroId,
        Integer paginaAtual) {
}