package com.BearShelf.projeto.controller;

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

    @DeleteMapping()
    public void deletarUsuario(){

    }

    @PutMapping()
    public void atualizarUsuario(){

    }

    @PatchMapping()
    public void atualizarUsuarioParcialmente(){

    }
}
