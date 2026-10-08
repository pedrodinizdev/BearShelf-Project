package com.BearShelf.projeto.controllers;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/categoria")
@RestController
public class CategoriaController {

    @GetMapping()
    public String inforCategoria(){
        return "Ola";
    }

    @PostMapping()
    public void adicionarCategoria(){

    }

    @DeleteMapping()
    public void deletarCategoria(){

    }

    @PutMapping()
    public void mudarTodasAsInformacoes(){

    }

    @PatchMapping
    public void mudarAlgumaInfo(){

    }


}
