package com.BearShelf.projeto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

// Sinaliza que não é uma tabela, vai entrar em outra entidade
@Embeddable
// Jpa pede que classes usadas como chave composta sejam serializable
public class BibliotecaId implements Serializable {

    @Column (name = "id_usuario")
    private Integer usuarioId;

    @Column (name = "id_livro")
    private Integer livroId;

    protected BibliotecaId() {
    }

    public BibliotecaId(Integer usuarioId, Integer livroId) {
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
        // id1 atual == id2
        if (this == objeto) {
            return true;
        }
        // pergunta se é do tipo correto
        if (!(objeto instanceof BibliotecaId outroId)) {
            return false;
        }
        //compara os ids
        return Objects.equals(this.usuarioId, outroId.usuarioId)
                && Objects.equals(livroId, outroId.livroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, livroId);
    }
}

