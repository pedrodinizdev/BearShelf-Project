package com.BearShelf.projeto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "leitura")
public class Leitura {

    @EmbeddedId
    private LeituraId id;

    @MapsId("usuarioId")
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @MapsId("livroId")
    @ManyToOne
    @JoinColumn(name = "id_livro")
    private Livro livro;

    @Column(name = "pagina_atual")
    private Integer paginaAtual;

    @Column(name = "ultimo_acesso")
    private LocalDateTime ultimoAcesso;

    protected Leitura() {
    }

    public Leitura(
            Usuario usuario,
            Livro livro,
            Integer paginaAtual,
            LocalDateTime ultimoAcesso) {

        this.id = new LeituraId(
                usuario.getId(),
                livro.getId()
        );

        this.usuario = usuario;
        this.livro = livro;
        this.paginaAtual = paginaAtual;
        this.ultimoAcesso = ultimoAcesso;
    }

    public LeituraId getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public Integer getPaginaAtual() {
        return paginaAtual;
    }

    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void atualizarProgresso(
            Integer paginaAtual,
            LocalDateTime ultimoAcesso) {

        this.paginaAtual = paginaAtual;
        this.ultimoAcesso = ultimoAcesso;
    }
}