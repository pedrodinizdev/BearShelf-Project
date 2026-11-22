package com.BearShelf.projeto.controller;

import com.BearShelf.projeto.dto.AutorAtualizacaoRequest;
import com.BearShelf.projeto.dto.AutorCadastroRequest;
import com.BearShelf.projeto.entity.Autor;
import com.BearShelf.projeto.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Autor> listarAutores() {
        return service.listarAutores();
    }

    @GetMapping("/{id}")
    public Autor buscarAutor(@PathVariable("id") int id) {

        return service.buscarAutor(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autor adicionarAutor(@RequestBody AutorCadastroRequest dados) {

        return service.cadastrarAutor(dados.nome(), dados.nacionalidade());
    }

    @PutMapping("/{id}")
    public Autor atualizarAutor(
            @PathVariable("id") int id,
            @RequestBody AutorAtualizacaoRequest dados) {

        return service.atualizarAutor(id, dados.nome(), dados.nacionalidade());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAutor(@PathVariable("id") int id) {

        service.deletarAutor(id);
    }
}
