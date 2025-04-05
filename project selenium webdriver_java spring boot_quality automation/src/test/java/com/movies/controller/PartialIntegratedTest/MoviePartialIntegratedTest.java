package com.movies.controller.PartialIntegratedTest;

import com.movies.model.Categoria;
import com.movies.model.Movie;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MoviePartialIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("test de integración parcial de encontrar todas las películas, de movieController")
    void findAllMovies_ShouldReturnMovieList() throws Exception {
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setName("Movie 1");

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setName("Movie 2");

        Mockito.when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", Arrays.asList(movie1, movie2)))
                .andExpect(view().name("movie-list"));
    }

    @Test
    @DisplayName("test de integración parcial de encontrar una película por id, de movieController")
    void findById_ShouldReturnMovieDetail_WhenExists() throws Exception {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Test Movie");

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Action");

        movie.setCategoria(categoria);

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        Mockito.when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoria));

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attributeExists("categorias"))
                .andExpect(model().attribute("movie", movie))
                .andExpect(model().attribute("categorias", Arrays.asList(categoria)))
                .andExpect(view().name("movie-detail"));
    }

    @Test
    @DisplayName("test de integración parcial de encontrar una película por id, de movieController, película no encontrada")
    void findById_ShouldReturnNotFound_WhenMovieDoesNotExist() throws Exception {
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("test de integración parcial para Obtener formulario para crear nueva película, de movieController")
    void createForm_saveMovie_ShouldSaveNewMovie() throws Exception {
        Movie movie = new Movie();
        movie.setId(null);
        movie.setName("New Movie");

        Mockito.when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "New Movie"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies"));

        Mockito.verify(movieRepository, Mockito.times(1)).save(any(Movie.class));
    }
    @Test
    @DisplayName("test de integración parcial para Obtener formulario para editar nueva película, de movieController")
    void editForm_ShouldReturnMovieForm_WhenMovieExists() throws Exception {
        // Simular una película existente
        Movie existingMovie = new Movie();
        existingMovie.setId(1L);
        existingMovie.setName("Existing Movie");

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Drama");

        // Configuración de simulaciones
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(existingMovie));
        Mockito.when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoria));

        // Ejecutar la solicitud para acceder al formulario de edición
        mockMvc.perform(get("/movies/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attributeExists("categorias"))
                .andExpect(model().attribute("movie", existingMovie))
                .andExpect(model().attribute("categorias", Arrays.asList(categoria)))
                .andExpect(view().name("movie-form"));
    }

    @Test
    @DisplayName("test de integración parcial para Obtener formulario para editar nueva película, pelicula no existe, de movieController")
    void editForm_ShouldReturnNotFound_WhenMovieDoesNotExist() throws Exception {
        // Configurar el repositorio para devolver vacío cuando se busca la película
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutar la solicitud
        mockMvc.perform(get("/movies/update/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("test de integración parcial para borrar película, de movieController")
    void deleteById_ShouldDeleteMovie_WhenExists() throws Exception {
        Mockito.when(movieRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(get("/movies/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies"));

        Mockito.verify(movieRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("test de integración parcial para borrar película, película no existe, de movieController")
    void deleteById_ShouldReturnNotFound_WhenMovieDoesNotExist() throws Exception {
        Mockito.when(movieRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(get("/movies/delete/1"))
                .andExpect(status().isNotFound());
    }
}

