package com.movies.repository;

import com.movies.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Categoria categoria1;
    private Categoria categoria2;

    @BeforeEach
    public void setUp() {
        categoria1 = new Categoria();
        categoria1.setNombre("Acción");
        categoria1.setDescripcion("Aventuras");

        categoria2 = new Categoria();
        categoria2.setNombre("Comedia");
        categoria2.setDescripcion("Risas");

        categoriaRepository.save(categoria1);
        categoriaRepository.save(categoria2);
    }

    @Test
    @DisplayName("Test find by nombre")
    public void testFindByNombre() {
        Optional<Categoria> result = categoriaRepository.findByNombre("Acción");
        assertThat(result).isPresent();
        assertThat(result.get().getNombre()).isEqualTo("Acción");
    }

    @Test
    @DisplayName("Test find by nombre containing ignorar mayusculas")
    public void testFindByNombreContainingIgnoreCase() {
        List<Categoria> result = categoriaRepository.findByNombreContainingIgnoreCase("com");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombre()).isEqualTo("Comedia");
    }

    @Test
    @DisplayName("Test deletear por id")
    public void testDeleteById() {
        categoriaRepository.deleteById(categoria1.getId());
        Optional<Categoria> result = categoriaRepository.findById(categoria1.getId());
        assertThat(result).isNotPresent();
    }

    @Test
    @DisplayName("Test existe por id")
    public void testExistsById() {
        boolean exists = categoriaRepository.existsById(categoria1.getId());
        assertThat(exists).isTrue();
    }
}
