package com.fede.alk.back.app.service.interfaces;

import com.fede.alk.back.app.models.entity.Personaje;

import java.util.List;
import java.util.Optional;

public interface PersonajeService {

    List<Personaje> findAll();
    List<Personaje> findAllPeliculasId(Integer id);
    Optional<Personaje> findById(Integer id);
    Optional<Personaje> findByNombre(String nombre);
    List<Personaje> findAllByAge(Integer age);
    Personaje save(Personaje personaje);
    void deleteById(Integer id);

    void deleteByNombre(String nombre);
}
