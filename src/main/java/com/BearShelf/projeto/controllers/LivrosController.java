package com.BearShelf.projeto.controllers;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livro")
public class LivrosController {

    /*
    Criando rotas para receber requisições da biblioteca
     */

    @GetMapping("categoria/{id}")
    public String informacaoLivro(){
        return "teste";
    }

    @PostMapping()
    public void adicionarLivro(){

    }

    @DeleteMapping("{id}")
    public void deletarLivro(){

    }

    @PatchMapping()
    public void modificarTodasAsInformacoes(){

    }

    @PutMapping
    public void modificarInfoEspecificas(){

    }

}
