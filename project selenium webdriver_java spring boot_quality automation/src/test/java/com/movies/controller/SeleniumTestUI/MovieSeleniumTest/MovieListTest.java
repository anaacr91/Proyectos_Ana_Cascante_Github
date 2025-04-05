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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MovieListTest {

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
    @DisplayName("Test Movie List Page Titulo y cabecera")
    public void testMovieListPageTitleAndHeader() {
        movieRepository.save(Movie.builder().id(1L).name("Inception").duration(148).year(2010).rentalPricePerDay(5.00).build());
        driver.get("http://localhost:8080/movies");
        driver.navigate().refresh();
        String title = driver.getTitle();
        assertEquals("Lista de Películas", title);
        String header = driver.findElement(By.id("h1_movie_list")).getText();
        assertEquals("Lista de Películas", header);
    }

    @Test
    @DisplayName("Test Movie List Page Boton crear")
    public void testCreateNewMovieButton() {
        driver.get("http://localhost:8080/movies");
        driver.navigate().refresh();
        WebElement createButton = driver.findElement(By.id("btnCreate_movie"));
        assertTrue(createButton.isDisplayed());
        assertEquals("Nueva Película", createButton.getText());

        String createButtonHref = createButton.getAttribute("href");
        assertTrue(createButtonHref.endsWith("/movies/new"));
    }

    @Test
    @DisplayName("Test Movie Tabla contenido")
    public void testMovieTableContent() {
        Categoria categoria = categoriaRepository.save(Categoria.builder().id(1L).nombre("Acción").build());
        movieRepository.save(Movie.builder().id(1L).name("Inception").duration(148).year(2010).rentalPricePerDay(5.00).categoria(categoria).build());

        driver.get("http://localhost:8080/movies");
        driver.navigate().refresh();
        WebElement movieTable = driver.findElement(By.id("movie_list_table"));
        assertTrue(movieTable.isDisplayed());

        List<WebElement> headers = movieTable.findElements(By.tagName("th"));
        assertEquals(7, headers.size());
        assertEquals("ID", headers.get(0).getText());
        assertEquals("Nombre", headers.get(1).getText());
        assertEquals("Duración (min)", headers.get(2).getText());
        assertEquals("Año", headers.get(3).getText());
        assertEquals("Disponible", headers.get(4).getText());

        List<WebElement> rows = movieTable.findElements(By.tagName("tr"));
        assertTrue(rows.size() > 1);
    }

    @Test
    @DisplayName("Test acciones de pelicula")
    public void testMovieActions() {
        movieRepository.save(Movie.builder().id(1L).name("Inception").duration(148).year(2010).rentalPricePerDay(5.00).build());
        driver.get("http://localhost:8080/movies");
        driver.navigate().refresh();
        List<WebElement> actionButtons = driver.findElements(By.cssSelector("td a"));

        for (WebElement button : actionButtons) {
            String buttonId = button.getAttribute("id");
            assertTrue(buttonId.equals("movie_view") || buttonId.equals("movie_update") || buttonId.equals("movie_delete"));
            assertTrue(button.isDisplayed());
        }

        WebElement viewButton = driver.findElement(By.id("movie_view"));
        String viewHref = viewButton.getAttribute("href");
        assertTrue(viewHref.contains("/movies/"));

        WebElement editButton = driver.findElement(By.id("movie_update"));
        String editHref = editButton.getAttribute("href");
        assertTrue(editHref.contains("/movies/update/"));

        WebElement deleteButton = driver.findElement(By.id("movie_delete"));
        String deleteHref = deleteButton.getAttribute("href");
        assertTrue(deleteHref.contains("/movies/delete/"));
    }

    @Test
    @DisplayName("Test mensaje no hay peliculas")
    public void testNoMoviesMessage() {
        driver.get("http://localhost:8080/movies");
        driver.navigate().refresh();
        List<WebElement> noMoviesMessage = driver.findElements(By.id("noMovies"));
        if (!noMoviesMessage.isEmpty()) {
            assertTrue(noMoviesMessage.get(0).isDisplayed());
            assertEquals("No hay peliculas", noMoviesMessage.get(0).getText());
        }
    }
}

