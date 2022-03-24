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
    public List<Personaje> findAllByAge(Integer edad) {
        return personajeRepository.findAllByEdad(edad);
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

    @Transactional
    @Override
    public void deleteByNombre(String nombre) {
        personajeRepository.deleteByNombre(nombre);
    }
}
