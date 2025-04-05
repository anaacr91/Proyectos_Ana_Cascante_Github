package com.movies.controller;
import com.movies.model.*;
import com.movies.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class MovieApiController {
    private MovieRepository movieRepository;
    private CategoriaRepository categoriaRepository;

    // Obtener todas las películas
    @GetMapping("/movies")
    @Operation(summary = "Obtener todas las películas", description = "Devuelve una lista de todas las películas registradas")
    public ResponseEntity<List<Movie>> findAll() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    // Obtener una película por su ID
    @GetMapping("/movies/{id}")
    @Operation(summary = "Obtener película por ID", description = "Devuelve una película específica según su ID")
    public ResponseEntity<Movie> findById(@PathVariable Long id) {
        return movieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Película no encontrada"));
    }

    // Crear una nueva película
    @PostMapping("/movies/new")
    @Operation(summary = "Crear nueva película", description = "Crea una nueva película en la base de datos")
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        if (movie.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID debe ser nulo para crear una nueva película");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRepository.save(movie));
    }

    // Actualizar una película existente
    @PutMapping("/movies/update/{id}")
    @Operation(summary = "Actualizar película", description = "Actualiza una película existente por su ID")
    public ResponseEntity<Movie> update(@PathVariable Long id, @RequestBody Movie movie) {
        return movieRepository.findById(id)
                .map(existingMovie -> {
                    existingMovie.setName(movie.getName());
                    existingMovie.setDuration(movie.getDuration());
                    existingMovie.setYear(movie.getYear());
                    existingMovie.setCategoria(movie.getCategoria());
                    existingMovie.setAvailable(movie.isAvailable());
                    existingMovie.setRentalPricePerDay(movie.getRentalPricePerDay());
                    return ResponseEntity.ok(movieRepository.save(existingMovie));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Película no encontrada"));
    }

    // Eliminar una película por su ID
    @DeleteMapping("/movies/delete/{id}")
    @Operation(summary = "Eliminar película", description = "Elimina una película específica por su ID")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Película no encontrada");
        }
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar películas por categoría
    @GetMapping("/movies/by-category/{categoryId}")
    @Operation(summary = "Buscar películas por categoría", description = "Devuelve una lista de películas de una categoría específica")
    public ResponseEntity<List<Movie>> findByCategory(@PathVariable Long categoryId) {
        var categoria = categoriaRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
        return ResponseEntity.ok(movieRepository.findByCategoriaId(categoryId));
    }
}
