package com.BearShelf.projeto.controller;

import com.BearShelf.projeto.dto.LivroCadastroRequest;
import com.BearShelf.projeto.dto.LivroResponse;
import com.BearShelf.projeto.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public List<LivroResponse> listarLivros() {
        return service.listarLivros();
    }

    @GetMapping("/{id}")
    public LivroResponse buscarLivro(@PathVariable int id) {
        return service.buscarLivro(id);
    }
}
