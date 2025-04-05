package com.movies.controller.PartialIntegratedTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Valoracion;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
public class ValoracionApiPartialIntegratedTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValoracionRepository valoracionRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private MovieRepository movieRepository;

    private Valoracion valoracion;
    private Customer customer;
    private Movie movie;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan@example.com")
                .build();
        customerRepository.save(customer);
        movie = Movie.builder()
                .name("Película 1")
                .year(2024)
                .duration(120)
                .available(true)
                .rentalPricePerDay(5.99)
                .build();
        movieRepository.save(movie);
        valoracion = Valoracion.builder()
                .customer(customer)
                .movie(movie)
                .comentario("Excelente película")
                .puntuacion(5)
                .build();
        valoracionRepository.save(valoracion);
    }

    @Test
    @DisplayName("Test findAllValoraciones")
    void testFindAllValoraciones() throws Exception {
        when(valoracionRepository.findAll()).thenReturn(List.of(valoracion));

        mockMvc.perform(get("/api/valoraciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Excelente película"));
    }

    @Test
    @DisplayName("Test findValoracionById")
    void testFindValoracionById() throws Exception {
        when(valoracionRepository.findById(1L)).thenReturn(Optional.of(valoracion));

        mockMvc.perform(get("/api/valoraciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario").value("Excelente película"));
    }

    @Test
    @DisplayName("Test findValoracionByIdNotFound")
    void testFindValoracionByIdNotFound() throws Exception {
        when(valoracionRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/valoraciones/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test createValoracion")
    void testCreateValoracion() throws Exception {
        Valoracion newValoracion = Valoracion.builder()
                .customer(customer)
                .movie(movie)
                .comentario("Nueva valoración")
                .puntuacion(4)
                .build();

        when(customerRepository.existsById(customer.getId())).thenReturn(true);
        when(movieRepository.existsById(movie.getId())).thenReturn(true);
        when(valoracionRepository.save(any(Valoracion.class))).thenReturn(newValoracion);

        mockMvc.perform(post("/api/valoraciones/new")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(newValoracion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comentario").value("Nueva valoración"));
    }

    @Test
    @DisplayName("Test createValoracionBadRequest")
    void testCreateValoracionBadRequest() throws Exception {
        valoracion.setId(1L);
        mockMvc.perform(post("/api/valoraciones/new")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(valoracion)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test createValoracionCustomerNotFound")
    void testUpdateValoracion() throws Exception {
        Long valoracionId = 1L;
        Valoracion updatedValoracion = Valoracion.builder()
                .id(valoracionId)
                .customer(customer)
                .movie(movie)
                .comentario("Película actualizada")
                .puntuacion(4)
                .build();
        when(valoracionRepository.findById(valoracionId)).thenReturn(Optional.of(valoracion));
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        when(valoracionRepository.save(any(Valoracion.class))).thenReturn(updatedValoracion);

        mockMvc.perform(put("/api/valoraciones/update/1")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updatedValoracion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario").value("Película actualizada"))
                .andExpect(jsonPath("$.puntuacion").value(4));
    }

    @Test
    @DisplayName("Test updateValoracionNotFound")
    void testDeleteValoracion() throws Exception {
        when(valoracionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(valoracionRepository).deleteById(1L);

        mockMvc.perform(delete("/api/valoraciones/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Test deleteValoracionNotFound")
    void testDeleteValoracionNotFound() throws Exception {
        when(valoracionRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/valoraciones/delete/1"))
                .andExpect(status().isNotFound());
    }

}
