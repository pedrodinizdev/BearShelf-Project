package com.BearShelf.projeto.repository;

import com.BearShelf.projeto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmailIgnoreCase(String email);
}