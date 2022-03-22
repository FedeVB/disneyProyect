package com.fede.alk.back.app.repositoryTest;

import com.fede.alk.back.app.models.entity.Pelicula;
import com.fede.alk.back.app.models.repository.PeliculaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

@DataJpaTest
public class PeliculaRepositoryTest {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Test
    void testFindAllByTitulo() {
        List<Pelicula> peliculas = peliculaRepository.findAllByTitulo("Star Wars 1");
        List<Pelicula> peliculas2 = peliculaRepository.findAllByTitulo("Star Wars 5");

        Assertions.assertEquals(1, peliculas.size());
        Assertions.assertTrue(peliculas2.isEmpty());
    }

    @Test
    void testFindAllByGeneroId() {
        List<Pelicula> peliculas = peliculaRepository.findAllByGeneroId(1);
        List<Pelicula> peliculas2 = peliculaRepository.findAllByGeneroId(5);
        Assertions.assertEquals(3, peliculas.size());
        Assertions.assertTrue(peliculas2.isEmpty());
    }

    @Test
    void findAllByOrderByTituloAsc() {
        List<Pelicula> peliculas = peliculaRepository.findAllByOrderByTituloAsc();
        Assertions.assertEquals("Star Wars 1", peliculas.get(0).getTitulo());

        Assertions.assertEquals("Star Wars 3",peliculas.get(2).getTitulo());
    }

    @Test
    void findAllByOrderByTituloDes() {
        List<Pelicula> peliculas = peliculaRepository.findAllByOrderByTituloDesc();
        Assertions.assertEquals("Star Wars 3", peliculas.get(0).getTitulo());

        Assertions.assertEquals("Star Wars 1",peliculas.get(2).getTitulo());
    }

    @Test
    void testFindByTitulo() {
        Pelicula pelicula = peliculaRepository.findByTitulo("Star Wars 1").orElse(null);
        Pelicula pelicula2=peliculaRepository.findByTitulo("Terminator").orElse(null);
        Assertions.assertEquals("Star Wars 1", pelicula.getTitulo());

        Assertions.assertNotEquals("Star Wars 2", pelicula.getTitulo());
        Assertions.assertThrows(NullPointerException.class, () -> {
            pelicula2.getTitulo();
        });
    }
}
