package com.fede.alk.back.app.service.implementations;

import com.fede.alk.back.app.models.entity.Pelicula;
import com.fede.alk.back.app.models.repository.PeliculaRepository;
import com.fede.alk.back.app.service.interfaces.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Pelicula> findAll() {
        return peliculaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pelicula> findAllByNombre(String nombre) {
        return peliculaRepository.findAllByTitulo(nombre);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pelicula> findAllByGeneroId(Integer id) {
        return peliculaRepository.findAllByGeneroId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pelicula> findAllByOrderByTituloAsc() {
        return peliculaRepository.findAllByOrderByTituloAsc();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pelicula> findAllByOrderByTituloDesc() {
        return peliculaRepository.findAllByOrderByTituloDesc();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Pelicula> findById(Integer id) {
        return peliculaRepository.findById(id);
    }

    @Override
    public Optional<Pelicula> findByTitulo(String titulo) {
        return peliculaRepository.findByTitulo(titulo);
    }

    @Transactional
    @Override
    public Pelicula save(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        peliculaRepository.deleteById(id);
    }
}
