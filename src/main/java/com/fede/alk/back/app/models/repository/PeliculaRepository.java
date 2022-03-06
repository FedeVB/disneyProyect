package com.fede.alk.back.app.models.repository;

import com.fede.alk.back.app.models.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PeliculaRepository extends JpaRepository<Pelicula,Integer> {

    Optional<Pelicula> findByTitulo(String titulo);
    List<Pelicula> findAllByGeneroId(Integer id);
    List<Pelicula> findAllByOrderByTituloAsc();
    List<Pelicula> findAllByOrderByTituloDesc();
}
