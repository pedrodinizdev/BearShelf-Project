package com.BearShelf.projeto.repository;

import com.BearShelf.projeto.entity.Biblioteca;
import com.BearShelf.projeto.entity.BibliotecaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BibliotecaRepository
    //id é o par representado por Bibliotecaid
        extends JpaRepository<Biblioteca, BibliotecaId> {

    // busca todas linhas de biblioteca que tiver o id.usuario dado
    List<Biblioteca> findAllByUsuario_Id(int usuarioId);
}