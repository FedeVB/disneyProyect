package com.fede.alk.back.app.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank
    private String titulo;
    @Column(name = "fecha_creacion")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaCreacion;
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer calificacion;

    private String urlFoto;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "peliculas", fetch = FetchType.EAGER)
    private List<Personaje> personajes;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_genero", referencedColumnName = "id")
    @JsonIgnoreProperties(value = "peliculas")
    private Genero genero;

}
