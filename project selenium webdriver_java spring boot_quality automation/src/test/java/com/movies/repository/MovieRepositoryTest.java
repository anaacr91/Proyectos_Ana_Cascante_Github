package com.movies.repository;

import com.movies.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    private Customer customer1;
    private Customer customer2;
    private Categoria categoria;
    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    public void setUp() {
       categoria = new Categoria();
        categoria.setNombre("Action");
        categoria.setDescripcion("Accion y Aventura");
        categoria = categoriaRepository.save(categoria);
        customer1 = Customer.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan@example.com")
                .build();
        customer2 = Customer.builder()
                .nombre("Ana")
                .apellido("López")
                .email("ana@example.com")
                .build();
        customerRepository.saveAll(List.of(customer1, customer2));

        movie1 = Movie.builder()
                .name("Movie 1")
                .duration(120)
                .year(2020)
                .categoria(categoria)
                .customers(Collections.singleton(customer2))
                .available(true)
                .rentalPricePerDay(5.99)
                .build();

        movie2 = Movie.builder()
                .name("Movie 2")
                .duration(90)
                .year(1999)
                .categoria(categoria)
                .customers(Collections.singleton(customer2))
                .available(false)
                .rentalPricePerDay(3.99)
                .build();
        movieRepository.saveAll(List.of(movie1, movie2));

    }
    @Test
    @DisplayName("Test find by customer id")
    void testFindByCustomerId() {
        // Buscar películas alquiladas por customer1
        List<Movie> moviesForCustomer1 = movieRepository.findByCustomerId(customer1.getId());
        List<Movie> moviesForCustomer2 = movieRepository.findByCustomerId(customer2.getId());
        assertNotNull(moviesForCustomer1, "La lista de películas no debería ser nula.");
        assertEquals(0, moviesForCustomer1.size(), "Customer 1 debería tener 0 películas asociadas.");
        assertNotNull(moviesForCustomer2, "La lista de películas no debería ser nula.");

    }

    @Test
    @DisplayName("Test find all peliculas disponibles")
    public void testFindAllAvailableMovies() {
        List<Movie> result = movieRepository.findAllAvailableMovies();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Movie 1");
    }

    @Test
    @DisplayName("Test find by categoria id")
    public void testFindByCategoriaId() {
        List<Movie> result = movieRepository.findByCategoriaId(categoria.getId());
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Test find por pelicula por rango de años")
    public void testFindMoviesByYearRange() {
        List<Movie> result = movieRepository.findMoviesByYearRange(1990, 2000);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Movie 2");
    }

    @Test
    @DisplayName("Test search peliculas por nombre")
    public void testSearchMoviesByName() {
        List<Movie> result = movieRepository.searchMoviesByName("Movie");
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Test find peliculas por precio de alquiler")
    public void testFindMoviesByRentalPriceLessThan() {
        List<Movie> result = movieRepository.findMoviesByRentalPriceLessThan(4.00);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Movie 2");
    }

}