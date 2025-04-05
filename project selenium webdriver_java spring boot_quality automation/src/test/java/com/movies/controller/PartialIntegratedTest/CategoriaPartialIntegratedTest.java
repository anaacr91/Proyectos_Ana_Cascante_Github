package com.movies.controller.PartialIntegratedTest;

import com.movies.model.Categoria;
import com.movies.repository.CategoriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaPartialIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("test de integración parcial de encontrar todas las categorías, de categoriaController")
    void findAll() throws Exception {
        when (categoriaRepository.findAll()).thenReturn(List.of(
                Categoria.builder().id(1L).build(),
                Categoria.builder().id(2L).build()));

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(view().name("categoria-list"))
                .andExpect(model().attributeExists("categorias"))
                .andExpect(model().attribute("categorias", hasSize(2)));
    }

    @Test
    @DisplayName("test de integración parcial de encontrar una categoría por id, de categoriaController")
    void findById() throws Exception {
        Categoria categoria = Categoria.builder().id(1L).build();

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/categorias/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("categoria-detail"))
                .andExpect(model().attributeExists("categoria"));
    }

    @Test
    @DisplayName("test de integración parcial de encontrar una categoría por id, de categoriaController, categoria no encontrada")
    void findById_CategoriaNotFound() throws Exception{
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/categorias/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(categoriaRepository).findById(1L);
    }

    @Test
    @DisplayName("Test de integración parcial para Obtener formulario para crear nueva categoria, de categoriaController")
    void getFormCreateCategoria() throws Exception {

        mockMvc.perform(get("/categorias/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categoria"))
                .andExpect(view().name("categoria-form"));
    }

    @Test
    @DisplayName("Test de integración parcial para Obtener formulario para actualizar una categoria, de categoriaController")
    void getFormUpdateCategoria() throws Exception {
        Categoria categoria = Categoria.builder().id(1L).build();

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/categorias/edit/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("categoria-form"))
                .andExpect(model().attributeExists("categoria"))
                .andExpect(model().attribute("categoria", categoria));
    }

    @Test
    @DisplayName("Test de integración parcial para Obtener formulario para actualizar una categoria, de categoriaController, categoria no encontrada")
    void getFormUpdateCategoria_NotFound() throws Exception{
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/categorias/update/{id}", 1L))
                .andExpect(status().isNotFound());
    }
    @Test
    void saveCategoriaNew () throws Exception {
        mockMvc.perform(post("/categorias")
//                        .param("id", "1")
                        .param("nombre", "Categoria")
                        .param("Descripcion", "Descripcion"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorias"));

        verify(categoriaRepository).save(any(Categoria.class));
    }
    @Test
    @DisplayName("Test de integración parcial para guardar una categoria actualizada, de categoriaController")
    void saveCategoriaUpdate() throws Exception {
        Categoria categoria = Categoria.builder().id(1L).nombre("Categoria").build();
        when(categoriaRepository.findByNombre("Categoria Actualizada")).thenReturn(Optional.empty());
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        mockMvc.perform(post("/categorias")
                        .param("id", "1")
                        .param("nombre", "Categoria Actualizada")
                        .param("Descripcion", "Descripcion"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorias"));

        verify(categoriaRepository).findByNombre("Categoria Actualizada");
        verify(categoriaRepository).findById(1L);
        verify(categoriaRepository).save(any(Categoria.class));
    }
    @Test
    @DisplayName("Test de integración parcial para borrar una categoria de categoriaController")
    void deleteCategoria() throws Exception{
        when(categoriaRepository.existsById(1L)).thenReturn(true);
        mockMvc.perform(get("/categorias/delete/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorias"));

        verify(categoriaRepository).deleteById(1L);
    }

}