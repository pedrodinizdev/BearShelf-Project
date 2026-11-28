package com.BearShelf.projeto.repository;

import com.BearShelf.projeto.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Integer> {
}
