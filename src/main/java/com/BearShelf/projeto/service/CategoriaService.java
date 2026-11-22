package com.BearShelf.projeto.service;

import com.BearShelf.projeto.entity.Categoria;
import com.BearShelf.projeto.repository.CategoriaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaService {
    // get
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Categoria buscarCategoria(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria não encontrada"));
    }

    public List<Categoria> listarCategorias() {
        return repository.findAll();
    }

    // post
    public Categoria cadastrarCategoria(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O nome da categoria é obrigatório"
            );
        }

        String nomeTratado = nome.strip();

        if (nomeTratado.length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome deve ter no máximo 255 caracteres");
        }

        Categoria categoria = new Categoria(nomeTratado);

        return repository.save(categoria);
    }
    public Categoria atualizarCategoria(int id, String nome) {
        Categoria categoria = buscarCategoria(id);

        if (nome == null || nome.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da categoria é obrigatório"
            );
        }

        String nomeTratado = nome.strip();

        if (nomeTratado.length() > 255) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome deve ter no máximo 255 caracteres"
            );
        }

        categoria.atualizarNome(nomeTratado);

        return repository.save(categoria);
    }

    public void deletarCategoria(int id) {
        Categoria categoria = buscarCategoria(id);

        try {
            repository.delete(categoria);
        } catch (DataIntegrityViolationException erro) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Não foi possível excluir a categoria por restrição do banco.", erro);
        }
    }
}
