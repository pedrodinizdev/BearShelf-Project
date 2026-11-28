package com.BearShelf.projeto.service;

import com.BearShelf.projeto.dto.LeituraAtualizacaoRequest;
import com.BearShelf.projeto.dto.LeituraCadastroRequest;
import com.BearShelf.projeto.dto.LeituraResponse;
import com.BearShelf.projeto.entity.Biblioteca;
import com.BearShelf.projeto.entity.BibliotecaId;
import com.BearShelf.projeto.entity.Leitura;
import com.BearShelf.projeto.entity.LeituraId;
import com.BearShelf.projeto.entity.Livro;
import com.BearShelf.projeto.repository.BibliotecaRepository;
import com.BearShelf.projeto.repository.LeituraRepository;
import com.BearShelf.projeto.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeituraService {

    private final LeituraRepository leituraRepository;
    private final BibliotecaRepository bibliotecaRepository;
    private final UsuarioRepository usuarioRepository;

    public LeituraService(
            LeituraRepository leituraRepository,
            BibliotecaRepository bibliotecaRepository,
            UsuarioRepository usuarioRepository) {

        this.leituraRepository = leituraRepository;
        this.bibliotecaRepository = bibliotecaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // POST
    @Transactional
    public LeituraResponse iniciarLeitura(
            LeituraCadastroRequest dados) {

        validarId(dados.usuarioId(), "O ID do usuário é obrigatório");
        validarId(dados.livroId(), "O ID do livro é obrigatório");

        BibliotecaId bibliotecaId = new BibliotecaId(
                dados.usuarioId(),
                dados.livroId()
        );

        Biblioteca biblioteca = bibliotecaRepository.findById(bibliotecaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "O livro não está na biblioteca do usuário"
                ));

        LeituraId leituraId = new LeituraId(
                dados.usuarioId(),
                dados.livroId()
        );

        if (leituraRepository.existsById(leituraId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A leitura deste livro já foi iniciada"
            );
        }

        Integer paginaAtual = dados.paginaAtual();

        if (paginaAtual == null) {
            paginaAtual = 0;
        }

        paginaAtual = validarPagina(
                paginaAtual,
                biblioteca.getLivro()
        );

        Leitura leitura = new Leitura(
                biblioteca.getUsuario(),
                biblioteca.getLivro(),
                paginaAtual,
                LocalDateTime.now()
        );

        Leitura leituraSalva = leituraRepository.save(leitura);

        return criarResponse(leituraSalva);
    }

    // get
    @Transactional(readOnly = true)
    public List<LeituraResponse> listarLeituras(int usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado"
            );
        }

        List<Leitura> leituras =
                leituraRepository.findAllByUsuario_Id(usuarioId);

        List<LeituraResponse> respostas = new ArrayList<>();

        for (Leitura leitura : leituras) {
            respostas.add(criarResponse(leitura));
        }

        return respostas;
    }

    @Transactional(readOnly = true)
    public LeituraResponse buscarLeitura(
            int usuarioId,
            int livroId) {

        LeituraId id = new LeituraId(usuarioId, livroId);

        Leitura leitura = leituraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Leitura não encontrada"
                ));

        return criarResponse(leitura);
    }

    // Patch
    @Transactional
    public LeituraResponse atualizarProgresso(
            int usuarioId,
            int livroId,
            LeituraAtualizacaoRequest dados) {

        LeituraId id = new LeituraId(usuarioId, livroId);

        Leitura leitura = leituraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Leitura não encontrada"
                ));

        Integer paginaAtual = validarPagina(
                dados.paginaAtual(),
                leitura.getLivro()
        );

        leitura.atualizarProgresso(
                paginaAtual,
                LocalDateTime.now()
        );

        Leitura leituraSalva = leituraRepository.save(leitura);

        return criarResponse(leituraSalva);
    }

    // DEL
    @Transactional
    public void removerLeitura(int usuarioId, int livroId) {
        LeituraId id = new LeituraId(usuarioId, livroId);

        Leitura leitura = leituraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Leitura não encontrada"
                ));

        leituraRepository.delete(leitura);
    }

    private Integer validarPagina(
            Integer paginaAtual,
            Livro livro) {

        if (paginaAtual == null || paginaAtual < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A página atual deve ser zero ou maior"
            );
        }

        if (paginaAtual > livro.getTotalPaginas()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A página atual não pode ser maior que o total de páginas"
            );
        }

        return paginaAtual;
    }

    private void validarId(Integer id, String mensagem) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    mensagem
            );
        }
    }

    private LeituraResponse criarResponse(Leitura leitura) {
        return new LeituraResponse(
                leitura.getUsuario().getId(),
                leitura.getLivro().getId(),
                leitura.getLivro().getTitulo(),
                leitura.getPaginaAtual(),
                leitura.getLivro().getTotalPaginas(),
                leitura.getUltimoAcesso()
        );
    }
}