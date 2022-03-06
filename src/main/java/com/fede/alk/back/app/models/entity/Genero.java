package com.fede.alk.back.app.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "generos")
public class Genero implements Serializable {

    private static final long serialVersionUID = -2960310459648852847L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    @Lob
    private byte[] imagen;

    @OneToMany(cascade = CascadeType.MERGE,mappedBy = "genero")
    private List<Pelicula> peliculas;
}
