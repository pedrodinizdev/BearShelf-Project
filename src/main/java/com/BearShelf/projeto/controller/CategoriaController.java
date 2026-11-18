package com.BearShelf.projeto.controller;

import com.BearShelf.projeto.dto.CategoriaAtualizacaoRequest;
import com.BearShelf.projeto.dto.CategoriaCadastroRequest;
import com.BearShelf.projeto.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.BearShelf.projeto.entity.Categoria;
import com.BearShelf.projeto.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Categoria> listarCategorias() {
        return service.listarCategorias();
    }

    @GetMapping("/{id}")
    public Categoria inforCategoria(@PathVariable int id) {

        // CategoriaService service = new CategoriaService();

        return service.buscarCategoria(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria adicionarCategoria(@RequestBody CategoriaCadastroRequest dados){
        return service.cadastrarCategoria(dados.nome());
    }

    @DeleteMapping()
    public void deletarCategoria(){

    }

    @PutMapping("/{id}")
    public Categoria atualizarCategoria(@PathVariable int id, @RequestBody CategoriaAtualizacaoRequest dados) {
        return service.atualizarCategoria(id, dados.nome());
    }

    @PatchMapping
    public void mudarAlgumaInfo(){

    }


}
