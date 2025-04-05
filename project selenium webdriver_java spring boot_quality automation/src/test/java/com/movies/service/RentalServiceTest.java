package com.movies.service;

import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Rental;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
//@Transactional
@ExtendWith(MockitoExtension.class)
class RentalServiceTest {
    @InjectMocks
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Movie movie;
    @Mock
    private Customer customer;
    @Mock
    private Rental rental;
    @Mock
    private Rental rental1;
    @Mock
    private Rental rental2;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setName("Película Test");
        movie.setRentalPricePerDay(10.0);
        movie.setAvailable(true);

        customer = new Customer();
        customer.setId(1L);
        customer.setNombre("Cliente Test");
        customer.setEmail("test@example.com");

        rental = new Rental();
        rental.setId(1L);
        rental.setMovie(movie);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDateTime.now());
        rental.setReturnDueDate(LocalDateTime.now().plusDays(5));
        rental.setRentalPrice(50.0);

        rental1 = new Rental();
        rental1.setId(1L);
        rental1.setRentalDate(LocalDateTime.of(2023, 12, 1, 10, 0));

        rental2 = new Rental();
        rental2.setId(2L);
        rental2.setRentalDate(LocalDateTime.of(2023, 12, 5, 12, 0));
    }

    @Test
    @DisplayName("Prueba del método rentMovie - Alquiler exitoso")
    void testRentMovie_Success() {
        // Simula la búsqueda de una película por ID en el repositorio de películas.
        // Mockito devuelve el objeto `movie` cuando se llama al método `findById` con el ID de la película.
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));

        // Simula la búsqueda de un cliente por ID en el repositorio de clientes.
        // Mockito devuelve el objeto `customer` cuando se llama al método `findById` con el ID del cliente.
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        // Simula el guardado de un alquiler en el repositorio de alquileres.
        // Mockito devuelve el mismo objeto `Rental` que fue pasado al método `save`.
        when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Llamada al método del servicio que se está probando.
        // Se realiza un alquiler para el cliente con el ID de la película y la duración del alquiler en días.
        Rental result = rentalService.rentMovie(customer.getId(), movie.getId(), 5);

        // Validaciones del resultado:

        // Asegurarse de que el alquiler no sea nulo.
        assertNotNull(result, "El alquiler no debería ser nulo.");

        // Validar que la película asociada al alquiler es la correcta.
        assertEquals(movie, result.getMovie(), "La película del alquiler debería coincidir.");

        // Validar que el cliente asociado al alquiler es el correcto.
        assertEquals(customer, result.getCustomer(), "El cliente del alquiler debería coincidir.");

        // Comprobar que el precio del alquiler fue calculado correctamente (5 días x 10.0 por día).
        assertEquals(50.0, result.getRentalPrice(), "El precio del alquiler debería ser 50.0.");

        // Verificar que la fecha de alquiler no sea nula.
        assertNotNull(result.getRentalDate(), "La fecha del alquiler no debería ser nula.");

        // Validar que la fecha de devolución es 5 días después de la fecha actual.
        assertEquals(LocalDateTime.now().plusDays(5).getDayOfMonth(),
                result.getReturnDueDate().getDayOfMonth(),
                "La fecha de devolución debería ser en 5 días.");

        // Verificaciones de las interacciones con los repositorios:

        // Asegurarse de que se llamó al método `findById` del repositorio de películas.
        verify(movieRepository).findById(movie.getId());

        // Asegurarse de que se llamó al método `findById` del repositorio de clientes.
        verify(customerRepository).findById(customer.getId());

        // Verificar que la película fue guardada en el repositorio después de actualizar su estado.
        verify(movieRepository).save(movie);

        // Asegurarse de que el alquiler fue guardado en el repositorio.
        verify(rentalRepository).save(any(Rental.class));
    }


    @Test
    @DisplayName("Prueba del método rentMovie - Película no disponible")
    void testRentMovie_MovieNotAvailable_message() {
        movie.setAvailable(false);
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.rentMovie(customer.getId(), movie.getId(), 5);
        }, "Se debería lanzar una excepción cuando la película no está disponible.");

        assertEquals("La película no está disponible.", exception.getMessage(), "El mensaje de la excepción debería ser 'La película no está disponible.'");

        verify(movieRepository).findById(movie.getId());
        verify(customerRepository, never()).findById(anyLong());
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    @DisplayName("Prueba del método returnMovie - Devolución exitosa")
    void testReturnMovie_Success() {
        movie.setAvailable(false);
        when(rentalRepository.findById(rental.getId())).thenReturn(Optional.of(rental));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        rentalService.returnMovie(rental.getId());

        assertTrue(movie.isAvailable(), "La película debería estar disponible después de la devolución.");

        verify(rentalRepository).findById(rental.getId());
        verify(movieRepository).save(movie);
        verify(rentalRepository).save(rental);
    }

    @Test
    @DisplayName("Prueba del método returnMovie - Alquiler no encontrado")
    void testReturnMovie_NotFound() {
        when(rentalRepository.findById(rental.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.returnMovie(rental.getId());
        }, "Se debería lanzar una excepción cuando el alquiler no es encontrado.");

        assertEquals("Alquiler no encontrado.", exception.getMessage(), "El mensaje de la excepción debería ser 'Alquiler no encontrado.'");

        verify(rentalRepository).findById(rental.getId());
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    @DisplayName("Prueba del método getRentalsByCustomer")
    void testGetRentalsByCustomer() {
        when(rentalRepository.findByCustomerId(customer.getId())).thenReturn(Arrays.asList(rental));

        List<Rental> result = rentalService.getRentalsByCustomer(customer.getId());

        assertNotNull(result, "La lista de alquileres no debería ser nula.");
        assertEquals(1, result.size(), "Debería haber un alquiler en la lista.");
        assertEquals(rental, result.get(0), "El alquiler de la lista debería coincidir con el de prueba.");

        verify(rentalRepository).findByCustomerId(customer.getId());
    }

    @Test
    @DisplayName("Prueba del método getRentalsByMovie")
    void testGetRentalsByMovie() {

        when(rentalRepository.findByMovieId(movie.getId())).thenReturn(Arrays.asList(rental));

        List<Rental> result = rentalService.getRentalsByMovie(movie.getId());

        assertNotNull(result, "La lista de alquileres no debería ser nula.");
        assertEquals(1, result.size(), "Debería haber un alquiler en la lista.");
        assertEquals(rental, result.get(0), "El alquiler de la lista debería coincidir con el de prueba.");

        verify(rentalRepository).findByMovieId(movie.getId());
    }
    @Test
    @DisplayName("Prueba del método getRentalsBetweenDates - Alquileres encontrados")
    void testGetRentalsBetweenDates_Success() {
        // Configuración de fechas de búsqueda
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 10, 0, 0);

        // Simulación del repositorio
        when(rentalRepository.findByRentalDateBetween(startDate, endDate))
                .thenReturn(Arrays.asList(rental1, rental2));

        // Ejecución del método bajo prueba
        List<Rental> result = rentalService.getRentalsBetweenDates(startDate, endDate);

        // Verificaciones
        assertNotNull(result, "La lista de alquileres no debería ser nula.");
        assertEquals(2, result.size(), "Deberían encontrarse 2 alquileres.");
        assertEquals(rental1, result.get(0), "El primer alquiler debería coincidir.");
        assertEquals(rental2, result.get(1), "El segundo alquiler debería coincidir.");

        // Verificación de interacción con el repositorio
        verify(rentalRepository, times(1)).findByRentalDateBetween(startDate, endDate);
    }

    @Test
    @DisplayName("Prueba del método getRentalsBetweenDates - Ningún alquiler encontrado")
    void testGetRentalsBetweenDates_NoRentalsFound() {
        // Configuración de fechas de búsqueda
        LocalDateTime startDate = LocalDateTime.of(2023, 11, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 11, 10, 0, 0);

        // Simulación del repositorio
        when(rentalRepository.findByRentalDateBetween(startDate, endDate))
                .thenReturn(Arrays.asList());

        // Ejecución del método bajo prueba
        List<Rental> result = rentalService.getRentalsBetweenDates(startDate, endDate);

        // Verificaciones
        assertNotNull(result, "La lista de alquileres no debería ser nula.");
        assertEquals(0, result.size(), "No deberían encontrarse alquileres.");

        // Verificación de interacción con el repositorio
        verify(rentalRepository, times(1)).findByRentalDateBetween(startDate, endDate);
    }
    @Test
    @DisplayName("Prueba del método rentMovie - Duración no válida")
    void testRentMovie_InvalidDuration() {
        int invalidDuration = 0; // Puedes probar también con valores negativos

        // Usamos lenient para permitir stubbings innecesarios
        lenient().when(movieRepository.findById(anyLong())).thenReturn(Optional.of(new Movie()));
        lenient().when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));

        // Ejecutamos el método y verificamos que se lance la excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.rentMovie(1L, 1L, invalidDuration);
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("La duración debe ser mayor a 0 días.", exception.getMessage(), "El mensaje de la excepción no es el esperado.");
    }
    @Test
    @DisplayName("Prueba del método rentMovie - Película no encontrada")
    void testRentMovie_MovieNotFound() {
        Long movieId = 1L; // ID de la película que no existe

        // Simulamos que el repositorio no devuelve nada cuando se busca por el ID de película.
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        // Ejecutamos el método y verificamos que se lance la excepción correspondiente.
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.rentMovie(1L, movieId, 5); // Llamada con un ID de película que no existe.
        });

        // Verificamos que el mensaje de la excepción sea el esperado.
        assertEquals("Película no encontrada.", exception.getMessage(), "El mensaje de la excepción no es el esperado.");
    }
    @Test
    @DisplayName("Prueba del método rentMovie - Película no disponible")
    void testRentMovie_MovieNotAvailable_exception() {
        Long movieId = 1L; // ID de la película que vamos a probar

        // Creamos una película y la marcamos como no disponible
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setAvailable(false); // La película no está disponible

        // Simulamos que el repositorio devuelve la película con el ID proporcionado
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        // Ejecutamos el metodo y verificamos que se lance la excepción correspondiente
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.rentMovie(1L, movieId, 5); // Intentamos alquilar una película no disponible
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("La película no está disponible.", exception.getMessage(), "El mensaje de la excepción no es el esperado.");
    }
    @Test
    @DisplayName("Prueba del método rentMovie - Cliente no encontrado")
    void testRentMovie_CustomerNotFound() {
        Long customerId = 1L; // ID de cliente que no existe
        Long movieId = 1L; // ID de la película
        int durationDays = 5; // Duración del alquiler

        // Usamos lenient para permitir stubbings innecesarios
        lenient().when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Ejecutamos el método y verificamos que se lance la excepción correspondiente
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.rentMovie(customerId, movieId, durationDays); // Intentamos alquilar sin un cliente
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("Película no encontrada.", exception.getMessage(), "El mensaje de la excepción no es el esperado.");
    }

    @Test
    @DisplayName("Prueba del método rentMovie - Cálculo del precio de alquiler")
    void testRentMovie_CalculateRentalPrice() {
        Long customerId = 1L; // ID de cliente
        Long movieId = 1L; // ID de película
        int durationDays = 5; // Duración del alquiler

        // Creamos un objeto de película con un precio por día de 10.0
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setRentalPricePerDay(10.0); // Precio por día
        movie.setAvailable(true); // Marcamos la película como disponible

        // Creamos un cliente válido
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setNombre("Cliente Test");
        customer.setEmail("cliente@example.com");

        // Simulamos que el repositorio de clientes y películas devuelven estos objetos
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Simulamos que el repositorio de alquileres guarda y devuelve el alquiler creado
        Rental rental = new Rental();
        rental.setMovie(movie);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDateTime.now());
        rental.setReturnDueDate(LocalDateTime.now().plusDays(durationDays));
        rental.setRentalPrice(10.0 * durationDays); // Calculamos el precio del alquiler

        when(rentalRepository.save(any(Rental.class))).thenReturn(rental); // Simulamos que se guarda correctamente

        // Ejecutamos el método rentMovie
        Rental rentalResult = rentalService.rentMovie(customerId, movieId, durationDays);

        // Verificamos que el precio del alquiler se haya calculado correctamente
        Double expectedPrice = 10.0 * durationDays; // 10.0 por día x 5 días = 50.0
        assertEquals(expectedPrice, rentalResult.getRentalPrice(), "El precio del alquiler no es el esperado.");

        // Verificamos que la película fue marcada como no disponible
        assertFalse(movie.isAvailable(), "La película debería estar marcada como no disponible después de alquilarla.");

        // Verificamos que el alquiler fue guardado con los valores correctos
        assertNotNull(rentalResult.getRentalDate(), "La fecha de alquiler no debería ser nula.");
        assertEquals(LocalDateTime.now().plusDays(durationDays).getDayOfMonth(),
                rentalResult.getReturnDueDate().getDayOfMonth(), "La fecha de devolución debería ser en 5 días.");
    }

}
