package com.fede.alk.back.app.service.implementations;

import com.fede.alk.back.app.models.entity.Genero;
import com.fede.alk.back.app.models.entity.Personaje;
import com.fede.alk.back.app.models.repository.GeneroRepository;
import com.fede.alk.back.app.service.interfaces.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genero> findById(Integer id) {
        return generoRepository.findById(id);
    }

    @Transactional
    @Override
    public Genero save(Genero genero) {
        return generoRepository.save(genero);
    }
}
