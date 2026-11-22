package com.BearShelf.projeto.repository;

import com.BearShelf.projeto.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
