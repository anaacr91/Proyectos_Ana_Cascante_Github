package com.movies.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    // Las lineas comentadas de abajo dan error, por que supuestamente ya estan definidas,
    // pero las dejo ahi por si acaso

    //public Categoria(Long id, String nombre, String descripcion ) {
    //    this.id = id;
    //    this.nombre = nombre;
    //    this.descripcion = descripcion;
    // }

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Movie> movies = new HashSet<>();

}