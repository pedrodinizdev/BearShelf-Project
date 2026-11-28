package com.BearShelf.projeto.dto;

import java.time.LocalDate;

public record BibliotecaResponse(
        int usuarioId,
        int livroId,
        String titulo,
        LocalDate dataAdicao) {
}