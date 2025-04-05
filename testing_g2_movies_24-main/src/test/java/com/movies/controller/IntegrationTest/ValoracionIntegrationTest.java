package com.movies.controller.IntegrationTest;

import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Valoracion;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ValoracionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        valoracionRepository.deleteAll();
        movieRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Test de integración findAll de valoracionController")
    void findAll() throws Exception {
        // Limpiar la base de datos antes de comenzar el test.
        // Esto garantiza que no haya datos residuales que puedan afectar los resultados del test.
        valoracionRepository.deleteAll();
        customerRepository.deleteAll();
        movieRepository.deleteAll();

        // Crear datos de prueba en la base de datos.

        // Crear y guardar un cliente en el repositorio.
        Customer customer1 = customerRepository.save(Customer.builder()
                .nombre("Ana")
                .apellido("C")
                .email("ana.c@example.com")
                .password("password") // Se define una contraseña ficticia.
                .build());

        // Crear y guardar un segundo cliente en el repositorio.
        Customer customer2 = customerRepository.save(Customer.builder()
                .nombre("P")
                .apellido("C")
                .email("p.c@example.com")
                .password("password") // Se define una contraseña ficticia.
                .build());

        // Crear y guardar una película en el repositorio.
        Movie movie1 = movieRepository.save(Movie.builder()
                .name("Inception") // Nombre de la película.
                .year(2010) // Año de estreno.
                .duration(148) // Duración en minutos.
                .rentalPricePerDay(5.00) // Precio de alquiler por día.
                .build());

        // Crear y guardar una segunda película en el repositorio.
        Movie movie2 = movieRepository.save(Movie.builder()
                .name("Titanic") // Nombre de la película.
                .year(1997) // Año de estreno.
                .duration(195) // Duración en minutos.
                .rentalPricePerDay(5.00) // Precio de alquiler por día.
                .build());

        // Crear y guardar una valoración asociada al primer cliente y la primera película.
        Valoracion valoracion1 = valoracionRepository.save(Valoracion.builder()
                .customer(customer1) // Cliente que realizó la valoración.
                .movie(movie1) // Película valorada.
                .comentario("Amazing movie!") // Comentario del cliente.
                .puntuacion(5) // Puntuación otorgada.
                .build());

        // Crear y guardar una segunda valoración asociada al segundo cliente y la segunda película.
        Valoracion valoracion2 = valoracionRepository.save(Valoracion.builder()
                .customer(customer2) // Cliente que realizó la valoración.
                .movie(movie2) // Película valorada.
                .comentario("Emotional story!") // Comentario del cliente.
                .puntuacion(4) // Puntuación otorgada.
                .build());

        // Ejecutar el test llamando al endpoint del controlador y validando el resultado.
        mockMvc.perform(get("/valoraciones")) // Realiza una petición GET al endpoint "/valoraciones".
                .andExpect(status().isOk()) // Valida que el estado HTTP sea 200 (OK).
                .andExpect(model().attributeExists("valoraciones")) // Verifica que el modelo incluye un atributo "valoraciones".
                .andExpect(model().attribute("valoraciones", hasSize(2))) // Valida que hay dos valoraciones en el modelo.
                .andExpect(model().attribute("valoraciones", hasItem( // Comprueba que el modelo contiene la primera valoración.
                        allOf(
                                hasProperty("id", is(valoracion1.getId())), // Verifica que el ID coincide.
                                hasProperty("comentario", is(valoracion1.getComentario())), // Verifica el comentario.
                                hasProperty("puntuacion", is(valoracion1.getPuntuacion())) // Verifica la puntuación.
                        )
                )))
                .andExpect(model().attribute("valoraciones", hasItem( // Comprueba que el modelo contiene la segunda valoración.
                        allOf(
                                hasProperty("id", is(valoracion2.getId())), // Verifica que el ID coincide.
                                hasProperty("comentario", is(valoracion2.getComentario())), // Verifica el comentario.
                                hasProperty("puntuacion", is(valoracion2.getPuntuacion())) // Verifica la puntuación.
                        )
                )));
    }



    @Test
    @DisplayName("Test de integración findById de valoracionController")
    void findById() throws Exception {
        // Crear datos de prueba
        Customer customer = customerRepository.save(Customer.builder()
                .nombre("Ana")
                .apellido("C")
                .email("ana.c@example.com")
                .password("password")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .name("Inception")
                .year(2010)
                .duration(148)
                .rentalPricePerDay(5.00)
                .build());

        Valoracion valoracion = valoracionRepository.save(Valoracion.builder()
                .customer(customer)
                .movie(movie)
                .comentario("Amazing movie!")
                .puntuacion(5)
                .build());

        // Verificar que la valoración se creó correctamente
        assertNotNull(valoracion.getId(), "La valoración debería haberse guardado y tener un ID.");

        // Imprimir la valoración para depurar
        System.out.println("Valoración creada: " + valoracion);

        // Ejecutar el test
        mockMvc.perform(get("/valoraciones/{id}", valoracion.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("valoracion-detail"))
                .andExpect(model().attributeExists("valoracion"))
                .andExpect(model().attribute("valoracion",
                        allOf(
                                hasProperty("id", is(valoracion.getId())),
                                hasProperty("comentario", is(valoracion.getComentario())),
                                hasProperty("puntuacion", is(valoracion.getPuntuacion())),
                                hasProperty("customer", hasProperty("id", is(customer.getId()))),
                                hasProperty("movie", hasProperty("id", is(movie.getId())))
                        )));
    }

    @Test
    @DisplayName("Test de integración findById de valoracionController, id no existente")
    void findById_NotExist() throws Exception {
        mockMvc.perform(get("/valoraciones/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test de integración ir al formulario, crear valoracion nueva, de valoracionController")
    void getFormToCreateValoracion() throws Exception {
        mockMvc.perform(get("/valoraciones/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("valoracion-form"))
                .andExpect(model().attributeExists("valoracion"));
    }

    @Test
    @DisplayName("Test de integración ir al formulario, editar valoracion existente, de valoracionController")
    void getFormToUpdateValoracion() throws Exception {
        // Crear datos de prueba
        Customer customer = customerRepository.save(Customer.builder()
                .nombre("Ana")
                .apellido("C")
                .email("ana.c@example.com")
                .password("password")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .name("Inception")
                .year(2010)
                .duration(148)
                .rentalPricePerDay(5.00)
                .build());

        Valoracion valoracion = valoracionRepository.save(Valoracion.builder()
                .customer(customer)
                .movie(movie)
                .comentario("Amazing movie!")
                .puntuacion(5)
                .build());

        // Ejecutar el test
        mockMvc.perform(get("/valoraciones/edit/{id}", valoracion.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("valoracion-form"))
                .andExpect(model().attributeExists("valoracion"));
    }

    @Test
    @DisplayName("Test de integración guardar valoracion nueva, de valoracionController")
    void saveValoracion() throws Exception {
        // Crear datos de prueba
        Customer customer = customerRepository.save(Customer.builder()
                .nombre("Ana")
                .apellido("C")
                .email("ana.c@example.com")
                .password("password")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .name("Inception")
                .year(2010)
                .duration(148)
                .rentalPricePerDay(5.00)
                .build());

        // Confirmar IDs generados
        System.out.println("Customer ID: " + customer.getId());
        System.out.println("Movie ID: " + movie.getId());

        // Enviar datos
        mockMvc.perform(MockMvcRequestBuilders.post("/valoraciones")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("customer.id", String.valueOf(customer.getId()))
                        .param("movie.id", String.valueOf(movie.getId()))
                        .param("comentario", "Amazing movie!")
                        .param("puntuacion", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/valoraciones"));

        // Verificar los datos guardados
        valoracionRepository.findAll().forEach(valoracion -> System.out.println("Saved Valoracion: " + valoracion));
        Valoracion savedValoracion = valoracionRepository.findAll().get(0);
        assertEquals("Amazing movie!", savedValoracion.getComentario());
        assertEquals(5, savedValoracion.getPuntuacion());
        assertEquals(customer.getId(), savedValoracion.getCustomer().getId());
        assertEquals(movie.getId(), savedValoracion.getMovie().getId());
    }

    @Test
    @DisplayName("Test de integración borrar valoracion, de valoracionController")
    void deleteValoracion() throws Exception {
        // Crear datos de prueba
        Customer customer = customerRepository.save(Customer.builder()
                .nombre("Ana")
                .apellido("C")
                .email("ana.c@example.com")
                .password("password")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .name("Inception")
                .year(2010)
                .duration(148)
                .rentalPricePerDay(5.00)
                .build());

        Valoracion valoracion = valoracionRepository.save(Valoracion.builder()
                .customer(customer)
                .movie(movie)
                .comentario("Amazing movie!")
                .puntuacion(5)
                .build());

        // Confirmar que la valoración fue creada
        System.out.println("Valoracion ID antes de eliminar: " + valoracion.getId());

        // Ejecutar la solicitud GET para eliminar la valoración
        mockMvc.perform(get("/valoraciones/delete/{id}", valoracion.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/valoraciones"));

        // Verificar que la valoración ya no existe
        boolean valoracionExiste = valoracionRepository.findById(valoracion.getId()).isPresent();
        assertFalse(valoracionExiste, "La valoración debería haber sido eliminada.");
    }

}
