package com.BearShelf.projeto.controllers;

import org.springframework.web.bind.annotation.*;
import com.BearShelf.projeto.entity.Categoria;
import com.BearShelf.projeto.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @GetMapping()
    public Categoria inforCategoria() {

        CategoriaService service = new CategoriaService();

        return service.buscarCategoria();
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
