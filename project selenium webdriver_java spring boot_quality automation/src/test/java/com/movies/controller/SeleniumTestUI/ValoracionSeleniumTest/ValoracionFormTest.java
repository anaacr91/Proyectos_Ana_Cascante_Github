package com.movies.controller.SeleniumTestUI.ValoracionSeleniumTest;

import com.movies.model.Customer;
import com.movies.model.Movie;
import com.movies.model.Valoracion;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ValoracionFormTest {

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
    @DisplayName("Comprobar inputs vacíos si es CREACIÓN")
    void checkCreation_EmptyInputs() {
        // Configuración inicial
        customerRepository.save(Customer.builder()
                .nombre("Ana")
                .apellido("Perez")
                .email("ana.p@example.com")
                .password("1234") // Asegúrate de establecer un valor para 'password'
                .build());

        movieRepository.save(Movie.builder()
                .name("Inception")
                .year(2010)
                .duration(120) // Asigna un valor para 'duration'
                .rentalPricePerDay(5.00)
                .build());

        // Navegar a la página
        driver.get("http://localhost:8080/valoraciones/new");

        // Validar encabezado
        var h1 = driver.findElement(By.tagName("h1"));
        assertEquals("Formulario de Valoración", h1.getText());

        // Validar campos vacíos
        var inputComentario = driver.findElement(By.id("comentario"));
        assertTrue(inputComentario.getAttribute("value").isEmpty());

        var inputPuntuacion = driver.findElement(By.id("puntuacion"));
        assertTrue(inputPuntuacion.getAttribute("value").isEmpty());

        // Validar selects
        Select customerSelect = new Select(driver.findElement(By.id("usuarioId")));
        assertFalse(customerSelect.isMultiple());
        assertEquals(2, customerSelect.getOptions().size()); // Incluye el placeholder

        Select movieSelect = new Select(driver.findElement(By.id("peliculaId")));
        assertFalse(movieSelect.isMultiple());
        assertEquals(2, movieSelect.getOptions().size()); // Incluye el placeholder
    }




    @Test
    @DisplayName("Comprobar que el formulario aparece relleno al editar una valoración")
    void checkEdition_FilledInputs() {
        // Configuración inicial
        var customer = customerRepository.save(Customer.builder()
                .nombre("Ana")
                .apellido("Perez")
                .email("ana.p@example.com")
                .password("1234") // Establecer un valor para 'password'
                .build());

        var movie = movieRepository.save(Movie.builder()
                .name("Inception")
                .year(2010)
                .duration(120) // Asegúrate de establecer una duración
                .rentalPricePerDay(5.00)
                .build());

        var valoracion = valoracionRepository.save(Valoracion.builder()
                .customer(customer)
                .movie(movie)
                .comentario("Gran película")
                .puntuacion(5)
                .build());

        // Navegar al formulario de edición
        driver.get("http://localhost:8080/valoraciones/edit/" + valoracion.getId());

        // Validar campos
        var inputComentario = driver.findElement(By.id("comentario"));
        assertEquals("Gran película", inputComentario.getAttribute("value"));

        var inputPuntuacion = driver.findElement(By.id("puntuacion"));
        assertEquals("5", inputPuntuacion.getAttribute("value"));

        // Validar selects
        Select customerSelect = new Select(driver.findElement(By.id("usuarioId")));
        assertEquals(String.valueOf(customer.getId()), customerSelect.getFirstSelectedOption().getAttribute("value"));

        Select movieSelect = new Select(driver.findElement(By.id("peliculaId")));
        assertEquals(String.valueOf(movie.getId()), movieSelect.getFirstSelectedOption().getAttribute("value"));
    }



    @Test
    @DisplayName("Entrar en el formulario y crear una nueva valoración y enviar")
    void crearNuevaValoracionYEnviar() {
        // Configuración inicial
        var customer = customerRepository.save(Customer.builder()
                .nombre("Ana")
                .apellido("Perez")
                .email("ana.p@example.com")
                .password("1234") // Establecer password
                .build());

        var movie = movieRepository.save(Movie.builder()
                .name("Inception")
                .year(2010)
                .duration(120) // Establecer duración
                .rentalPricePerDay(5.00)
                .build());

        // Navegar al formulario de creación
        driver.get("http://localhost:8080/valoraciones/new");

        // Esperar a que los selects estén completamente cargados
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usuarioId")));
        wait.until(driver -> !new Select(driver.findElement(By.id("usuarioId"))).getOptions().isEmpty());

        // Depuración: imprimir opciones disponibles en el select
        Select customerSelect = new Select(driver.findElement(By.id("usuarioId")));
        List<WebElement> customerOptions = customerSelect.getOptions();
        for (WebElement option : customerOptions) {
            System.out.println("Option text: " + option.getText());
        }

        // Seleccionar cliente por valor (ID)
        customerSelect.selectByValue(String.valueOf(customer.getId()));

        // Seleccionar película
        Select movieSelect = new Select(driver.findElement(By.id("peliculaId")));
        movieSelect.selectByVisibleText("Inception");

        // Llenar otros campos
        var inputComentario = driver.findElement(By.id("comentario"));
        inputComentario.sendKeys("Excelente película");

        var inputPuntuacion = driver.findElement(By.id("puntuacion"));
        inputPuntuacion.sendKeys("9");

        // Guardar
        driver.findElement(By.id("btnSave")).click();

        // Validar redirección y datos guardados
        assertEquals("http://localhost:8080/valoraciones", driver.getCurrentUrl());
        var valoracionGuardada = valoracionRepository.findAll().get(0);
        assertEquals("Excelente película", valoracionGuardada.getComentario());
        assertEquals(9, valoracionGuardada.getPuntuacion());
        assertEquals("Ana Perez", valoracionGuardada.getCustomer().getNombre() + " " + valoracionGuardada.getCustomer().getApellido());
    }




}
