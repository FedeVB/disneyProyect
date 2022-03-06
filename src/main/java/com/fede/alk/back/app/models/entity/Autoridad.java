package com.fede.alk.back.app.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "autoridades")
@NoArgsConstructor
public class Autoridad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String autority;

    @ManyToMany(mappedBy = "autoridades")
    private List<Usuario> usuarios;

    public Autoridad(Integer id, String autority) {
        this.id = id;
        this.autority = autority;
    }
}
