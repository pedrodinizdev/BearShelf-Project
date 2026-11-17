package com.BearShelf.projeto.controller;

import org.springframework.web.bind.annotation.*;
import com.BearShelf.projeto.entity.Categoria;
import com.BearShelf.projeto.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Categoria inforCategoria(@PathVariable int id) {

        // CategoriaService service = new CategoriaService();

        return service.buscarCategoria(id);
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
