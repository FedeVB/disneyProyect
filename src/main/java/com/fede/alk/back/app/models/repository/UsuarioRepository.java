package com.fede.alk.back.app.models.repository;

import com.fede.alk.back.app.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Optional<Usuario> findByUsername(String username);
}
