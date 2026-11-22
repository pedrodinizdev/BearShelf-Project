package com.BearShelf.projeto.controller;

import com.BearShelf.projeto.dto.LivroCadastroRequest;
import com.BearShelf.projeto.dto.LivroResponse;
import com.BearShelf.projeto.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livro")
public class LivrosController {

    private final LivroService service;

    public LivrosController(LivroService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroResponse adicionarLivro(@RequestBody LivroCadastroRequest dados) {
        return service.cadastrarLivro(dados);
    }
}
