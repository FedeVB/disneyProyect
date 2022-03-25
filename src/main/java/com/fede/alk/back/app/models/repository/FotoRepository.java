package com.fede.alk.back.app.models.repository;

import com.fede.alk.back.app.models.entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Foto,Integer> {
}
