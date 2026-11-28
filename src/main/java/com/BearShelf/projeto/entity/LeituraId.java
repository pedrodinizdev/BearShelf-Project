package com.BearShelf.projeto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LeituraId implements Serializable {

    @Column(name = "id_usuario")
    private Integer usuarioId;

    @Column(name = "id_livro")
    private Integer livroId;

    protected LeituraId() {
    }

    public LeituraId(Integer usuarioId, Integer livroId) {
        this.usuarioId = usuarioId;
        this.livroId = livroId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public Integer getLivroId() {
        return livroId;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof LeituraId outroId)) {
            return false;
        }

        return Objects.equals(usuarioId, outroId.usuarioId)
                && Objects.equals(livroId, outroId.livroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, livroId);
    }
}