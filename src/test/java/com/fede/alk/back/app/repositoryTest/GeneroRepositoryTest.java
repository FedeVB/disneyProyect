package com.fede.alk.back.app.repositoryTest;

import com.fede.alk.back.app.models.repository.GeneroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class GeneroRepositoryTest {

    @Autowired
    GeneroRepository generoRepository;
}

