package com.BearShelf.projeto.controller;

import com.BearShelf.projeto.dto.UsuarioAtualizacaoRequest;
import org.springframework.web.bind.annotation.*;
import com.BearShelf.projeto.dto.UsuarioCadastroRequest;
import com.BearShelf.projeto.dto.UsuarioResponse;
import com.BearShelf.projeto.service.UsuarioService;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequestMapping("/usuario")
@RestController
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioResponse> listarUsuarios() {
        return service.listarUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarUsuario(@PathVariable int id) {
        return service.buscarUsuario(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse adicionarUsuario(
            @RequestBody UsuarioCadastroRequest dados) {

        return service.cadastrarUsuario(dados);
    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizarUsuario(
            @PathVariable int id,
            @RequestBody UsuarioAtualizacaoRequest dados) {

        return service.atualizarUsuario(id, dados);
    }

    @PatchMapping("/{id}")
    public UsuarioResponse atualizarUsuarioParcialmente(
            @PathVariable int id,
            @RequestBody UsuarioAtualizacaoRequest dados) {

        return service.atualizarUsuarioParcialmente(id, dados);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario(@PathVariable int id) {
        service.deletarUsuario(id);
    }
}
