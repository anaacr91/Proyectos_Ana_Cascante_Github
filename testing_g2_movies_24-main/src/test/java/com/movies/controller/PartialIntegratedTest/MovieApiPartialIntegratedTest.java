package com.movies.controller.PartialIntegratedTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.model.Categoria;
import com.movies.model.Movie;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class MovieApiPartialIntegratedTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private CategoriaRepository categoriaRepository;

    private Movie movie;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = Categoria.builder()
                .id(1L)
                .nombre("Acción")
                .descripcion("Películas llenas de acción y aventura")
                .build();
        //categoriaRepository.save(categoria);

        movie = Movie.builder()
                .id(1L)
                .name("Película 1")
                .year(2024)
                .duration(120)
                .categoria(categoria)
                .available(true)
                .rentalPricePerDay(5.99)
                .build();
        //movieRepository.save(movie);
    }

    @Test
    @DisplayName("Obtener todas las películas")
    void testFindAllMovies() throws Exception {
        when(movieRepository.findAll()).thenReturn(List.of(movie));
        mockMvc.perform(get("/api/movies")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Obtener película por ID")
    void testFindMovieById() throws Exception {
        Long movieId = 1L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        mockMvc.perform(get("/api/movies/{id}", movieId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movieId));
    }

    @Test
    @DisplayName("Obtener película por ID no encontrada")
    void testFindMovieByIdNotFound() throws Exception {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/movies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Crear una nueva película")
    void testCreateMovie() throws Exception {
        Movie newMovie = Movie.builder()
                .name("Nueva Película")
                .year(2024)
                .duration(120)
                .categoria(categoria)
                .available(true)
                .rentalPricePerDay(5.99)
                .build();
        when(movieRepository.save(any(Movie.class))).thenReturn(newMovie);
        mockMvc.perform(post("/api/movies/new")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(newMovie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Nueva Película"));
    }

    @Test
    @DisplayName("Actualizar película")
    void testUpdateMovie() throws Exception {
        Long movieId = 1L; // Asegúrate de usar un ID válido en tu base de datos de pruebas
        Movie updatedMovie = Movie.builder()
                .name("Película Actualizada")
                .duration(150)
                .year(2024)
                .available(false)
                .rentalPricePerDay(6.99)
                .build();
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(updatedMovie);

        mockMvc.perform(put("/api/movies/update/{id}", movieId)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updatedMovie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Película Actualizada"));
    }

    @Test
    @DisplayName("Eliminar película por ID")
    void testDeleteMovie() throws Exception {
        Long movieId = 1L;
        when(movieRepository.existsById(movieId)).thenReturn(true);
        doNothing().when(movieRepository).deleteById(movieId);
        //doNothing() es una forma de decirle a Mockito que no haga nada cuando se llame a un metodo
        mockMvc.perform(delete("/api/movies/delete/{id}", movieId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Eliminar película por ID no encontrada")
    void testDeleteMovieNotFound() throws Exception {
        Long movieId = 1L;
        when(movieRepository.existsById(movieId)).thenReturn(false);

        mockMvc.perform(delete("/api/movies/delete/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Buscar películas por categoría")
    void testFindMoviesByCategory() throws Exception {
        Long categoryId = 1L; // Asegúrate de usar un ID válido en tu base de datos de pruebas
        when(categoriaRepository.findById(categoryId)).thenReturn(Optional.of(categoria));
        when(movieRepository.findByCategoriaId(categoryId)).thenReturn(List.of(movie));

        mockMvc.perform(get("/api/movies/by-category/{categoryId}", categoryId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    //un json path es un camino de acceso a un valor en un objeto json
    //se usa $ para indicar el objeto raíz
    //isArray() verifica que el valor sea un array

    @Test
    @DisplayName("Buscar películas por categoría no encontrada")
    void testFindMoviesByCategoryNotFound() throws Exception {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/movies/by-category/1"))
                .andExpect(status().isNotFound());
    }
}
