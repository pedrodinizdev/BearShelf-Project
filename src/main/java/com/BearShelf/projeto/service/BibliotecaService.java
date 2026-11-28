package com.BearShelf.projeto.service;

import com.BearShelf.projeto.dto.BibliotecaCadastroRequest;
import com.BearShelf.projeto.dto.BibliotecaResponse;
import com.BearShelf.projeto.entity.*;
import com.BearShelf.projeto.repository.BibliotecaRepository;
import com.BearShelf.projeto.repository.LeituraRepository;
import com.BearShelf.projeto.repository.LivroRepository;
import com.BearShelf.projeto.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final LeituraRepository leituraRepository;

    public BibliotecaService(
            BibliotecaRepository bibliotecaRepository,
            UsuarioRepository usuarioRepository,
            LivroRepository livroRepository,
            LeituraRepository leituraRepository) {

        this.bibliotecaRepository = bibliotecaRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
        this.leituraRepository = leituraRepository;
    }

    // POST
    @Transactional
    public BibliotecaResponse adicionarLivro(
            BibliotecaCadastroRequest dados) {

        validarId(dados.usuarioId(), "O ID do usuário é obrigatório");
        validarId(dados.livroId(), "O ID do livro é obrigatório");

        Usuario usuario = usuarioRepository.findById(dados.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        Livro livro = livroRepository.findById(dados.livroId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Livro não encontrado"
                ));

        BibliotecaId id = new BibliotecaId(
                usuario.getId(),
                livro.getId()
        );

        if (bibliotecaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "O livro já está na biblioteca do usuário"
            );
        }

        Biblioteca biblioteca = new Biblioteca(
                usuario,
                livro,
                LocalDate.now()
        );

        Biblioteca bibliotecaSalva =
                bibliotecaRepository.save(biblioteca);

        return criarResponse(bibliotecaSalva);
    }

    // Get
    @Transactional(readOnly = true)
    public List<BibliotecaResponse> listarBiblioteca(int usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado"
            );
        }

        List<Biblioteca> registros =
                bibliotecaRepository.findAllByUsuario_Id(usuarioId);

        List<BibliotecaResponse> respostas = new ArrayList<>();

        for (Biblioteca biblioteca : registros) {
            respostas.add(criarResponse(biblioteca));
        }

        return respostas;
    }

    // DEL
    //confere se tá na biblioteca do usuario, verifica se existe progresso de leitura, se tiver apaga progresso primeiro
    @Transactional
    public void removerLivro(int usuarioId, int livroId) {
        BibliotecaId id = new BibliotecaId(usuarioId, livroId);

        Biblioteca biblioteca = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Livro não encontrado na biblioteca do usuário"
                ));

        LeituraId leituraId = new LeituraId(usuarioId, livroId);

        if (leituraRepository.existsById(leituraId)) {
            leituraRepository.deleteById(leituraId);
        }

        bibliotecaRepository.delete(biblioteca);
    }

    private BibliotecaResponse criarResponse(Biblioteca biblioteca) {
        return new BibliotecaResponse(
                biblioteca.getUsuario().getId(),
                biblioteca.getLivro().getId(),
                biblioteca.getLivro().getTitulo(),
                biblioteca.getDataAdicao()
        );
    }

    private void validarId(Integer id, String mensagem) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    mensagem
            );
        }
    }
}