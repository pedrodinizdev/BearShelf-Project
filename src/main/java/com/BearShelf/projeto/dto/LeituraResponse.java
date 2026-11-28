package com.BearShelf.projeto.dto;

import java.time.LocalDateTime;

public record LeituraResponse(
        int usuarioId,
        int livroId,
        String titulo,
        int paginaAtual,
        int totalPaginas,
        LocalDateTime ultimoAcesso) {
}