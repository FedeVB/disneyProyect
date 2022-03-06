package com.fede.alk.back.app.service.interfaces;

import com.fede.alk.back.app.models.entity.Pelicula;

import java.util.List;
import java.util.Optional;

public interface PeliculaService {

    List<Pelicula> findAll();

    List<Pelicula> findAllByGeneroId(Integer id);

    List<Pelicula> findAllByOrderByTituloAsc();

    List<Pelicula> findAllByOrderByTituloDesc();

    Optional<Pelicula> findByNombre(String nombre);

    Optional<Pelicula> findById(Integer id);

    Pelicula save(Pelicula pelicula);

    void deleteById(Integer id);
}
