package com.BearShelf.projeto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "biblioteca")
public class Biblioteca {

    // puxa o BibliotecaId como chave por conta do Embeddeable
    @EmbeddedId
    private BibliotecaId id;

    @MapsId("usuarioId")
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @MapsId("livroId")
    @ManyToOne
    @JoinColumn(name = "id_livro")
    private Livro livro;

    @Column(name = "data_adicao", nullable = false)
    private LocalDate dataAdicao;

    protected Biblioteca() {
    }

    public Biblioteca(
            Usuario usuario,
            Livro livro,
            LocalDate dataAdicao) {

        this.id = new BibliotecaId(
                usuario.getId(),
                livro.getId()
        );

        this.usuario = usuario;
        this.livro = livro;
        this.dataAdicao = dataAdicao;
    }

    public BibliotecaId getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public LocalDate getDataAdicao() {
        return dataAdicao;
    }
}