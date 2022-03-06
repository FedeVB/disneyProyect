package com.fede.alk.back.app.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "personajes")
public class Personaje implements Serializable {

    private static final long serialVersionUID = -2672613032276353467L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String nombre;
    @NotNull
    private Integer edad;
    @NotNull
    private Double peso;
    @NotBlank
    private String historia;
    @Lob
    private byte[] imagen;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "personajes_peliculas", joinColumns = @JoinColumn(name = "id_personaje",referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "id_pelicula",referencedColumnName = "id")
            , uniqueConstraints = {@UniqueConstraint(columnNames = {"id_personaje", "id_pelicula"})})
    private List<Pelicula> peliculas;

}
