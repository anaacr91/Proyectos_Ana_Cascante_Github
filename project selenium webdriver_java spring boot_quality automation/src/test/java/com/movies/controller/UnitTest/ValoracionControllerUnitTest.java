package com.movies.controller.UnitTest;

import com.movies.controller.ValoracionController;
import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Valoracion;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValoracionControllerUnitTest {

    @InjectMocks
    private ValoracionController valoracionController;

    @Mock
    private ValoracionRepository valoracionRepository;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private Model model;

    @Test
    @DisplayName("Test unitario findAll de valoraciónController")
    void findAll() {
        // Configuración de comportamiento simulado del repositorio.
        // Aquí se utiliza Mockito para simular que el método `findAll` del repositorio devuelve una lista de objetos `Valoracion`.
        when(valoracionRepository.findAll()).thenReturn(List.of(
                Valoracion.builder().id(1L).build() // Se devuelve una lista con una sola valoración de ejemplo.
        ));

        // Ejecución del método `findAll` del controlador.
        // Este método es el que se está probando. También recibe el modelo para pasar datos a la vista.
        String view = valoracionController.findAll(model);

        // Verificación de que el método `findAll` del repositorio fue llamado una vez durante la ejecución.
        // Esto asegura que el controlador interactúa con el repositorio como se esperaba.
        verify(valoracionRepository).findAll();

        // Validación del resultado devuelto por el controlador.
        // Aquí se asegura que la vista devuelta es la correcta, en este caso, "valoracion-list".
        assertEquals("valoracion-list", view);
    }


    @Test
    @DisplayName("Test unitario findById de valoraciónController")
    void findById() {
        Valoracion valoracion = Valoracion.builder().id(1L).build();

        when(customerRepository.findAll()).thenReturn(List.of());
        when(movieRepository.findAll()).thenReturn(List.of());
        when(valoracionRepository.findById(1L)).thenReturn(Optional.of(valoracion));
        String view = valoracionController.findById(1L, model);
        assertEquals("valoracion-detail", view);
        verify(valoracionRepository).findById(1L);
        verify(model).addAttribute("valoracion", valoracion);
    }

    @Test
    @DisplayName("Test unitario findById de valoraciónController, valoración no encontrada")
    void findById_ValoracionNotFound() {
        when(valoracionRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            valoracionController.findById(1L, model);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Valoración no encontrada", exception.getReason());
        verify(valoracionRepository).findById(1L);
        verify(model, never()).addAttribute(eq("valoracion"), any());
    }

    @Test
    @DisplayName("Test unitario ir al formulario, crear valoración nueva, de valoraciónController")
    void getFormCreateValoracion() {
        Valoracion valoracion = new Valoracion();
        String view = valoracionController.createValoracion(model);
        assertEquals("valoracion-form", view);
        verify(model).addAttribute(eq("valoracion"), any(Valoracion.class));
    }

    @Test
    @DisplayName("Test unitario ir al formulario, actualizar valoración existente, de valoraciónController")
    void getFormUpdateValoracion() {
        Valoracion valoracion = Valoracion.builder().id(1L).build();
        when(valoracionRepository.findById(1L)).thenReturn(Optional.of(valoracion));
        String view = valoracionController.updateValoracion(1L, model);
        assertEquals("valoracion-form", view);
        verify(model).addAttribute("valoracion", valoracion);
        verify(valoracionRepository).findById(1L);
    }

    @Test
    @DisplayName("Test unitario ir al formulario, actualizar valoración existente, valoración no encontrada, de valoraciónController")
    void getFormUpdateValoracion_NotFound() {
        when(valoracionRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            valoracionController.updateValoracion(1L, model);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Valoración no encontrada", exception.getReason());
        verify(valoracionRepository).findById(1L);
        verify(model, never()).addAttribute(eq("valoracion"), any());
    }

    @Test
    @DisplayName("Test unitario guardar valoración nueva, de valoraciónController")
    void saveValoracionNew() {
        Valoracion valoracion = Valoracion.builder()
                .customer(Customer.builder().id(1L).build())
                .movie(Movie.builder().id(1L).build())
                .comentario("Nueva valoración")
                .puntuacion(4)
                .build();
        String view = valoracionController.save(valoracion);
        assertEquals("redirect:/valoraciones", view);
        verify(valoracionRepository).save(valoracion);
    }

    @Test
    @DisplayName("Test unitario guardar valoración existente, de valoraciónController")
    void saveValoracionUpdate() {
        Valoracion valoracion = Valoracion.builder().id(1L).comentario("Comentario actualizado").puntuacion(8).build();
        String result = valoracionController.save(valoracion);
        verify(valoracionRepository, times(1)).save(valoracion);
        assertEquals("redirect:/valoraciones", result);
    }

    @Test
    @DisplayName("Test unitario borrar valoración, de valoraciónController")
    void deleteValoracion() {
        String view = valoracionController.deleteValoracion(1L);
        assertEquals("redirect:/valoraciones", view);
        verify(valoracionRepository).deleteById(1L);
    }

    @Test
    void findById_IdNotFound() {
        when(valoracionRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            valoracionController.findById(1L, model);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Valoración no encontrada", exception.getReason());
        verify(valoracionRepository).findById(1L);
        verify(model, never()).addAttribute(eq("valoracion"), any());
    }
}
