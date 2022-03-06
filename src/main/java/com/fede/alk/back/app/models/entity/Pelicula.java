package com.fede.alk.back.app.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "peliculas")
public class Pelicula implements Serializable {

    private static final long serialVersionUID = -8294700509550064211L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    @Lob
    private byte[] imagen;
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;
    private Integer calificacion;

    @ManyToMany(cascade = CascadeType.MERGE,mappedBy = "peliculas")
    private List<Personaje> personajes;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_genero",referencedColumnName = "id")
    private Genero genero;

}
