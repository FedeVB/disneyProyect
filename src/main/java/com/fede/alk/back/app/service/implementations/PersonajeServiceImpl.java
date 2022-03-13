package com.fede.alk.back.app.service.implementations;

import com.fede.alk.back.app.models.entity.Personaje;
import com.fede.alk.back.app.models.repository.PersonajeRepository;
import com.fede.alk.back.app.service.interfaces.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Personaje> findAll() {
        return personajeRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Personaje> findAllPeliculasId(Integer id) {
        return personajeRepository.findAllByPeliculasId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Personaje> findById(Integer id) {
        return personajeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Personaje> findByNombre(String nombre) {
        return personajeRepository.findByNombre(nombre);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Personaje> findByAge(Integer edad) {
        return personajeRepository.findByEdad(edad);
    }

    @Transactional
    @Override
    public Personaje save(Personaje personaje) {
        return personajeRepository.save(personaje);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        personajeRepository.deleteById(id);
    }
}
