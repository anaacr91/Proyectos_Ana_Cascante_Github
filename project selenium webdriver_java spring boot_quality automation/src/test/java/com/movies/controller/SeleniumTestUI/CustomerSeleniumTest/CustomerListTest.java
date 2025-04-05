package com.movies.controller.SeleniumTestUI.CustomerSeleniumTest;

import com.movies.model.Customer;
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

import static org.junit.jupiter.api.Assertions.*;
//@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerListTest {
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
    @DisplayName("Test Selenium UI, Customer List Page Title, Header and Create Button")
    public void testCustomerListPageTitleAndHeader() {
        customerRepository.save(Customer.builder().id(1L).nombre("Ana").apellido("C").email("ana.c@example.com").password("123").build());
        driver.get("http://localhost:8080/customers");
        driver.navigate().refresh();
        String title =driver.getTitle();
        assertEquals("Lista de Clientes", title);
        String header = driver.findElement(By.id("h1_customer_list")).getText();
        assertEquals("Lista de Clientes", header);
    }

    @Test
    @DisplayName("Test Selenium UI, Crear cliente nuevo con boton crear")
    public void testCreateNewCustomerButton() {
        customerRepository.save(Customer.builder().id(1L).nombre("Ana").apellido("C").email("ana.c@example.com").password("123").build());
        driver.get("http://localhost:8080/customers");
        driver.navigate().refresh();
        WebElement createButton = driver.findElement(By.id("btnCreate_customer"));
        assertTrue(createButton.isDisplayed());
        assertEquals("Nuevo Cliente", createButton.getText());

        String createButtonHref = createButton.getAttribute("href");
        assertTrue(createButtonHref.endsWith("/customers/new"));
    }

    @Test
    @DisplayName("Test Selenium UI, contenido de la tabla de clientes")
    public void testCustomerTableContent() {
        customerRepository.save(Customer.builder().id(1L).nombre("Ana").apellido("C").email("ana.c@example.com").password("123").build());
        driver.get("http://localhost:8080/customers");
        driver.navigate().refresh();
        WebElement customerTable = driver.findElement(By.id("customer_list_table"));
        assertTrue(customerTable.isDisplayed());

        List<WebElement> headers = customerTable.findElements(By.tagName("th"));
        assertEquals(6, headers.size());
        assertEquals("ID", headers.get(0).getText());
        assertEquals("Nombre", headers.get(1).getText());
        assertEquals("Apellido", headers.get(2).getText());
        assertEquals("Email", headers.get(3).getText());
        assertEquals("Password", headers.get(4).getText());
        assertEquals("Acciones", headers.get(5).getText());

        List<WebElement> rows = customerTable.findElements(By.tagName("tr"));
        assertTrue(rows.size() > 1);
    }

    @Test
    @DisplayName("Test Selenium UI, acciones de cliente")
    public void testCustomerActions() {
        customerRepository.save(Customer.builder().id(1L).nombre("Ana").apellido("C").email("ana.c@example.com").password("123").build());
        driver.get("http://localhost:8080/customers");
        driver.navigate().refresh();
        List<WebElement> actionButtons = driver.findElements(By.cssSelector("td a"));

        for (WebElement button : actionButtons) {
            String buttonId = button.getAttribute("id");
            assertTrue(buttonId.equals("customer_view") || buttonId.equals("customer_update") || buttonId.equals("customer_delete"));
            assertTrue(button.isDisplayed());
        }

        WebElement viewButton = driver.findElement(By.id("customer_view"));
        String viewHref = viewButton.getAttribute("href");
        assertTrue(viewHref.contains("/customers/"));

        WebElement editButton = driver.findElement(By.id("customer_update"));
        String editHref = editButton.getAttribute("href");
        assertTrue(editHref.contains("/customers/update/"));

        WebElement deleteButton = driver.findElement(By.id("customer_delete"));
        String deleteHref = deleteButton.getAttribute("href");
        assertTrue(deleteHref.contains("/customers/delete/"));
    }

    @Test
    @DisplayName("Test Selenium UI, mensaje de no hay clientes")
    public void testNoCustomersMessage() {
        customerRepository.save(Customer.builder().id(1L).nombre("Ana").apellido("C").email("ana.c@example.com").password("123").build());
        driver.get("http://localhost:8080/customers");
        driver.navigate().refresh();
        List<WebElement> noCustomersMessage = driver.findElements(By.id("noCustomers"));
        if (!noCustomersMessage.isEmpty()) {
            assertTrue(noCustomersMessage.get(0).isDisplayed());
            assertEquals("No hay clientes.", noCustomersMessage.get(0).getText());
        }
    }
}
