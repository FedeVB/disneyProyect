package com.fede.alk.back.app.service.implementations;

import com.fede.alk.back.app.models.entity.Genero;
import com.fede.alk.back.app.models.entity.Personaje;
import com.fede.alk.back.app.models.repository.GeneroRepository;
import com.fede.alk.back.app.service.interfaces.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    @Override
    public Optional<Genero> findById(Integer id) {
        return generoRepository.findById(id);
    }

    @Override
    public void save(Genero genero) {
        generoRepository.save(genero);
    }
}
