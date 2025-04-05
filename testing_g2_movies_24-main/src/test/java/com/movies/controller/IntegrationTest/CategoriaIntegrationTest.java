package com.movies.controller.IntegrationTest;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItem;

import com.movies.model.Categoria;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class CategoriaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoriaRepository customerRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Test
    @DisplayName("Test de integración findAll de categoriaController")
    void findAll() throws Exception {
        categoriaRepository.deleteAll();
        Categoria categoria1 = categoriaRepository.save(Categoria.builder()
                        .id(1L)
                        .nombre("Categoria")
                        .descripcion("Descripcion")
                        .build());

        Categoria categoria2 = categoriaRepository.save(Categoria.builder()
                .id(1L)
                .nombre("C")
                .descripcion("D")
                .build());

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categorias"))
                .andExpect(model().attribute("categorias", hasSize(2)))
                .andExpect(model().attribute("categorias", hasItem(
                        allOf(
                                hasProperty("id", is(categoria1.getId())),
                                hasProperty("nombre", is(categoria1.getNombre())),
                                hasProperty("descripcion", is(categoria1.getDescripcion()))
                        )
                )))
                .andExpect(model().attribute("categorias", hasItem(
                        allOf(
                                hasProperty("id", is(categoria2.getId())),
                                hasProperty("nombre", is(categoria2.getNombre())),
                                hasProperty("descripcion", is(categoria2.getDescripcion()))
                        )
                )));
    }
    @Test
    @DisplayName("Test de integración findById de categoriaController")
    void findById() throws Exception {
        Categoria categoria = categoriaRepository.save(Categoria.builder()
                .id(1L)
                .nombre("C")
                .descripcion("D")
                .build());

        mockMvc.perform(get("/categorias/{id}", categoria.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("categoria-detail"))
                .andExpect(model().attributeExists("categoria"));
    }
    @Test
    @DisplayName("Test de integración findById de categoriaController, id no existente")
    void findById_NotExist() throws Exception {
        mockMvc.perform(get("/categorias404/{id}", 999L))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Test de integración ir al formulario, crear categoría nueva, de categoriaController")
    void getFormToCreateCategoria() throws Exception {
        mockMvc.perform(get("/categorias/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("categoria-form"))
                .andExpect(model().attributeExists("categoria"));
    }
    @Test
    @DisplayName("Test de integración ir al formulario, editar categoría existente, de categoriaController")
    void getFormToUpdateCategoria() throws Exception {
        Categoria categoria = categoriaRepository.save(Categoria.builder().nombre("Categoría").descripcion("Descripción").build());

        mockMvc.perform(get("/categorias/edit/" + categoria.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("categoria-form"))
                .andExpect(model().attributeExists("categoria"));
    }
    @Test
    @DisplayName("Test de integración guardar categoría, de categoriaController")
    void saveCategoria() throws Exception {
    categoriaRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nombre", "Categoría")
                        .param("descripcion", "Descripción"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorias"));
        List<Categoria> categorias = categoriaRepository.findAll();
        assertEquals(1, categorias.size(), "Debe haber exactamente una categoría en la base de datos");
        Categoria categoria = categorias.get(0);
        assertEquals("Categoría", categoria.getNombre());
        assertEquals("Descripción", categoria.getDescripcion());

    }

    @Test
    @DisplayName("Test de integración borrar categoría, de categoriaController")
    void deleteCategoria() throws Exception {
        Categoria categoria = categoriaRepository.save(Categoria.builder()
                .id(1L)
                .nombre("C")
                .descripcion("D")
                .build());

        mockMvc.perform(get("/categorias/delete/{id}", categoria.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorias"));
    }

}
