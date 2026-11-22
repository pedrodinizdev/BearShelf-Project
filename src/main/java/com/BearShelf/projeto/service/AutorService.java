package com.BearShelf.projeto.service;

import com.BearShelf.projeto.entity.Autor;
import com.BearShelf.projeto.repository.AutorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public List<Autor> listarAutores() {
        return repository.findAll();
    }

    public Autor buscarAutor(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Autor não encontrado"
                ));
    }

    public Autor cadastrarAutor(String nome, String nacionalidade) {
        String nomeTratado = validarNome(nome);
        String nacionalidadeTratada = tratarNacionalidade(nacionalidade);

        Autor autor = new Autor(nomeTratado, nacionalidadeTratada);
        return repository.save(autor);
    }

    public Autor atualizarAutor(int id, String nome, String nacionalidade) {
        Autor autor = buscarAutor(id);
        String nomeTratado = validarNome(nome);
        String nacionalidadeTratada = tratarNacionalidade(nacionalidade);

        autor.atualizarDados(nomeTratado, nacionalidadeTratada);
        return repository.save(autor);
    }

    public void deletarAutor(int id) {
        Autor autor = buscarAutor(id);

        try {
            repository.delete(autor);
        } catch (DataIntegrityViolationException erro) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não foi possível excluir o autor por uma restrição do banco",
                    erro
            );
        }
    }

    private String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome do autor é obrigatório"
            );
        }

        String nomeTratado = nome.strip();
        if (nomeTratado.length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome deve ter no máximo 100 caracteres"
            );
        }

        return nomeTratado;
    }

    private String tratarNacionalidade(String nacionalidade) {
        if (nacionalidade == null || nacionalidade.isBlank()) {
            return null;
        }

        String nacionalidadeTratada = nacionalidade.strip();
        if (nacionalidadeTratada.length() > 60) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A nacionalidade deve ter no máximo 60 caracteres"
            );
        }

        return nacionalidadeTratada;
    }
}
