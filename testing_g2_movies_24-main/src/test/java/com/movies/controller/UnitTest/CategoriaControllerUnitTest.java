package com.movies.controller.UnitTest;

import com.movies.controller.CategoriaController;
import com.movies.model.Categoria;
import com.movies.repository.CategoriaRepository;
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
public class CategoriaControllerUnitTest {
    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    Model model;

    @Test
    @DisplayName("Test unitario findAll de categoriaController")
    void findAll(){
        when (categoriaRepository.findAll()).thenReturn(List.of(
                Categoria.builder().id(1L).build()));
        String view = categoriaController.findAll(model);
        verify(categoriaRepository).findAll();
        assertEquals("categoria-list", view);
    }

    @Test
    @DisplayName("Test unitario findById de categoriaController")
    void findById(){
        Categoria categoria = Categoria.builder().id(1L).build();

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        String view = categoriaController.findById(1L, model);
        assertEquals("categoria-detail", view);
        verify(categoriaRepository).findById(1L);
        verify(model).addAttribute("categoria", categoria);
    }
    @Test
    @DisplayName("Test unitario findById de categoriaController,categoria not found")
    void findById_CategoriaNotFound(){
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            categoriaController.findById(1L, model);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("categoria not found", exception.getReason());
        verify(categoriaRepository).findById(1L);
        verify(model, never()).addAttribute(eq("categoria"), any());
    }
    @Test
    @DisplayName("Test unitario findById de categoriaController,id not exist")
    void findById_IdNotFound(){
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            categoriaController.findById_NotExist(1L, model);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("categoria not found", exception.getReason());
        verify(categoriaRepository).findById(1L);
        verify(model, never()).addAttribute(eq("categoria"), any());
    }

    @Test
    @DisplayName("Test unitario ir al formulario de categoria, crear categoria nueva, de categoriaController")
    void getFormCreateCategoria(){
        Categoria categoria = new Categoria();
        String view = categoriaController.getFormCreateCategoria(model);
        assertEquals("categoria-form", view);
        verify(model).addAttribute(eq("categoria"), any(Categoria.class));
    }

    @Test
    @DisplayName("Test unitario ir al formulario de categoria, editar categoria existente, de categoriaController")
    void getFormUpdateCategoria(){
        Categoria categoria = Categoria.builder().id(1L).build();
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        String view = categoriaController.getFormUpdateCategoria(model, 1L);
        assertEquals("categoria-form", view);
        verify(model).addAttribute("categoria", categoria);
        verify(categoriaRepository).findById(1L);
    }
    @Test
    @DisplayName("Test unitario ir al formulario de categoria, editar categoria existente, de categoriaController, categoria not found")
    void getFormUpdateCategoria_NotFound(){
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            categoriaController.getFormUpdateCategoria(model, 1L);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("not found", exception.getReason());
        verify(categoriaRepository).findById(1L);
        verify(model, never()).addAttribute(eq("categoria"), any());
    }

    @Test
    @DisplayName("Test unitario guardar categoria nueva de categoriaController")
    void saveCategoriaNew(){
        Categoria categoria = new Categoria();
        String view = categoriaController.saveCategoria(categoria, model);
        assertEquals("redirect:/categorias", view);
        verify(categoriaRepository).save(categoria);
    }

    @Test
    @DisplayName("Test unitario guardar categoria existente de categoriaController")
    void saveCategoriaUpdate(){
        Categoria categoria = Categoria.builder().id(1L).build();
        Categoria categoriaUpdate = Categoria.builder().id(1L).nombre("Editado").build();
        when(categoriaRepository.findByNombre("Editado")).thenReturn(Optional.empty());
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        String view = categoriaController.saveCategoria(categoriaUpdate, model);
        assertEquals("redirect:/categorias", view);
        verify(categoriaRepository).findByNombre("Editado");
        verify(categoriaRepository).findById(1L);
        verify(categoriaRepository).save(categoria);
        assertEquals(categoriaUpdate.getNombre(), categoria.getNombre());
    }

    @Test
    @DisplayName("Test unitario borrar categoria de categoriaController")
    void deleteCategoria(){
        when(categoriaRepository.existsById(1L)).thenReturn(true);
        String view = categoriaController.deleteCategoria(1L);
        assertEquals("redirect:/categorias", view);
        verify(categoriaRepository).deleteById(1L);
    }
}
