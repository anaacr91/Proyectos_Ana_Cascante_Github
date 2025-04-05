package com.movies.controller.SeleniumTestUI.ValoracionSeleniumTest;

import com.movies.model.Categoria;
import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Valoracion;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ValoracionDetailTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    WebDriver driver;

    @BeforeEach
    void setUp() {
        valoracionRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
        categoriaRepository.deleteAllInBatch();
        //driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // para que no se abra el navegador
        options.addArguments("--disable-gpu"); // Deshabilita la aceleración de hardware
        options.addArguments("--window-size=1920,1080"); // Tamaño de la ventana
        options.addArguments("--no-sandbox"); // Bypass OS security model, requerido en entornos sin GUI
        options.addArguments("--disable-dev-shm-usage"); // Deshabilita el uso de /dev/shm manejo de memoria compartida
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Test Valoracion Detail Page Titulo y cabecera")
    public void testValoracionDetailPage() {
        // Crear datos de prueba
        Customer customer = customerRepository.save(
                Customer.builder().id(1L).nombre("Ana").apellido("C").email("ana.c@example.com").password("123").build());
        Movie movie = movieRepository.save(
                Movie.builder().id(1L).name("Inception").duration(148).year(2010).rentalPricePerDay(5.00).build());
        Valoracion valoracion = valoracionRepository.save(
                Valoracion.builder().id(1L).puntuacion(5).comentario("Excelente película").customer(customer).movie(movie).build());

        // Navegar a la página de detalle
        driver.get("http://localhost:8080/valoraciones/" + valoracion.getId());
        driver.navigate().refresh();

        // Verificar encabezado
        String header = driver.findElement(By.id("valoracion_header")).getText();
        assertEquals("Detalle de Valoración", header);

        // Verificar ID
        String valoracionId = driver.findElement(By.id("valoracion_id")).getText();
        assertTrue(valoracionId.matches("\\d+"), "El ID de la valoración debe ser numérico");

        // Verificar usuario
        String usuario = driver.findElement(By.id("valoracion_customer")).getText();
        assertEquals("Ana", usuario);

        // Verificar película
        String pelicula = driver.findElement(By.id("valoracion_movie")).getText();
        assertEquals("Inception", pelicula);

        // Verificar puntuación
        String puntuacion = driver.findElement(By.id("valoracion_puntuacion")).getText();
        assertEquals("5", puntuacion);

        // Verificar comentario
        String comentario = driver.findElement(By.id("valoracion_comentario")).getText();
        assertEquals("Excelente película", comentario);

        // Verificar botón Editar
        WebElement editButton = driver.findElement(By.id("editBtn_valoracion"));
        assertTrue(editButton.isDisplayed());
        assertEquals("Editar", editButton.getText());

        // Verificar botón Borrar
        WebElement deleteButton = driver.findElement(By.id("deleteBtn_valoracion"));
        assertTrue(deleteButton.isDisplayed());
        assertEquals("Borrar", deleteButton.getText());

        // Verificar botón Volver
        WebElement backButton = driver.findElement(By.id("backBtn_valoracion_list"));
        assertTrue(backButton.isDisplayed());
        assertEquals("Volver a la lista de valoraciones", backButton.getText());
    }


    @Test
    @DisplayName("Test Valoracion Detail añadir pelicula")
    public void testValoracionMovie() {
        // Crear datos necesarios
        Categoria categoria = categoriaRepository.save(
                Categoria.builder().nombre("Acción").descripcion("Películas de acción").build()
        );
        Movie movie = movieRepository.save(
                Movie.builder().name("Inception").duration(148).year(2010).rentalPricePerDay(5.00).categoria(categoria).build()
        );
        Customer customer = customerRepository.save(
                Customer.builder().nombre("Ana").apellido("C").email("ana.c@example.com").password("123").build()
        );
        Valoracion valoracion = valoracionRepository.save(
                Valoracion.builder().customer(customer).movie(movie).comentario("Increíble").puntuacion(9).build()
        );

        // Navegar a la página de detalle de la valoración
        driver.get("http://localhost:8080/valoraciones/" + valoracion.getId());
        driver.navigate().refresh();

        // Verificar la película asociada
        String peliculaNombre = driver.findElement(By.xpath("//p[strong[text()='Película:']]//span")).getText();
        assertEquals(movie.getName(), peliculaNombre);
    }

    @Test
    @DisplayName("Test Valoracion Detail añadir cliente")
    public void testValoracionCustomer() {
        // Crear datos necesarios
        Customer customer = customerRepository.save(
                Customer.builder().nombre("Ana").apellido("C").email("ana.c@example.com").password("123").build()
        );
        Movie movie = movieRepository.save(
                Movie.builder().name("Inception").duration(148).year(2010).rentalPricePerDay(5.00).build()
        );
        Valoracion valoracion = valoracionRepository.save(
                Valoracion.builder().customer(customer).movie(movie).comentario("Excelente").puntuacion(9).build()
        );

        // Navegar a la página de detalle de la valoración
        driver.get("http://localhost:8080/valoraciones/" + valoracion.getId());
        driver.navigate().refresh();

        // Verificar el cliente asociado
        String clienteNombre = driver.findElement(By.xpath("//p[strong[text()='Usuario:']]//span")).getText();
        assertEquals(customer.getNombre(), clienteNombre);
    }

}
