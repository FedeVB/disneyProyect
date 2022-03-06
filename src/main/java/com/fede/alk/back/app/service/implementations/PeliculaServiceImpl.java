package com.fede.alk.back.app.service.implementations;

import com.fede.alk.back.app.models.entity.Pelicula;
import com.fede.alk.back.app.models.repository.PeliculaRepository;
import com.fede.alk.back.app.service.interfaces.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Override
    public List<Pelicula> findAll() {
        return peliculaRepository.findAll();
    }

    @Override
    public Optional<Pelicula> findByNombre(String nombre) {
        return peliculaRepository.findByTitulo(nombre);
    }

    @Override
    public List<Pelicula> findAllByGeneroId(Integer id) {
        return peliculaRepository.findAllByGeneroId(id);
    }

    @Override
    public List<Pelicula> findAllByOrderByTituloAsc() {
        return peliculaRepository.findAllByOrderByTituloAsc();
    }

    @Override
    public List<Pelicula> findAllByOrderByTituloDesc() {
        return peliculaRepository.findAllByOrderByTituloDesc();
    }

    @Override
    public Optional<Pelicula> findById(Integer id) {
        return peliculaRepository.findById(id);
    }

    @Override
    public Pelicula save(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    @Override
    public void deleteById(Integer id) {
        peliculaRepository.deleteById(id);
    }
}
