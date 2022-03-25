package com.fede.alk.back.app.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private String urlFoto;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "genero")
    @JsonIgnoreProperties(value = "genero")
    private List<Pelicula> peliculas;
}
