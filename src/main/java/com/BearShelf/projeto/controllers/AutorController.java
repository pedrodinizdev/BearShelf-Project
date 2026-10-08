package com.BearShelf.projeto.controllers;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/autor")
@RestController
public class AutorController {

    @GetMapping("{id}")
    public String infoAutor(){
        return "0";
    }

    @PostMapping()
    public void adicionarAutor(){

    }

    @DeleteMapping
    public void deletarAutor(){

    }

    @PutMapping()
    public void mudarTodasAsInformacoes(){

    }

    @PatchMapping
    public void mudarInfoEspecifica(){

    }


}
