package com.movies.repository;
import com.movies.model.Categoria;
import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Rental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class RentalRepositoryTest {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MovieRepository movieRepository;

    private Customer customer;
    private Categoria categoria;
    private Movie movie;
    private Rental rental1;
    private Rental rental2;

    @BeforeEach
    void setUp() {
        // Configuración del cliente
        customer = new Customer();
        customer.setNombre("Cliente Test");
        customer.setApellido("Apellido Test");
        customer.setPassword("password123");
        customer.setEmail("test@example.com");
        customer = customerRepository.save(customer);

        categoria = new Categoria();
        categoria.setNombre("Action");
        categoria.setDescripcion("Action movies");
        categoria = categoriaRepository.save(categoria);

        // Configuración de la película
        movie = new Movie();
        movie.setName("Película Test");
        movie.setYear(2023);
        movie.setDuration(60);
        movie.setRentalPricePerDay(10.0);
        movie.setAvailable(true);
        movie.setCategoria(categoria);
        movie = movieRepository.save(movie);

        // Configuración de los alquileres
        rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setMovie(movie);
        rental1.setRentalDate(LocalDateTime.of(2023, 12, 1, 10, 0));
        rental1.setReturnDueDate(LocalDateTime.of(2023, 12, 5, 10, 0));
        rental1.setRentalPrice(50.0);
        rental1 = rentalRepository.save(rental1);

        rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setMovie(movie);
        rental2.setRentalDate(LocalDateTime.of(2023, 12, 10, 12, 0));
        rental2.setReturnDueDate(LocalDateTime.of(2023, 12, 15, 12, 0));
        rental2.setRentalPrice(60.0);
        rental2 = rentalRepository.save(rental2);
    }

    @Test
    @DisplayName("Prueba findByRentalDateBetween - Alquileres encontrados")
    void testFindByRentalDateBetween_Success() {
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 10, 0, 0);

        List<Rental> rentals = rentalRepository.findByRentalDateBetween(startDate, endDate);

        assertNotNull(rentals, "La lista de alquileres no debería ser nula.");
        assertEquals(1, rentals.size(), "Debería haber 1 alquiler en el rango.");
        assertEquals(rental1, rentals.get(0), "El alquiler debería coincidir con rental1.");
    }

    @Test
    @DisplayName("Prueba findByRentalDateBetween - Ningún alquiler encontrado")
    void testFindByRentalDateBetween_NoRentals() {
        LocalDateTime startDate = LocalDateTime.of(2023, 11, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 11, 10, 0, 0);

        List<Rental> rentals = rentalRepository.findByRentalDateBetween(startDate, endDate);

        assertNotNull(rentals, "La lista de alquileres no debería ser nula.");
        assertTrue(rentals.isEmpty(), "No debería haber alquileres en este rango.");
    }

    @Test
    @DisplayName("Prueba findByCustomerId - Alquileres encontrados")
    void testFindByCustomerId_Success() {
        List<Rental> rentals = rentalRepository.findByCustomerId(customer.getId());

        assertNotNull(rentals, "La lista de alquileres no debería ser nula.");
        assertEquals(2, rentals.size(), "Debería haber 2 alquileres para el cliente.");
        assertTrue(rentals.contains(rental1), "La lista debería contener rental1.");
        assertTrue(rentals.contains(rental2), "La lista debería contener rental2.");
    }

    @Test
    @DisplayName("Prueba findByCustomerId - Ningún alquiler encontrado")
    void testFindByCustomerId_NoRentals() {
        Long nonExistingCustomerId = 999L;
        List<Rental> rentals = rentalRepository.findByCustomerId(nonExistingCustomerId);

        assertNotNull(rentals, "La lista de alquileres no debería ser nula.");
        assertTrue(rentals.isEmpty(), "No debería haber alquileres para un cliente inexistente.");
    }

    @Test
    @DisplayName("Prueba findByMovieId - Alquileres encontrados")
    void testFindByMovieId_Success() {
        List<Rental> rentals = rentalRepository.findByMovieId(movie.getId());

        assertNotNull(rentals, "La lista de alquileres no debería ser nula.");
        assertEquals(2, rentals.size(), "Debería haber 2 alquileres para la película.");
        assertTrue(rentals.contains(rental1), "La lista debería contener rental1.");
        assertTrue(rentals.contains(rental2), "La lista debería contener rental2.");
    }

    @Test
    @DisplayName("Prueba findByMovieId - Ningún alquiler encontrado")
    void testFindByMovieId_NoRentals() {
        Long nonExistingMovieId = 999L;
        List<Rental> rentals = rentalRepository.findByMovieId(nonExistingMovieId);

        assertNotNull(rentals, "La lista de alquileres no debería ser nula.");
        assertTrue(rentals.isEmpty(), "No debería haber alquileres para una película inexistente.");
    }
}

