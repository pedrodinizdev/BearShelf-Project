package com.BearShelf.projeto.controller;

import com.BearShelf.projeto.dto.BibliotecaCadastroRequest;
import com.BearShelf.projeto.dto.BibliotecaResponse;
import com.BearShelf.projeto.service.BibliotecaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    private final BibliotecaService service;

    public BibliotecaController(BibliotecaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BibliotecaResponse adicionarLivro(
            @RequestBody BibliotecaCadastroRequest dados) {

        return service.adicionarLivro(dados);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<BibliotecaResponse> listarBiblioteca(
            @PathVariable int usuarioId) {

        return service.listarBiblioteca(usuarioId);
    }

    @DeleteMapping("/usuario/{usuarioId}/livro/{livroId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerLivro(
            @PathVariable int usuarioId,
            @PathVariable int livroId) {

        service.removerLivro(usuarioId, livroId);
    }
}