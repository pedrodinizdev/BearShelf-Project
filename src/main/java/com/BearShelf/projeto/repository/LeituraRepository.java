package com.BearShelf.projeto.repository;

import com.BearShelf.projeto.entity.Leitura;
import com.BearShelf.projeto.entity.LeituraId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeituraRepository
        extends JpaRepository<Leitura, LeituraId> {

    List<Leitura> findAllByUsuario_Id(int usuarioId);
}