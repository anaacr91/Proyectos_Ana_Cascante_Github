package com.movies.controller.SeleniumTestUI.CategoriaSeleniumTest;

import com.movies.model.Categoria;
import com.movies.repository.CategoriaRepository;
import com.movies.repository.CustomerRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ValoracionRepository;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//@Disabled
//@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CategoriaListTest {
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
        categoriaRepository.save(Categoria.builder().nombre("Categoria").build());


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
    @DisplayName("Test Lista Categorias título y cabecera ")
    public void testCategoriaListPageTitleAndHeader() {
        categoriaRepository.save(Categoria.builder().nombre("Categoria").descripcion("Descripcion").build());
        driver.get("http://localhost:8080/categorias");
        driver.navigate().refresh();
        String title =driver.getTitle();
        assertEquals("Lista Categorias", title);
        String header = driver.findElement(By.id("h1_categoria_list")).getText();
        assertEquals("Lista de Categorías", header);
    }

    @Test
    @DisplayName("Test Crear nueva categoría")
    public void testCreateNewCategoriaButton() {
        categoriaRepository.save(Categoria.builder().nombre("Categoria").descripcion("Descripcion").build());
        driver.get("http://localhost:8080/categorias");
        driver.navigate().refresh();
        WebElement createButton = driver.findElement(By.id("btnCreate_categoria"));
        assertTrue(createButton.isDisplayed());
        assertEquals("Nueva Categoría", createButton.getText());

        String createButtonHref = createButton.getAttribute("href");
        assertTrue(createButtonHref.endsWith("/categorias/new"));
    }

    @Test
    @DisplayName("Test contenido de la tabla de categorías")
    public void testCategoriaTableContent() {
        categoriaRepository.save(Categoria.builder().nombre("Categoria").descripcion("Descripcion").build());
        driver.get("http://localhost:8080/categorias");
        driver.navigate().refresh();
        WebElement categoriaTable = driver.findElement(By.id("categoria_list_table"));
        assertTrue(categoriaTable.isDisplayed());

        List<WebElement> headers = categoriaTable.findElements(By.tagName("th"));
        assertEquals(4, headers.size());
        assertEquals("ID", headers.get(0).getText());
        assertEquals("Nombre", headers.get(1).getText());
        assertEquals("Descripción", headers.get(2).getText());

        List<WebElement> rows = categoriaTable.findElements(By.tagName("tr"));
        assertTrue(rows.size() > 1);
    }

    @Test
    @DisplayName("Test acciones de categoría")
    public void testCategoriaActions() {
        categoriaRepository.save(Categoria.builder().nombre("Ana").descripcion("Descripcion").build());
        driver.get("http://localhost:8080/categorias");
        driver.navigate().refresh();
        List<WebElement> actionButtons = driver.findElements(By.cssSelector("td a"));

        for (WebElement button : actionButtons) {
            String buttonId = button.getAttribute("id");
            assertTrue(buttonId.equals("categoria_view") || buttonId.equals("categoria_update") || buttonId.equals("categoria_delete"));
            assertTrue(button.isDisplayed());
        }

        WebElement viewButton = driver.findElement(By.id("categoria_view"));
        String viewHref = viewButton.getAttribute("href");
        assertTrue(viewHref.contains("/categorias/"));

        WebElement editButton = driver.findElement(By.id("categoria_update"));
        String editHref = editButton.getAttribute("href");
        assertTrue(editHref.contains("/categorias/edit/"));

        WebElement deleteButton = driver.findElement(By.id("categoria_delete"));
        String deleteHref = deleteButton.getAttribute("href");
        assertTrue(deleteHref.contains("/categorias/delete/"));
    }

    @Test
    @DisplayName("Test mensaje de no categorías")
    public void testNoCategoriasMessage() {
        categoriaRepository.save(Categoria.builder().nombre("Categoria").descripcion("Descripcion").build());
        driver.get("http://localhost:8080/categorias");
        driver.navigate().refresh();
        List<WebElement> noCategoriasMessage = driver.findElements(By.id("noCategorias"));
        if (!noCategoriasMessage.isEmpty()) {
            assertTrue(noCategoriasMessage.get(0).isDisplayed());
            assertEquals("No hay Categorias.", noCategoriasMessage.get(0).getText());
        }
    }
}
