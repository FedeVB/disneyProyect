package com.fede.alk.back.app.models.repository;

import com.fede.alk.back.app.models.entity.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonajeRepository extends JpaRepository<Personaje,Integer> {

    List<Personaje> findAllByPeliculasId(Integer id);
    Optional<Personaje> findByNombre(String nombre);
    Optional<Personaje> findByEdad(Integer edad);
}
