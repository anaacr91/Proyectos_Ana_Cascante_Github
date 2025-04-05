package com.movies.controller.UnitTest;

import com.movies.controller.MovieController;
import com.movies.model.Movie;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.MovieRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class MovieControllerUnitTest {
    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    Model model;

    @Test
    @DisplayName("Test unitario findAll de movieController")
    void findAll(){
        when (movieRepository.findAll()).thenReturn(List.of(
                Movie.builder().id(1L).build()));
        String view = movieController.findAll(model);
        verify(movieRepository).findAll();
        assertEquals("movie-list", view);
    }

    @Test
    @DisplayName("Test unitario find por id de movieController")
    void findById(){
        Movie movie = Movie.builder().id(1L).build();

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        String view = movieController.findById(model, 1L);
        assertEquals("movie-detail", view);
        verify(movieRepository).findById(1L);
        verify(model).addAttribute("movie", movie);
    }
    @Test
    @DisplayName("Test unitario find por id, pelicula no existente, de movieController")
    void findById_MovieNotFound(){
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            movieController.findById(model,1L);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("movie not found", exception.getReason());
        verify(movieRepository).findById(1L);
        verify(model, never()).addAttribute(eq("movie"), any());
    }
    @Test
    @DisplayName("Test unitario find por id, id no existente, de movieController")
    void findById_IdNotFound(){
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            movieController.findById_NotExist(model, 1L);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Pelicula no encontrada", exception.getReason());
        verify(movieRepository).findById(1L);
        verify(model, never()).addAttribute(eq("movie"), any());
    }

    @Test
    @DisplayName("Test unitario ir al formulario, crear pelicula nueva, de movieController")
    void getFormCreateMovie(){
        Movie movie = new Movie();
        String view = movieController.createForm(model);
        assertEquals("movie-form", view);
        verify(model).addAttribute(eq("movie"), any(Movie.class));
    }

    @Test
    @DisplayName("Test unitario ir al formulario, editar pelicula existente, de movieController")
    void getFormUpdateMovie(){
        Movie movie = Movie.builder().id(1L).build();
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        String view = movieController.editForm(model, 1L);
        assertEquals("movie-form", view);
        verify(model).addAttribute("movie", movie);
        verify(movieRepository).findById(1L);
    }
    @Test
    @DisplayName("Test unitario editar pelicula, id no encontrado, de movieController")
    void getFormUpdateMovie_NotFound(){
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            movieController.editForm(model, 1L);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Movie not found", exception.getReason());
        verify(movieRepository).findById(1L);
        verify(model, never()).addAttribute(eq("movie"), any());
    }

    @Test
    @DisplayName("Test unitario guardar nuevo de movieController")
    void saveMovieNew(){
        Movie movie = new Movie();
        String view = movieController.saveMovie(movie);
        assertEquals("redirect:/movies", view);
        verify(movieRepository).save(movie);
    }

    @Test
    @DisplayName("Test unitario guardar existente de movieController")
    void saveMovieUpdate(){
        Movie movie = Movie.builder().id(1L).name("peli").duration(123).year(2020).available(true).rentalPricePerDay(5.00).build();
        Movie movieUpdate = Movie.builder().id(1L).name("Editado").build();
        String view = movieController.saveMovie(movieUpdate);
        assertEquals("redirect:/movies", view);
        verify(movieRepository).save(movie);
    }

    @Test
    @DisplayName("Test unitario borrar de movieController")
    void deleteMovie(){
        when(movieRepository.existsById(1L)).thenReturn(true);
        String view = movieController.deleteById(1L);
        assertEquals("redirect:/movies", view);
        verify(movieRepository).deleteById(1L);
    }
}
