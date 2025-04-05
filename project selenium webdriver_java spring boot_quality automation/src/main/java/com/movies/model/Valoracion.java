package com.movies.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Data
@Table(name = "valoracion")
public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String comentario;
    private Integer puntuacion;

    public Valoracion(Customer customer, Movie movie, String comentario, Integer puntuacion) {
        this.customer = customer;
        this.movie = movie;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
    }
}
