package com.fede.alk.back.app.service.implementations;

import com.fede.alk.back.app.models.entity.Foto;
import com.fede.alk.back.app.models.repository.FotoRepository;
import com.fede.alk.back.app.service.interfaces.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FotoServiceImpl implements FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Foto> findById(Integer id) {
        return fotoRepository.findById(id);
    }

    @Transactional
    @Override
    public Foto save(Foto foto) {
        return fotoRepository.save(foto);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        fotoRepository.deleteById(id);
    }
}
