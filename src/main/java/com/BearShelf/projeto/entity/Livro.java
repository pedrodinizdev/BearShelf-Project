package com.BearShelf.projeto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livro")
    private int id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "total_paginas")
    private Integer totalPaginas;

    @ManyToMany
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "id_livro"),
            inverseJoinColumns = @JoinColumn(name = "id_autor")
    )
    private Set<Autor> autores = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "livro_categoria",
            joinColumns = @JoinColumn(name = "id_livro"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private Set<Categoria> categorias = new HashSet<>();

    protected Livro() {
    }

    public Livro(
            String titulo,
            Integer anoPublicacao,
            String descricao,
            Integer totalPaginas,
            Set<Autor> autores,
            Set<Categoria> categorias) {

        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.descricao = descricao;
        this.totalPaginas = totalPaginas;
        this.autores = new HashSet<>(autores);
        this.categorias = new HashSet<>(categorias);
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void atualizarDados(
            String titulo,
            Integer anoPublicacao,
            String descricao,
            Integer totalPaginas,
            Set<Autor> autores,
            Set<Categoria> categorias) {

        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.descricao = descricao;
        this.totalPaginas = totalPaginas;
        this.autores = new HashSet<>(autores);
        this.categorias = new HashSet<>(categorias);
    }
}
