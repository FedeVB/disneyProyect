package com.fede.alk.back.app.repositoryTest;

import com.fede.alk.back.app.models.entity.Personaje;
import com.fede.alk.back.app.models.repository.PersonajeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class PersonajeRepositoryTest {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Test
    void testFindAllByPeliculasId() {
        List<Personaje> personajes1 = personajeRepository.findAllByPeliculasId(1);
        List<Personaje> personajes2 = personajeRepository.findAllByPeliculasId(2);
        List<Personaje> personajes3 = personajeRepository.findAllByPeliculasId(3);

        Assertions.assertEquals(2, personajes1.size());
        Assertions.assertEquals(1, personajes2.size());
        Assertions.assertTrue(personajes3.isEmpty());
    }

    @Test
    void testFindByNombre() {
        Personaje personaje1 = personajeRepository.findByNombre("Susan").orElse(null);
        Personaje personaje2 = personajeRepository.findByNombre("Sara").orElse(null);
        Personaje personaje3 = personajeRepository.findByNombre("Martina").orElse(null);

        Assertions.assertEquals("Susan", personaje1.getNombre());
        Assertions.assertEquals(20,personaje1.getEdad());
        Assertions.assertEquals("Sara", personaje2.getNombre());
        Assertions.assertEquals(21,personaje2.getEdad());
        Assertions.assertThrows(NullPointerException.class, () -> {
            personaje3.getNombre();
        });
    }

    @Test
    void testFindByEdad() {
        Personaje personaje1 = personajeRepository.findByEdad(20).orElse(null);
        Personaje personaje2 = personajeRepository.findByEdad(22).orElse(null);
        Personaje personaje3 = personajeRepository.findByEdad(30).orElse(null);

        Assertions.assertEquals("Susan", personaje1.getNombre());
        Assertions.assertEquals(20,personaje1.getEdad());
        Assertions.assertEquals("Susana", personaje2.getNombre());
        Assertions.assertEquals(22,personaje2.getEdad());
        Assertions.assertThrows(NullPointerException.class, () -> {
            personaje3.getNombre();
        });
    }

    @Test
    void testDeleteByNombre(){
        List<Personaje> personajes=personajeRepository.findAll();
        Assertions.assertEquals(3,personajes.size());

        personajeRepository.deleteByNombre("Susan");
        personajes=personajeRepository.findAll();
        Assertions.assertEquals(2,personajes.size());

        personajeRepository.deleteByNombre("Martina");
        personajes=personajeRepository.findAll();
        Assertions.assertEquals(2,personajes.size());
    }
}
