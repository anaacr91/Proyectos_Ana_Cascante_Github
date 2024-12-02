package com.Selenium_Java.SeleniumTestUI.Book;

import com.Selenium_Java.model.Book;
import com.Selenium_Java.model.Category;
import com.Selenium_Java.repository.BookRepository;
import com.Selenium_Java.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//puerto random
public class BookFormTestUI {

    //obtiene el puerto aleatorio
    @LocalServerPort
    int port;

    WebDriver driver;

    @Autowired
    BookRepository bookRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAllInBatch();//borra todos los registros bbdd
        categoryRepository.deleteAllInBatch();

        var categories = categoryRepository.saveAll(List.of(
                Category.builder().name("cat1").build(),
                Category.builder().name("cat2").build(),
                Category.builder().name("cat3").build(),
                Category.builder().name("cat4").build()
        ));//crea y guarda las categorias
        Book book1 = new Book();
        book1.setTitle("book1");
        book1.setPrice(20d);
        book1.getCategories().add(categories.get(0));
        book1.getCategories().add(categories.get(2));
        bookRepository.save(book1);//guarda el libro creado
        Book.builder().title("book2").price(30d).categories(Set.of(categories.get(1), categories.get(3))).build();
        //agregamos categorias con Set.of porque es un HashSet (List.of seria para List)

        driver = new ChromeDriver();
        driver.get("http://localhost:" + port + "/productos");//abre el navegador
        driver.manage().window().maximize();//maximiza la ventana, modo pc
    }
    @AfterEach
    void tearDown() {//creado con generate
        driver.quit();//cierra el navegador
    }
    @Test
    @DisplayName("Entrar a book-form y crear un nuevo libro y guardarlo")
    void bookFormEmpty() {
        driver.get("http://localhost:" + port + "/libros/crear");
        assertEquals("Crear Libro", driver.findElement(By.tagName("h1")).getText());
        // rellenar el formulario
        driver.findElement(By.id("title")).sendKeys("Libro Selenium");
        driver.findElement(By.id("price")).sendKeys("44,12");
        Select categoriesSelect = new Select(driver.findElement(By.id("categories")));
        List<WebElement> categories = categoriesSelect.getOptions();
        assertEquals(4, categories.size());
        //forma 1
        categories.forEach(category-> assertTrue(category.getText().startsWith("cat")));
    // comprobar que las categories tienen un text que empieza por "cat": cat1, cat2, cat3, cat4
        /*forma2
        for (WebElement category : categories) {
            assertTrue(category.getText().startsWith("cat"));
        }
        */
        /*forma3
        categories.stream().map(c-> c.getText()).forEach(text-> assertTrue(text.startsWith("cat")));
        * */
        categoriesSelect.selectByVisibleText("cat1");
        categoriesSelect.selectByVisibleText("cat4");

        var selectedCategories = categoriesSelect.getAllSelectedOptions();
        assertEquals(2, selectedCategories.size());
        assertEquals("cat1", selectedCategories.get(0).getText());
        assertEquals("cat4", selectedCategories.get(1).getText());

        driver.findElement(By.id("btnSend")).click();

        assertEquals("http://localhost:" + port + "/libros", driver.getCurrentUrl());
        // buscar el libro en base de datos y comprobar la creación
        Book savedBook = bookRepository.findBookEagerByTitle("Libro Selenium").orElseThrow();
        assertEquals("Libro Selenium", savedBook.getTitle());
        assertEquals(44.12, savedBook.getPrice());
        assertEquals(2, savedBook.getCategories().size());
        List<String> categoryNames = savedBook.getCategories().stream().map(c->c.getName()).toList();
        //verifica que el libro creado tiene el titulo, precio y categorias correctas
        //el metodo stream() convierte la lista en un stream
        //el metodo map() transforma cada categoria en su nombre
        //el metodo toList() convierte el stream en una lista
        //sobre la lista de strings comprueba los nombres de las categorias
        assertTrue(categoryNames.contains("cat1"));
        assertTrue(categoryNames.contains("cat4"));
    }
    @Test
    @DisplayName("Comprobar que en edición las categorías están preseleccionadas correctamente")
    void checkPreFilledCategoriesSelector() {
        Book book = bookRepository.findBookEagerByTitle("book1").orElseThrow();
        driver.get("http://localhost:" + port + "/libros/editar/" + book.getId());

        // comprobar que aparecen seleccionadas 2 categorías
        Select categoriesSelect = new Select(driver.findElement(By.id("categories")));
        var selectedCategories = categoriesSelect.getAllSelectedOptions();
        //getAllSelectedOptions() devuelve una lista de elementos seleccionados
        assertEquals(2, selectedCategories.size());
        assertEquals("cat1", selectedCategories.get(0).getText());
        assertEquals("cat3", selectedCategories.get(1).getText());
        /*
        Hemos comprobado que quitando th:selected Thymeleaf es capaz de preseleccionar bien las categorías
         */
    }




}
