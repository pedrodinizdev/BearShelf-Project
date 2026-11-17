package com.BearShelf.projeto.repository;

import com.BearShelf.projeto.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository
    extends JpaRepository<Categoria, Integer> {

}

