package com.BearShelf.projeto.controllers;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/usuario")
@RestController
public class UsuarioController {

    @GetMapping()
    public String usuarioInfo(){
        return "teste";
    }

    @PostMapping()
    public void adicionarUsuario(){

    }

    @DeleteMapping()
    public void deletarUsuario(){

    }

    @PutMapping()
    public void mudarInfoGerais(){

    }

    @PatchMapping()
    public void mudarEspecifico(){

    }
}
