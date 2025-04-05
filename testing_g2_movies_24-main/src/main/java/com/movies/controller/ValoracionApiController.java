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
public class ValoracionApiController {
    private ValoracionRepository valoracionRepository;
    private CustomerRepository customerRepository;
    private MovieRepository movieRepository;

    // Obtener todas las valoraciones
    @GetMapping("/valoraciones")
    @Operation(summary = "Obtener todas las valoraciones", description = "Devuelve una lista de todas las valoraciones registradas")
    public ResponseEntity<List<Valoracion>> findAll() {
        return ResponseEntity.ok(valoracionRepository.findAll());
    }

    // Obtener una valoración por su ID
    @GetMapping("/valoraciones/{id}")
    @Operation(summary = "Obtener valoración por ID", description = "Devuelve una valoración específica según su ID")
    public ResponseEntity<Valoracion> findById(@PathVariable Long id) {
        return valoracionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valoración no encontrada"));
    }

    // Crear una nueva valoración
    @PostMapping("/valoraciones/new")
    @Operation(summary = "Crear nueva valoración", description = "Crea una nueva valoración para una película y un cliente")
    public ResponseEntity<Valoracion> createValoracion(@RequestBody Valoracion valoracion) {
        if (valoracion.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        if (!customerRepository.existsById(valoracion.getCustomer().getId()) ||
                !movieRepository.existsById(valoracion.getMovie().getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer or Movie not found");
        }
        Valoracion savedValoracion = valoracionRepository.save(valoracion);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedValoracion);
    }


    // Actualizar una valoración existente
    @PutMapping("/valoraciones/update/{id}")
    @Operation(summary = "Actualizar valoración", description = "Actualiza los datos de una valoración existente")
    public ResponseEntity<Valoracion> update(@PathVariable Long id, @RequestBody Valoracion valoracion) {
        return valoracionRepository.findById(id)
                .map(existingValoracion -> {
                    existingValoracion.setComentario(valoracion.getComentario());
                    existingValoracion.setPuntuacion(valoracion.getPuntuacion());

                    // Validar que el cliente y la película existan antes de asignarlos
                    if (valoracion.getCustomer() != null) {
                        var customer = customerRepository.findById(valoracion.getCustomer().getId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
                        existingValoracion.setCustomer(customer);
                    }

                    if (valoracion.getMovie() != null) {
                        var movie = movieRepository.findById(valoracion.getMovie().getId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Película no encontrada"));
                        existingValoracion.setMovie(movie);
                    }

                    return ResponseEntity.ok(valoracionRepository.save(existingValoracion));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Valoración no encontrada"));
    }

    // Eliminar una valoración por su ID
    @DeleteMapping("/valoraciones/delete/{id}")
    @Operation(summary = "Eliminar valoración", description = "Elimina una valoración específica por su ID")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!valoracionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Valoración no encontrada");
        }
        valoracionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
