package com.movies.controller.PartialIntegratedTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.model.Categoria;
import com.movies.repository.CategoriaRepository;
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
public class CategoriaApiPartialIntegratedTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = Categoria.builder()
                .id(1L)
                .nombre("Acción")
                .descripcion("Películas llenas de acción y aventura")
                .build();
    }

    @Test
    @DisplayName("GET API CATEGORIAS")
    void testFindAllCategorias() throws Exception {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Acción"));
    }

    @Test
    @DisplayName("GET API CATEGORIAS BY ID")
    void testFindCategoriaById() throws Exception {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/api/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Acción"));
    }

    @Test
    @DisplayName("GET API CATEGORIAS BY ID NOT FOUND")
    void testFindCategoriaByIdNotFound() throws Exception {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categorias/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST API CATEGORIAS")
    void testCreateCategoria() throws Exception {
        Categoria newCategoria = Categoria.builder()
                .nombre("Comedia")
                .descripcion("Películas divertidas")
                .build();

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(newCategoria);

        mockMvc.perform(post("/api/categorias/new")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(newCategoria)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Comedia"));
    }

    @Test
    @DisplayName("POST API CATEGORIAS BAD REQUEST")
    void testCreateCategoriaBadRequest() throws Exception {
        categoria.setId(1L); // Simulando que ya tiene un ID, lo cual es un error
        when(categoriaRepository.save(any(Categoria.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/api/categorias/new")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(categoria)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT API CATEGORIAS")
    void testUpdateCategoria() throws Exception {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        categoria.setNombre("Drama");

        mockMvc.perform(put("/api/categorias/update/1")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(categoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Drama"));
    }

    @Test
    @DisplayName("PUT API CATEGORIAS NOT FOUND")
    void testDeleteCategoria() throws Exception {
        when(categoriaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoriaRepository).deleteById(1L);

        mockMvc.perform(delete("/api/categorias/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE API CATEGORIAS NOT FOUND")
    void testDeleteCategoriaNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(categoriaRepository).deleteById(1L);

        mockMvc.perform(delete("/api/categorias/delete/1"))
                .andExpect(status().isNotFound());
    }
}
