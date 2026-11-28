package com.BearShelf.projeto.controller;

import com.BearShelf.projeto.dto.LeituraAtualizacaoRequest;
import com.BearShelf.projeto.dto.LeituraCadastroRequest;
import com.BearShelf.projeto.dto.LeituraResponse;
import com.BearShelf.projeto.service.LeituraService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leitura")
public class LeituraController {

    private final LeituraService service;

    public LeituraController(LeituraService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeituraResponse iniciarLeitura(
            @RequestBody LeituraCadastroRequest dados) {

        return service.iniciarLeitura(dados);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<LeituraResponse> listarLeituras(
            @PathVariable int usuarioId) {

        return service.listarLeituras(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/livro/{livroId}")
    public LeituraResponse buscarLeitura(
            @PathVariable int usuarioId,
            @PathVariable int livroId) {

        return service.buscarLeitura(usuarioId, livroId);
    }

    @PatchMapping("/usuario/{usuarioId}/livro/{livroId}")
    public LeituraResponse atualizarProgresso(
            @PathVariable int usuarioId,
            @PathVariable int livroId,
            @RequestBody LeituraAtualizacaoRequest dados) {

        return service.atualizarProgresso(
                usuarioId,
                livroId,
                dados
        );
    }

    @DeleteMapping("/usuario/{usuarioId}/livro/{livroId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerLeitura(
            @PathVariable int usuarioId,
            @PathVariable int livroId) {

        service.removerLeitura(usuarioId, livroId);
    }
}