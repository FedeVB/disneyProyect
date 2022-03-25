package com.fede.alk.back.app.service.interfaces;

import com.fede.alk.back.app.models.entity.Foto;

import java.util.Optional;

public interface FotoService {

    Optional<Foto> findById(Integer id);

    Foto save(Foto foto);

    void deleteById(Integer id);
}
