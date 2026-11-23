package com.BearShelf.projeto.service;

import com.BearShelf.projeto.dto.*;
import com.BearShelf.projeto.entity.Autor;
import com.BearShelf.projeto.entity.Categoria;
import com.BearShelf.projeto.entity.Livro;
import com.BearShelf.projeto.repository.AutorRepository;
import com.BearShelf.projeto.repository.CategoriaRepository;
import com.BearShelf.projeto.repository.LivroRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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

    // Post. Valida os dados, relaciona autores e categorias e salva o livro
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

    // Validações, tratam título, páginas e descrição.
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

    //Busca e valida os autores informados.
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

    // Busca e valida as categorias informadas.
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

    //Impede conjuntos de IDs vazios ou com valor nulo
    private void validarIdsObrigatorios(Set<Integer> ids, String mensagem) {
        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensagem);
        }

        for (Integer id : ids) {
            if (id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensagem);
            }
        }
    }

    // Converte a entidade Livro no JSON devolvido pela API.
    private LivroResponse criarResponse(Livro livro) {
        Set<AutorResumoResponse> autores = new HashSet<>();

        for (Autor autor : livro.getAutores()) {
            AutorResumoResponse resumo = new AutorResumoResponse(
                    autor.getId(),
                    autor.getNome()
            );

            autores.add(resumo);
        }

        Set<CategoriaResumoResponse> categorias = new HashSet<>();

        for (Categoria categoria : livro.getCategorias()) {
            CategoriaResumoResponse resumo = new CategoriaResumoResponse(
                    categoria.getId(),
                    categoria.getNome()
            );

            categorias.add(resumo);
        }

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

    // Get. Lista os livros e busca um livro pelo ID.
    @Transactional(readOnly = true)
    // garante que o jpa vai carregar por conta do carregamento lazy
    public List<LivroResponse> listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        List<LivroResponse> respostas = new ArrayList<>();

        for (Livro livro : livros) {
            LivroResponse resposta = criarResponse(livro);
            respostas.add(resposta);
        }

        return respostas;
    }
    @Transactional(readOnly = true)
    public LivroResponse buscarLivro(int id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Livro não encontrado"
                ));

        return criarResponse(livro);
    }

    //patch

    @Transactional
    public LivroResponse atualizarLivro(int id, LivroAtualizacaoRequest dados){
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        String titulo = validarTitulo(dados.titulo());
        Integer totalPaginas = validarTotalPaginas(dados.totalPaginas());
        String descricao = tratarDescricao(dados.descricao());
        Set<Autor> autores = buscarAutores(dados.autorIds());
        Set<Categoria> categorias = buscarCategorias(dados.categoriaIds());

        livro.atualizarDados(titulo, dados.anoPublicacao(), descricao, totalPaginas, autores, categorias);

        Livro livroSalvo = livroRepository.save(livro);

        return criarResponse(livroSalvo);
    }

    public void deletarLivro(int id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "Livro não encontrado"));

        try {
            livroRepository.delete(livro);
            //flush obriga o delete a ser executado pelo jpa na hr, se der erro para na hr ao inves de dps do fim do transactional
            livroRepository.flush();
        } catch (DataIntegrityViolationException erro) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Não foi possível excluir o livro porque ele está em uso", erro
            );
        }
    }


}
