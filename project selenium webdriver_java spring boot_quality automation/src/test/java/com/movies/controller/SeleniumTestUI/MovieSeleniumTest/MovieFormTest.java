package com.movies.controller.SeleniumTestUI.MovieSeleniumTest;

import com.movies.model.Categoria;
import com.movies.model.Movie;
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
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MovieFormTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        valoracionRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
        categoriaRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
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
    @DisplayName("Test Movie Form Page titulo y header")
    public void testPageTitleAndHeader() {
        driver.get("http://localhost:8080/movies/new");
        driver.navigate().refresh();
        String title = driver.getTitle();
        assertEquals("Formulario películas", title);
        String header = driver.findElement(By.id("movie_form_h1")).getText();
        assertEquals("Formulario de Película", header);
    }

    @Test
    @DisplayName("Test Movie Form Page llenado y submit pelicula nueva")
    public void testFillAndSubmitFormForNewMovie() {
        Categoria categoria = categoriaRepository.save(Categoria.builder().id(1L).nombre("Acción").build());

        driver.get("http://localhost:8080/movies/new");
        driver.navigate().refresh();
        driver.findElement(By.id("name")).sendKeys("Inception");
        driver.findElement(By.id("duration")).sendKeys("148");
        driver.findElement(By.id("year")).sendKeys("2010");

        WebElement categorySelect = driver.findElement(By.id("movie_categoria"));
        categorySelect.findElement(By.xpath("//option[text()='Acción']")).click();

        driver.findElement(By.id("movie_save")).click();

    }

    @Test
    @DisplayName("Test Movie Form Page llenado y submit de pelicula existente")
    public void testFillAndSubmitFormForExistingMovie() {
        Categoria categoria = categoriaRepository.save(Categoria.builder().id(1L).nombre("Acción").build());

        Movie movie = movieRepository.save(Movie.builder().id(1L).name("Matrix").duration(136).year(1999).rentalPricePerDay(5.00).categoria(categoria).build());

        driver.get("http://localhost:8080/movies/update/" + movie.getId());
        driver.navigate().refresh();
        WebElement nameInput = driver.findElement(By.id("name"));
        nameInput.clear();
        nameInput.sendKeys("The Matrix");

        WebElement durationInput = driver.findElement(By.id("duration"));
        durationInput.clear();
        durationInput.sendKeys("150");

        WebElement yearInput = driver.findElement(By.id("year"));
        yearInput.clear();
        yearInput.sendKeys("2000");

        driver.findElement(By.id("movie_save")).click();

    }

    @Test
    @DisplayName("Test Movie Form Page accion boton atras")
    public void testBackButton() {
        driver.get("http://localhost:8080/movies/new");
        driver.navigate().refresh();
        WebElement backButton = driver.findElement(By.id("backBtn_movies_list"));
        assertTrue(backButton.isDisplayed());
        assertEquals("Volver atrás", backButton.getText());

        backButton.click();
        assertEquals("http://localhost:8080/movies", driver.getCurrentUrl());
    }
}

