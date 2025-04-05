package com.movies.repository;

import com.movies.model.Categoria;
import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Valoracion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ValoracionRepositoryTest {

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;
    private Movie movie;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setNombre("Gines");
        customer.setApellido("Sanchez");
        customer.setEmail("g@s.com");
        customer.setPassword("1234");
        customer = customerRepository.save(customer);
        categoria = new Categoria();
        categoria.setNombre("Action");
        categoria.setDescripcion("Action movies");
        categoria = categoriaRepository.save(categoria);

        movie = Movie.builder()
                .name("Test Movie")
                .duration(120)
                .year(2022)
                .available(true)
                .rentalPricePerDay(4.99)
                .categoria(categoria)
                .build();
        movie = movieRepository.save(movie);

        Valoracion valoracion1 = new Valoracion(customer, movie, "Great movie!", 5);
        Valoracion valoracion2 = new Valoracion(customer, movie, "Not bad", 3);

        valoracionRepository.save(valoracion1);
        valoracionRepository.save(valoracion2);
    }

    @Test
    @DisplayName("Test find by customer id")
    public void testFindByCustomerId() {
        List<Valoracion> result = valoracionRepository.findByCustomerId(customer.getId());
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Test find by movie id")
    public void testFindByMovieId() {
        List<Valoracion> result = valoracionRepository.findByMovieId(movie.getId());
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Test find media de puntuación por movie id")
    public void testFindAveragePuntuacionByMovieId() {
        Double average = valoracionRepository.findAveragePuntuacionByMovieId(movie.getId());
        assertThat(average).isEqualTo(4.0);
    }

    @Test
    @DisplayName("Test find by min puntuacion")
    public void testFindByMinPuntuacion() {
        List<Valoracion> result = valoracionRepository.findByMinPuntuacion(4);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getComentario()).isEqualTo("Great movie!");
    }
}