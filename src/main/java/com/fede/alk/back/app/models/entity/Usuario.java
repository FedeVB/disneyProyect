package com.fede.alk.back.app.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_autoridades", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "id_autoridad", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario", "id_autoridad"})})
    private List<Autoridad> autoridades;

    {
        this.autoridades=new ArrayList<>();
    }
    @PrePersist
    public void prePersist() {
        this.enabled = true;
    }
}
