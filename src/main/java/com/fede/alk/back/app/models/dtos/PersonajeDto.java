package com.fede.alk.back.app.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonajeDto {
    private byte[] imagen;
    private String nombre;
}
