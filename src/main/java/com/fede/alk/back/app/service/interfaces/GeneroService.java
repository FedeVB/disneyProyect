package com.fede.alk.back.app.service.interfaces;

import com.fede.alk.back.app.models.entity.Genero;

import java.util.List;
import java.util.Optional;

public interface GeneroService {

    List<Genero> findAll();
    Optional<Genero> findById(Integer id);
    void save(Genero genero);
}
