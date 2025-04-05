package com.movies.controller.SeleniumTestUI.CategoriaSeleniumTest;

import com.movies.model.Categoria;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.WebDriver;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@Disabled
//@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CategoriaDetailTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    WebDriver driver;

    @BeforeEach
    void setUp() {

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
    @DisplayName("Test de detalle de categoría")
    public void testCategoriaDetailPage() {
        Categoria categoria = categoriaRepository.save(Categoria.builder().nombre("Categoria").descripcion("Descripcion").build());
        driver.get("http://localhost:8080/categorias/" + categoria.getId());
        driver.navigate().refresh();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased timeout duration
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("h1_categoria_detail")));
            assertEquals("Detalle de la Categoría", header.getText());
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Element not found within the timeout period. Page source:");
            System.out.println(driver.getPageSource()); // Print the page source for debugging
            throw e;
        }

        String categoriaId = driver.findElement(By.id("categoria_id")).getText();
        assertTrue(categoriaId.matches("\\d+"), "El ID de las categorias debe ser numérico");

        String nombre = driver.findElement(By.id("nombre")).getText();
        assertEquals("Categoria", nombre);

        String descripcion = driver.findElement(By.id("descripcion")).getText();
        assertEquals("Descripcion", descripcion);

        WebElement editButton = driver.findElement(By.id("editBtn_categoria"));
        assertTrue(editButton.isDisplayed());
        assertEquals("Editar", editButton.getText());

        WebElement deleteButton = driver.findElement(By.id("deleteBtn_categoria"));
        assertTrue(deleteButton.isDisplayed());
        assertEquals("Borrar", deleteButton.getText());

        WebElement backButton = driver.findElement(By.id("backBtn_categoria_list"));
        assertTrue(backButton.isDisplayed());
        assertEquals("Volver a la lista de categorías", backButton.getText());
    }
}



