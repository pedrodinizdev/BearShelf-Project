package com.BearShelf.projeto.service;

import com.BearShelf.projeto.dto.UsuarioCadastroRequest;
import com.BearShelf.projeto.dto.UsuarioResponse;
import com.BearShelf.projeto.entity.Usuario;
import com.BearShelf.projeto.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioResponse> listarUsuarios() {
        List<Usuario> usuarios = repository.findAll();
        List<UsuarioResponse> respostas = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            respostas.add(criarResponse(usuario));
        }

        return respostas;
    }

    public UsuarioResponse buscarUsuario(int id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        return criarResponse(usuario);
    }

    private UsuarioResponse criarResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCadastro()
        );
    }

    @Transactional
    public UsuarioResponse cadastrarUsuario(UsuarioCadastroRequest dados) {
        String nome = validarNome(dados.nome());
        String email = validarEmail(dados.email());

        if (repository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "E-mail já cadastrado"
            );
        }

        Usuario usuario = new Usuario(
                nome,
                email,
                LocalDate.now()
        );

        Usuario usuarioSalvo = repository.save(usuario);

        return new UsuarioResponse(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getDataCadastro()
        );
    }

    private String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome do usuário é obrigatório"
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

    private String validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O e-mail é obrigatório"
            );
        }

        String emailTratado = email.strip().toLowerCase();

        if (emailTratado.length() > 200 || !emailTratado.contains("@")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail inválido"
            );
        }

        return emailTratado;
    }
}