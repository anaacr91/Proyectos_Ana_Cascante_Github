package com.movies.controller.SeleniumTestUI.ValoracionSeleniumTest;

import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Valoracion;
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
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ValoracionListTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    WebDriver driver;

    @BeforeEach
    void setUp() {
        valoracionRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // para que no se abra el navegador
        options.addArguments("--disable-gpu"); // Deshabilita la aceleración de hardware
        options.addArguments("--window-size=1920,1080"); // Tamaño de la ventana
        options.addArguments("--no-sandbox"); // Bypass OS security model, requerido en entornos sin GUI
        options.addArguments("--disable-dev-shm-usage"); // Deshabilita el uso de /dev/shm manejo de memoria compartida
        driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/valoraciones");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Comprobar título de la página")
    void title() {
        String title = driver.getTitle();
        assertEquals("Lista de Valoraciones", title);
    }

    @Test
    @DisplayName("Comprobar encabezado <h1>")
    void h1() {
        WebElement h1 = driver.findElement(By.tagName("h1"));
        assertEquals("Lista de Valoraciones", h1.getText());
    }

    @Test
    @DisplayName("Comprobar que existe el botón de Crear nueva valoración")
    void buttonCreateValoracion() {
        // Localiza el botón de "Crear nueva valoración" en la página utilizando su clase CSS.
        // Aquí se usa Selenium para buscar el elemento en el DOM por su clase CSS "btn-primary".
        WebElement createButton = driver.findElement(By.className("btn-primary"));

        // Verifica que el texto del botón sea exactamente "Crear nueva Valoración".
        // Esto asegura que el botón tiene el contenido esperado.
        assertEquals("Nueva Valoración", createButton.getText(),
                "El texto del botón debería ser 'Crear nueva Valoración'.");

        // Simula un clic en el botón localizado.
        // Esto emula el comportamiento del usuario al interactuar con el botón en la interfaz.
        createButton.click();

        // Verifica que, tras hacer clic, el navegador redirige correctamente a la URL esperada.
        // En este caso, la URL debería ser la correspondiente al formulario para crear una nueva valoración.
        assertEquals("http://localhost:8080/valoraciones/new", driver.getCurrentUrl(),
                "La URL actual debería ser la del formulario para crear una nueva valoración.");
    }


    @Test
    @DisplayName("Comprobar mensaje de tabla vacía cuando no hay datos")
    void tableEmpty() {
        List<WebElement> noValoracionesMessages = driver.findElements(By.tagName("p"));
        assertFalse(noValoracionesMessages.isEmpty(), "El mensaje de 'No hay valoraciones' no se encontró.");
        assertEquals("No hay valoraciones disponibles.", noValoracionesMessages.get(0).getText());

        // Verificar que la tabla no está presente
        List<WebElement> tables = driver.findElements(By.tagName("table"));
        assertTrue(tables.isEmpty(), "La tabla no debería estar presente si no hay valoraciones.");
    }

    @Test
    @DisplayName("Comprobar tabla con valoraciones")
    void tableWithValoraciones() {
        // Crear un cliente con todos los campos necesarios
        Customer customer = customerRepository.save(
                Customer.builder()
                        .nombre("Ana")
                        .apellido("Perez")
                        .email("ana.p@example.com")
                        .password("securePassword123") // Agregamos un password válido
                        .build()
        );

        // Crear una película con los campos requeridos
        Movie movie = movieRepository.save(
                Movie.builder()
                        .name("Inception")
                        .duration(148)
                        .year(2010)
                        .rentalPricePerDay(5.00)
                        .build()
        );

        // Crear valoraciones asociadas al cliente y película
        valoracionRepository.saveAll(List.of(
                Valoracion.builder()
                        .customer(customer)
                        .movie(movie)
                        .comentario("Excelente película")
                        .puntuacion(9)
                        .build(),
                Valoracion.builder()
                        .customer(customer)
                        .movie(movie)
                        .comentario("Buena película")
                        .puntuacion(7)
                        .build()
        ));

        // Refrescar la página para cargar los nuevos datos
        driver.navigate().refresh();

        // Localizar la tabla y verificar que esté visible
        WebElement valoracionTable = driver.findElement(By.tagName("table"));
        assertTrue(valoracionTable.isDisplayed());

        // Verificar el número de filas (encabezado + 2 filas de datos)
        List<WebElement> rows = valoracionTable.findElements(By.tagName("tr"));
        assertEquals(3, rows.size());
    }


    @Test
    @DisplayName("Comprobar contenido de las filas de la tabla con valoraciones")
    void tableWithValoraciones_rows() {
        // Crear un cliente con todos los campos requeridos
        Customer customer = customerRepository.save(
                Customer.builder()
                        .nombre("Ana")
                        .apellido("Perez")
                        .email("ana.p@example.com")
                        .password("securePassword123") // Añadimos un password válido
                        .build()
        );

        // Crear una película con los campos necesarios
        Movie movie = movieRepository.save(
                Movie.builder()
                        .name("Inception")
                        .duration(148)
                        .year(2010)
                        .rentalPricePerDay(5.00)
                        .build()
        );

        // Crear una valoración asociada al cliente y a la película
        Valoracion valoracion = valoracionRepository.save(
                Valoracion.builder()
                        .customer(customer)
                        .movie(movie)
                        .comentario("Excelente película")
                        .puntuacion(9)
                        .build()
        );

        // Refrescar la página para que los datos se reflejen en la tabla
        driver.navigate().refresh();

        // Localizar la tabla y sus filas
        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Verificar contenido de la primera fila de datos
        List<WebElement> cells = rows.get(1).findElements(By.tagName("td"));

        assertEquals(valoracion.getId().toString(), cells.get(0).getText());
        assertEquals(customer.getNombre(), cells.get(1).getText());
        assertEquals(movie.getName(), cells.get(2).getText());
        assertEquals("9", cells.get(3).getText());
        assertEquals("Excelente película", cells.get(4).getText());
    }


    @Test
    @DisplayName("Comprobar botones de acción en las filas de la tabla")
    void actionButtons() {
        // Crear un cliente con todos los campos necesarios
        Customer customer = customerRepository.save(
                Customer.builder()
                        .nombre("Ana")
                        .apellido("Perez")
                        .email("ana.p@example.com")
                        .password("securePassword123") // Aseguramos un password válido
                        .build()
        );

        // Crear una película con los campos requeridos
        Movie movie = movieRepository.save(
                Movie.builder()
                        .name("Inception")
                        .duration(148)
                        .year(2010)
                        .rentalPricePerDay(5.00)
                        .build()
        );

        // Crear una valoración asociada al cliente y película
        Valoracion valoracion = valoracionRepository.save(
                Valoracion.builder()
                        .customer(customer)
                        .movie(movie)
                        .comentario("Buena película")
                        .puntuacion(8)
                        .build()
        );

        // Refrescar la página para cargar los nuevos datos
        driver.navigate().refresh();

        // Localizar la tabla y verificar los botones de acción
        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Seleccionar la celda de acciones en la primera fila de datos
        WebElement actionCell = rows.get(1).findElements(By.tagName("td")).get(5);

        // Verificar el botón "Ver"
        WebElement viewButton = actionCell.findElement(By.className("btn-info"));
        assertTrue(viewButton.isDisplayed());
        assertTrue(viewButton.getText().contains("Ver"));

        // Verificar el botón "Editar"
        WebElement editButton = actionCell.findElement(By.className("btn-warning"));
        assertTrue(editButton.isDisplayed());
        assertTrue(editButton.getText().contains("Editar"));

        // Verificar el botón "Eliminar"
        WebElement deleteButton = actionCell.findElement(By.className("btn-danger"));
        assertTrue(deleteButton.isDisplayed());
        assertTrue(deleteButton.getText().contains("Eliminar"));
    }

}
