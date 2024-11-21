package com.Selenium_Java.SeleniumTestUI.productSeleniumTest;

import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.model.Product;
import com.Selenium_Java.repository.ManufacturerRepository;
import com.Selenium_Java.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/*
Test de selenium para probar: product-detail.html
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductDetailTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    WebDriver driver;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();//borra todos los productos
        driver = new ChromeDriver();//crea un nuevo driver
    }
    @AfterEach
    void tearDown() {
        driver.quit();//cierra el driver
    }

    @Test
    @DisplayName("Comprobar producto OK con todos los datos correctos")
    void productExistWithAllDetails() {
        Manufacturer manufacturer = manufacturerRepository.save(Manufacturer.builder().name("fabricante 1").year(2024).build());
        Product product = productRepository.save(Product.builder().name("prod1").price(40.43).quantity(3).active(true).manufacturer(manufacturer).build());
        //crear y guardar producto y manufacturer
        // navegar al product-detail
        driver.get("http://localhost:8080/productos/" + product.getId());

        WebElement h1 = driver.findElement(By.tagName("h1"));//encuentra el elemento h1
        assertEquals("Detalle producto " + product.getId(), h1.getText());
        //comprueba que el texto del elemento h1 sea igual al esperado

        assertEquals("prod1", driver.findElement(By.id("productTitle")).getText());
        //comprueba que el texto del elemento con id productTitle sea igual al esperado
        assertEquals(
                product.getId().toString(),
                driver.findElement(By.id("product-id")).getText()
        );//comprueba que el texto del elemento con id product-id sea igual al esperado

        assertEquals(
                product.getPrice().toString(),
                driver.findElement(By.id("product-price")).getText()
        );

        WebElement active = driver.findElement(By.id("product-active"));//encuentra el elemento con id product-active
        assertEquals("Disponible", active.getText());//comprueba que el texto del elemento sea igual al esperado
        assertEquals("rgba(0, 128, 0, 1)", active.getCssValue("color"));//comprueba que el color del elemento sea igual al esperado
        WebElement manufacturerLink = driver.findElement(By.id("manufacturer-link"));//encuentra el elemento con id manufacturer-link
        assertEquals("http://localhost:8080/manufacturers/" + manufacturer.getId(),
                manufacturerLink.getAttribute("href"));//comprueba que el atributo href del elemento sea igual al esperado
        assertEquals("fabricante 1", manufacturerLink.getText());//comprueba que el texto del elemento sea igual al esperado

    }
    @Test
    @DisplayName("Comprobar active false y manufacturer null")
    void checkFalseAndNullValues() {
            Product product = productRepository.save(Product.builder().name("prod1").price(40.43).quantity(3).active(false).build());
            //crea y guarda producto
            driver.get("http://localhost:8080/productos/" + product.getId());//ve a la url del producto

            // active
            WebElement active = driver.findElement(By.id("product-active"));//encuentra el elemento con id product-active
            assertEquals("No disponible", active.getText());//comprueba que el texto del elemento sea igual al esperado
            assertEquals("rgba(255, 0, 0, 1)", active.getCssValue("color"));
            //comprueba que el color del elemento sea igual al esperado

            // manufacturer
        WebElement manufacturerEmpty = driver.findElement(By.id("manufacturerEmpty"));//encuentra el elemento con id manufacturerEmpty
        assertEquals("Sin fabricante", manufacturerEmpty.getText());//comprueba que el texto del elemento sea igual al esperado
        }

        @Test
    @DisplayName("comprobar accion editar, borrar, volver")
    void actionButtons(){
            Product product = productRepository.save(Product.builder().name("prod1").price(40.43).quantity(3).active(false).build());
            //crea y guarda producto
            driver.get("http://localhost:8080/productos/" + product.getId());//ve a la url del producto

            driver.navigate().refresh();//recarga la pagina

            var editBtn = driver.findElement(By.id("editButton"));//encuentra el elemento con id editButton
            assertEquals("Editar", editBtn.getText());//comprueba que el texto del boton sea igual al esperado
            assertEquals(
                    "http://localhost:8080/productos/editar/" + product.getId(),
                    editBtn.getAttribute("href")
            );//comprueba que el atributo href del boton sea igual al esperado

            editBtn.click(); // clicka boton editar
            assertEquals(
                    "http://localhost:8080/productos/editar/" + product.getId(),
                    driver.getCurrentUrl()
            );//comprueba que la url actual sea igual a la esperada
            driver.navigate().back();//vuelve a la pagina anterior
            //back button-> poner ir atras antes de borrar, porque si borramos deja de existir
            var backBtn = driver.findElement(By.id("backButton"));//encuentra el elemento con id backButton
            assertEquals("Volver a la lista", backBtn.getText());//comprueba que el texto del boton sea igual al esperado
            assertEquals(
                    "http://localhost:8080/productos",
                    backBtn.getAttribute("href")
            );//comprueba que el atributo href del boton sea igual al esperado

            backBtn.click(); // clicka boton editar
            assertEquals(
                    "http://localhost:8080/productos" ,
                    driver.getCurrentUrl()
            );//comprueba que la url actual sea igual a la esperada
            driver.navigate().back();//volver a la pantalla anterior para seguir testeando
            var deleteBtn = driver.findElement(By.id("deleteButton"));//encuentra el elemento con id deleteButton
            assertEquals("Borrar", deleteBtn.getText());//comprueba que el texto del boton sea igual al esperado
            assertEquals(
                    "http://localhost:8080/productos/borrar/" + product.getId(),
                    deleteBtn.getAttribute("href")
            );//comprueba que el atributo href del boton sea igual al esperado

            deleteBtn.click(); // clicka boton borrar
            assertEquals(
                    "http://localhost:8080/productos",
                    driver.getCurrentUrl()
            );//comprueba que la url actual sea igual a la esperada
        }
        @Test
    @DisplayName("comprobar producto no existe")
    void productoNotExist(){
        driver.get("http://localhost:8080/productos/999");//ve a la url de un producto que no existe
        assertEquals("Producto no encontrado", driver.findElement(By.tagName("h1")).getText());
        //comprueba que el texto del elemento h1 sea igual al esperado
        assertEquals("No existe el producto", driver.findElement(By.id("productEmpty")).getText());
        //comprueba que el texto del elemento con id productEmpty sea igual al esperado
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.id("editButton")));
        //comprueba lanza excepcion cuando no encuentre el elemento con id editButton
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.id("deleteButton")));
        //comprueba lanza excepcion cuando no encuentre el elemento con id deleteButton

        }
    }



