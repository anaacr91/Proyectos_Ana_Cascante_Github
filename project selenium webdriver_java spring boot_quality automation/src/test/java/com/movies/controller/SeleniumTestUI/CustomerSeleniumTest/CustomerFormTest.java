package com.movies.controller.SeleniumTestUI.CustomerSeleniumTest;

import com.movies.model.Categoria;
import com.movies.model.Customer;
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

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
//@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerFormTest {
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
    @DisplayName("Test Selenium UI, titulo y cabecera de customer form")
    public void testPageTitleAndHeader() {
        customerRepository.save(Customer.builder().id(1L).nombre("Ana").apellido("c").email("ana@c.com").password("123").build());
        driver.get("http://localhost:8080/customers/new");
        driver.navigate().refresh();
        String title = driver.getTitle();
        assertEquals("Formulario de Cliente", title);
        String header = driver.findElement(By.id("customer_form_h1")).getText();
        assertEquals("Formulario Clientes", header);
    }

    @Test
    @DisplayName("test Selenium UI, rellenar y enviar formulario para cliente nuevo")
    public void testFillAndSubmitFormForNewCustomer() {
        Set<Movie> movies = new HashSet<>();
        Movie.MovieBuilder movieBuilder = Movie.builder().id(1L).name("Inception").duration(60).year(2025).rentalPricePerDay(5.00);
        Customer customer = customerRepository.save(Customer.builder().id(1L).nombre("Ana").apellido("C").email("ana.c@example.com").password("123").movies(movies).build());
        driver.get("http://localhost:8080/customers/new");
        driver.navigate().refresh();
        driver.findElement(By.id("nombre")).sendKeys("Ana");
        driver.findElement(By.id("apellido")).sendKeys("C");
        driver.findElement(By.id("email")).sendKeys("ana.c@example.com");
        driver.findElement(By.id("password")).sendKeys("123");

        driver.findElement(By.id("customer_save")).click();

    }

    @Test
    @DisplayName("test Selenium UI, rellenar y enviar formulario para cliente existente")
    public void testFillAndSubmitFormForExistingCustomer() {
        Set <Categoria> categorias = new HashSet<>();
        categorias.add(Categoria.builder().id(1L).nombre("Acción").build());
        Set<Movie> movies = new HashSet<>();
        Movie.MovieBuilder movieBuilder = Movie.builder().id(1L).name("Inception").duration(60).year(2025).rentalPricePerDay(5.00);
        for (Categoria categoria : categorias) {
            movieBuilder.categoria(categoria);
        }
        Customer customer = customerRepository.save(Customer.builder().id(1L).nombre("Ana").apellido("C").email("ana.c@example.com").password("123").movies(movies).build());
        driver.get("http://localhost:8080/customers/update/"+ customer.getId());

        WebElement nombreInput = driver.findElement(By.id("nombre"));
        nombreInput.clear();
        nombreInput.sendKeys("Jane");

        WebElement apellidoInput = driver.findElement(By.id("apellido"));
        apellidoInput.clear();
        apellidoInput.sendKeys("Doe");

        driver.findElement(By.id("customer_save")).click();

    }

    @Test
    @DisplayName("test Selenium UI, rellenar y enviar formulario para cliente existente e ir atrás")
    public void testBackButton() {
        Set <Categoria> categorias = new HashSet<>();
        categorias.add(Categoria.builder().id(1L).nombre("Acción").build());
        Set<Movie> movies = new HashSet<>();
        Movie.MovieBuilder movieBuilder = Movie.builder().id(1L).name("Inception").duration(60).year(2025).rentalPricePerDay(5.00);
        for (Categoria categoria : categorias) {
            movieBuilder.categoria(categoria);
        }
        Customer customer = customerRepository.save(Customer.builder().id(1L).nombre("Ana").apellido("C").email("ana.c@example.com").password("123").movies(movies).build());
        driver.get("http://localhost:8080/customers/update/"+customer.getId());
        WebElement backButton = driver.findElement(By.id("backBtn_customer_list"));
        assertTrue(backButton.isDisplayed());
        assertEquals("Volver atrás", backButton.getText());
        backButton.click();
        assertEquals("http://localhost:8080/customers", driver.getCurrentUrl());
    }


}
