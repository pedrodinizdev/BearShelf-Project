package com.BearShelf.projeto.service;

import com.BearShelf.projeto.dto.AutorResumoResponse;
import com.BearShelf.projeto.dto.CategoriaResumoResponse;
import com.BearShelf.projeto.dto.LivroCadastroRequest;
import com.BearShelf.projeto.dto.LivroResponse;
import com.BearShelf.projeto.entity.Autor;
import com.BearShelf.projeto.entity.Categoria;
import com.BearShelf.projeto.entity.Livro;
import com.BearShelf.projeto.repository.AutorRepository;
import com.BearShelf.projeto.repository.CategoriaRepository;
import com.BearShelf.projeto.repository.LivroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroService(
            LivroRepository livroRepository,
            AutorRepository autorRepository,
            CategoriaRepository categoriaRepository) {

        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public LivroResponse cadastrarLivro(LivroCadastroRequest dados) {
        String titulo = validarTitulo(dados.titulo());
        Integer totalPaginas = validarTotalPaginas(dados.totalPaginas());
        String descricao = tratarDescricao(dados.descricao());

        Set<Autor> autores = buscarAutores(dados.autorIds());
        Set<Categoria> categorias = buscarCategorias(dados.categoriaIds());

        Livro livro = new Livro(
                titulo,
                dados.anoPublicacao(),
                descricao,
                totalPaginas,
                autores,
                categorias
        );

        Livro livroSalvo = livroRepository.save(livro);
        return criarResponse(livroSalvo);
    }

    private String validarTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O título do livro é obrigatório"
            );
        }

        String tituloTratado = titulo.strip();
        if (tituloTratado.length() > 200) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O título deve ter no máximo 200 caracteres"
            );
        }

        return tituloTratado;
    }

    private Integer validarTotalPaginas(Integer totalPaginas) {
        if (totalPaginas == null || totalPaginas <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O total de páginas deve ser maior que zero"
            );
        }

        return totalPaginas;
    }

    private String tratarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            return null;
        }

        return descricao.strip();
    }

    private Set<Autor> buscarAutores(Set<Integer> autorIds) {
        validarIdsObrigatorios(autorIds, "O livro deve ter pelo menos um autor");

        List<Autor> autoresEncontrados = autorRepository.findAllById(autorIds);
        if (autoresEncontrados.size() != autorIds.size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Um ou mais autores não foram encontrados"
            );
        }

        return new HashSet<>(autoresEncontrados);
    }

    private Set<Categoria> buscarCategorias(Set<Integer> categoriaIds) {
        validarIdsObrigatorios(
                categoriaIds,
                "O livro deve ter pelo menos uma categoria"
        );

        List<Categoria> categoriasEncontradas = categoriaRepository.findAllById(categoriaIds);
        if (categoriasEncontradas.size() != categoriaIds.size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Uma ou mais categorias não foram encontradas"
            );
        }

        return new HashSet<>(categoriasEncontradas);
    }

    private void validarIdsObrigatorios(Set<Integer> ids, String mensagem) {
        if (ids == null || ids.isEmpty() || ids.stream().anyMatch(Objects::isNull)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensagem);
        }
    }

    private LivroResponse criarResponse(Livro livro) {
        Set<AutorResumoResponse> autores = livro.getAutores().stream()
                .map(autor -> new AutorResumoResponse(autor.getId(), autor.getNome()))
                .collect(Collectors.toSet());

        Set<CategoriaResumoResponse> categorias = livro.getCategorias().stream()
                .map(categoria -> new CategoriaResumoResponse(
                        categoria.getId(),
                        categoria.getNome()
                ))
                .collect(Collectors.toSet());

        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getAnoPublicacao(),
                livro.getDescricao(),
                livro.getTotalPaginas(),
                autores,
                categorias
        );
    }
}
