package com.fede.alk.back.app.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PeliculaDto {
    private byte[] imagen;
    private String titulo;
    private LocalDate fechaCreacion;

    public PeliculaDto(byte[] imagen, String titulo, LocalDate fechaCreacion) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
    }
}
