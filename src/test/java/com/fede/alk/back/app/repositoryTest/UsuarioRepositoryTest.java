package com.fede.alk.back.app.repositoryTest;

import com.fede.alk.back.app.models.entity.Usuario;
import com.fede.alk.back.app.models.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testFindByUsername() {
        Usuario usuario1 = usuarioRepository.findByUsername("Susan").orElse(null);
        Usuario usuario2 = usuarioRepository.findByUsername("Fede").orElse(null);
        Usuario usuario3 = usuarioRepository.findByUsername("Ramiro").orElse(null);

        Assertions.assertEquals("Susan", usuario1.getUsername());
        Assertions.assertEquals("Fede", usuario2.getUsername());
        Assertions.assertThrows(NullPointerException.class, () -> {
            usuario3.getUsername();
        });

        Assertions.assertEquals(2,usuario1.getAutoridades().size());
        Assertions.assertEquals(1,usuario2.getAutoridades().size());
    }

    @Test
    void testExistsByUsername(){
        boolean exist1=usuarioRepository.existsByUsername("Susan");
        boolean exist2=usuarioRepository.existsByUsername("Fede");
        boolean exist3=usuarioRepository.existsByUsername("Micaela");

        Assertions.assertTrue(exist1);
        Assertions.assertTrue(exist2);
        Assertions.assertFalse(exist3);
    }

    @Test
    void testExistsByEmail(){
        boolean exist1=usuarioRepository.existsByEmail("susan@gmail.com");
        boolean exist2=usuarioRepository.existsByEmail("fede@gmail.com");
        boolean exist3=usuarioRepository.existsByEmail("micaela@gmail.com");

        Assertions.assertTrue(exist1);
        Assertions.assertTrue(exist2);
        Assertions.assertFalse(exist3);
    }
}
