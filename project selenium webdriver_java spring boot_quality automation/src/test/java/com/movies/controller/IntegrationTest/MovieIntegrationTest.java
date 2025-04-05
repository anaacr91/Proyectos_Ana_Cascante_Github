package com.movies.controller.IntegrationTest;

import com.movies.model.Categoria;
import com.movies.model.Movie;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class MovieIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("Test de integración findAll de movieController")
    void findAll() throws Exception {
        movieRepository.deleteAll();

        Categoria categoria = categoriaRepository.save(Categoria.builder()
                .id(1L)
                .nombre("Drama")
                .build());

        Movie movie1 = movieRepository.save(Movie.builder()
                .name("Movie 1")
                .duration(120)
                .year(2022)
                .categoria(categoria)
                .rentalPricePerDay(5.00)
                .build());

        Movie movie2 = movieRepository.save(Movie.builder()
                .name("Movie 2")
                .duration(90)
                .year(2021)
                .categoria(categoria)
                .rentalPricePerDay(5.00)
                .build());

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", hasSize(2)))
                .andExpect(model().attribute("movies", hasItem(
                        allOf(
                                hasProperty("id", is(movie1.getId())),
                                hasProperty("name", is(movie1.getName())),
                                hasProperty("duration", is(movie1.getDuration())),
                                hasProperty("year", is(movie1.getYear())),
                                hasProperty("categoria", hasProperty("id", is(categoria.getId())))
                        )
                )))
                .andExpect(model().attribute("movies", hasItem(
                        allOf(
                                hasProperty("id", is(movie2.getId())),
                                hasProperty("name", is(movie2.getName())),
                                hasProperty("duration", is(movie2.getDuration())),
                                hasProperty("year", is(movie2.getYear())),
                                hasProperty("categoria", hasProperty("id", is(categoria.getId())))
                        )
                )));
    }

    @Test
    @DisplayName("Test de integración findById de movieController")
    void findById() throws Exception {
        Categoria categoria = categoriaRepository.save(Categoria.builder()
                .id(1L)
                .nombre("Action")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .name("Movie 1")
                .duration(130)
                .year(2020)
                .rentalPricePerDay(5.00)
                .categoria(categoria)
                .build());

        mockMvc.perform(get("/movies/{id}", movie.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("movie-detail"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attribute("movie", allOf(
                        hasProperty("id", is(movie.getId())),
                        hasProperty("name", is(movie.getName())),
                        hasProperty("duration", is(movie.getDuration())),
                        hasProperty("year", is(movie.getYear())),
                        hasProperty("categoria", hasProperty("id", is(categoria.getId())))
                )));
    }

    @Test
    @DisplayName("Test de integración findById de movieController, id no existente")
    void findById_NotExist() throws Exception {
        mockMvc.perform(get("/movies404/{id}", 999L))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Test de integración ir al formulario, crear pelicula nueva, de movieController")
    void getFormToCreateMovie() throws Exception {
        mockMvc.perform(get("/movies/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie-form"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attributeExists("categorias"));
    }

    @Test
    @DisplayName("Test de integración ir al formulario, editar pelicula existente, de movieController")
    void getFormToUpdateMovie() throws Exception {
        Categoria categoria = categoriaRepository.save(Categoria.builder()
                .id(1L)
                .nombre("Comedy")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .name("Movie 1")
                .duration(120)
                .year(2023)
                .rentalPricePerDay(5.00)
                .categoria(categoria)
                .build());

        mockMvc.perform(get("/movies/update/{id}", movie.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("movie-form"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attributeExists("categorias"));
    }

    @Test
    @DisplayName("Test de integración guardar película nueva, de movieController")
    void saveMovie() throws Exception {
        // Limpia los datos previos
        movieRepository.deleteAll();
        categoriaRepository.deleteAll();

        // Crea una categoría nueva
        Categoria categoria = categoriaRepository.save(Categoria.builder()
                .nombre("Thriller")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .name("Movie 1")
                .duration(110)
                .year(2021)
                .rentalPricePerDay(5.00)
                .categoria(categoria)
                .build());

        // Envía la solicitud para guardar la película
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Movie 1")
                        .param("duration", "110")
                        .param("year", "2021")
                        .param("rentalPricePerDay", "5.00")
                        .param("categoria.id", String.valueOf(categoria.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies"));

        // Verifica que la película se haya guardado correctamente
        Movie savedMovie = movieRepository.findAll().get(0);
        assertEquals("Movie 1", savedMovie.getName());
        assertEquals(110, savedMovie.getDuration());
        assertEquals(2021, savedMovie.getYear());
        assertEquals(categoria.getId(), savedMovie.getCategoria().getId());
    }


    @Test
    @DisplayName("Test de integración borrar película, de movieController")
    void deleteMovie() throws Exception {
        Categoria categoria = categoriaRepository.save(Categoria.builder()
                .id(1L)
                .nombre("Sci-Fi")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .name("Movie 1")
                .duration(110)
                .year(2019)
                .rentalPricePerDay(5.00)
                .categoria(categoria)
                .build());

        mockMvc.perform(get("/movies/delete/{id}", movie.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies"));
    }
}
