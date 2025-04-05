package com.movies.controller;

import com.movies.model.Categoria;
import com.movies.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class CategoriaApiController {
   public CategoriaRepository categoriaRepository;

    @GetMapping("/categorias")
    @Operation(summary = "Obtener todas las categorías", description = "Devuelve una lista de todas las categorías")
    public ResponseEntity<List<Categoria>> findAll() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @GetMapping("/categorias/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Devuelve una categoría específica según su ID")
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }

    @PostMapping("/categorias/new")
    @Operation(summary = "Crear una nueva categoría", description = "Crea una nueva categoría en la base de datos")
    public ResponseEntity<Categoria> create(@RequestBody Categoria categoria) {
        if (categoria.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID debe ser nulo para crear una nueva categoría");
        }
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoria);
    }

    @PutMapping("/categorias/update/{id}")
    @Operation(summary = "Actualizar categoría", description = "Actualiza una categoría existente")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaRepository.findById(id)
                .map(existingCategoria -> {
                    existingCategoria.setNombre(categoria.getNombre());
                    existingCategoria.setDescripcion(categoria.getDescripcion());
                    Categoria updatedCategoria = categoriaRepository.save(existingCategoria);
                    return ResponseEntity.ok(updatedCategoria);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }

    @DeleteMapping("/categorias/delete/{id}")
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría específica por su ID")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

