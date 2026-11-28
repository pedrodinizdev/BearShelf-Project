package com.BearShelf.projeto.dto;

import java.time.LocalDate;

public record UsuarioResponse(
        int id,
        String nome,
        String email,
        LocalDate dataCadastro) {
}