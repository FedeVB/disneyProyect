package com.fede.alk.back.app.service.interfaces;

import com.fede.alk.back.app.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Integer id);

    Optional<Usuario> findByUsername(String username);

    Usuario save(Usuario usuario);

    void deleteById(Integer id);
}
