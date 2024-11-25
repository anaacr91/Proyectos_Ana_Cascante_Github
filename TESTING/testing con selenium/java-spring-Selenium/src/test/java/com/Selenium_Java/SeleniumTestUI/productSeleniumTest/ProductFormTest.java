package com.Selenium_Java.SeleniumTestUI.productSeleniumTest;

import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.model.Product;
import com.Selenium_Java.repository.ManufacturerRepository;
import com.Selenium_Java.repository.ProductRepository;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductFormTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    WebDriver driver;

    @BeforeEach
    void setUp() {
        productRepository.deleteAllInBatch();//borra todos los productos de la bbdd en memoria
        manufacturerRepository.deleteAllInBatch();//borra todos los fabricantes de la bbdd en memoria
        driver = new ChromeDriver();//crea un driver de chrome
    }

    @AfterEach
    void tearDown() {
        driver.quit();//cierra el driver
    }

    @Test
    @DisplayName("Comprobar que form esta vacio de imputs, en modo crear")
    void checkCreation_EmptyInputs() {
        manufacturerRepository.saveAll(List.of(
                Manufacturer.builder().name("fabricante 1").build(),
                Manufacturer.builder().name("fabricante 2").build()
        ));//guarda dos fabricantes en la bbdd en memoria
        driver.get("http://localhost:8080/productos/crear");//navega a la url
        var h1 = driver.findElement(By.tagName("h1"));//encuentra el h1
        assertEquals("Crear producto", h1.getText());//comprueba que el texto del h1 sea Crear producto
        // comprobar inputs vacíos
        var inputName = driver.findElement(By.id("name"));//encuentra el input con id name
        assertTrue(inputName.getAttribute("value").isEmpty());//comprueba que el valor del input sea vacio
        var inputPrice = driver.findElement(By.id("price"));//encuentra el input con id price
        assertTrue(inputPrice.getAttribute("value").isEmpty());//comprueba que el valor del input sea vacio
        var inputQuantity = driver.findElement(By.id("quantity"));//encuentra el input con id quantity
        assertTrue(inputQuantity.getAttribute("value").isEmpty());//comprueba que el valor del input sea vacio
        var inputActive = driver.findElement(By.id("active"));//encuentra el input con id active
        assertEquals("true", inputActive.getAttribute("value"));//comprueba que el valor del input sea true
        //selector de manufacturer, convertimos de webElement a Select
        Select manufacturerSelect = new Select(driver.findElement(By.id("manufacturer")));//encuentra el select con id manufacturer
        assertFalse(manufacturerSelect.isMultiple());//comprueba que select sea multiple
        assertEquals(3, manufacturerSelect.getOptions().size());//comprueba que tenga 3 opciones
        //selector vacio+ options 2 fabricantes
        assertEquals("", manufacturerSelect.getOptions().get(0).getText());
        assertEquals("FABRICANTE 1", manufacturerSelect.getOptions().get(1).getText());
        //comprueba que la primera opcion sea fabricante 1;
        assertEquals("FABRICANTE 2", manufacturerSelect.getOptions().get(2).getText());
        }
    // TODO hacer many to many Book y Editorial
    // edicion formulario relleno
    // rellenar campos y enviar

    @Test
    @DisplayName("Comprobar que el formulario aparece relleno al editar un producto")
    void checkEdition_FilledInputs() {
        var manufacturers = manufacturerRepository.saveAll(List.of(
                Manufacturer.builder().name("fabricante 1").build(),
                Manufacturer.builder().name("fabricante 2").build()
        ));//guarda dos fabricantes en la bbdd en memoria
        Manufacturer manufacturer2 = manufacturers.getLast();
        Product product = Product.builder()
                .name("prod1").price(14.22).quantity(4).active(true).manufacturer(manufacturers.get(1))//fabricante2
                .build();
        productRepository.save(product);//guarda un producto en la bbdd en memoria
        driver.get("http://localhost:8080/productos/editar/" + product.getId());//navega a la url
        //comprobar imputs rellenos
        var inputName = driver.findElement(By.id("name"));//encuentra el input con id name
        assertEquals("prod1", inputName.getAttribute("value"));//comprueba que el valor del input sea prod1
        var inputPrice = driver.findElement(By.id("price"));//encuentra el input con id price
        assertEquals("14.22", inputPrice.getAttribute("value"));//comprueba que el valor del input sea 14.22
        var inputQuantity = driver.findElement(By.id("quantity"));//encuentra el input con id quantity
        assertEquals("4", inputQuantity.getAttribute("value"));//comprueba que el valor del input sea 4
        var inputActive = driver.findElement(By.id("active"));//encuentra el input con id active
        assertEquals("true", inputActive.getAttribute("value"));//comprueba que el valor del input sea true
        // selector de manufacturer, convertimos de WebElement a Select
        Select manufacturerSelect = new Select(driver.findElement(By.id("manufacturer")));
        assertFalse(manufacturerSelect.isMultiple());//comprueba que select de manufacturer sea multiple
        assertEquals(3, manufacturerSelect.getOptions().size());//num opciones seleccionables 3
        //assertEquals("fabricante 2", manufacturerSelect.getFirstSelectedOption().getText());
        assertEquals(
                String.valueOf(manufacturer2.getId()), // id del fabricante en string,
                manufacturerSelect.getFirstSelectedOption().getAttribute("value")
        );//comprueba que el valor del input sea el id del fabricante2
        //El metodo getFirstSelectedOption() devuelve el primer elemento <option> seleccionado dentro de un <select>
        //firstSelectedOption devuelve el primer elemento seleccionado
        assertEquals("fabricante 2", manufacturerSelect.getFirstSelectedOption().getText());
        //comprueba que el texto del primer elemento seleccionado sea fabricante 2
    }
    @Test
    @DisplayName("Entrar en el formulario y crear un nuevo producto y enviar")
    void crearNuevoProductoYEnviar() {
    manufacturerRepository.saveAll(List.of(
        Manufacturer.builder().name("fabricante 1").build(),
        Manufacturer.builder().name("fabricante 2").build()
        ));

        driver.get("http://localhost:8080/productos/crear");
        var inputName = driver.findElement(By.id("name"));
        inputName.sendKeys("Producto Selenium");
        //sendKeys("Producto selenium"), es un metodo de Selenium que permite simular la acción de
        // escribir texto en un elemento HTML como un campo de entrada.
        //En este caso, se está escribiendo el texto "Producto selenium" en el campo localizado por inputName.
        var inputPrice = driver.findElement(By.id("price"));
        inputPrice.sendKeys("55");
        var inputQuantity = driver.findElement(By.id("quantity"));
        inputQuantity.sendKeys("23");
        var inputActive = driver.findElement(By.id("active"));
        Select manufacturerSelect = new Select(driver.findElement(By.id("manufacturer")));
        manufacturerSelect.selectByVisibleText("FABRICANTE 2");
        //selectByVisibleText("FABRICANTE 2"):
        // Selecciona una opción en el campo desplegable cuyo texto visible sea "FABRICANTE 2".
        inputActive.click();// simular el clic de un usuario en el elemento active
        driver.findElement(By.id("btnSend")).click();//click en el boton enviar
        assertEquals("http://localhost:8080/productos", driver.getCurrentUrl());
        //comprueba que la url sea http://localhost:8080/productos
        List<WebElement> tableRows = driver.findElements(By.cssSelector("#productList tbody tr"));//encuentra las filas de la tabla
        assertEquals(1, tableRows.size()); // COMPROBAR QUE SE HA CREADO UN PRODUCTO en la tabla
        var productSaved = productRepository.findAll().getFirst();//obtiene el producto guardado en la bbdd
        assertEquals("Producto Selenium", productSaved.getName());
        assertEquals(55, productSaved.getPrice());
        assertEquals(23, productSaved.getQuantity());
        assertEquals(true, productSaved.getActive());
        assertEquals("fabricante 2", productSaved.getManufacturer().getName());
    }

    @Test
    @DisplayName("Entrar en el formulario y editar un producto existente y enviar")
    void editarProductYEnviar() {
        var manufacturers = manufacturerRepository.saveAll(List.of(
                Manufacturer.builder().name("fabricante 1").build(), // 0
                Manufacturer.builder().name("fabricante 2").build() // 1
        ));
        Manufacturer manufacturer2 = manufacturers.getLast();
        Product product = Product.builder()
                .name("prod1").price(14.22).quantity(4).active(false).manufacturer(manufacturer2) // fabricante 2
                .build();
        productRepository.save(product);
        driver.get("http://localhost:8080/productos/editar/" + product.getId());
        // modificar campos desde selenium
        var inputName = driver.findElement(By.id("name"));
        inputName.clear();
        inputName.sendKeys("prod 1 modificado");
        var inputPrice = driver.findElement(By.id("price"));
        inputPrice.clear();
        inputPrice.sendKeys("55,43");
        var inputQuantity = driver.findElement(By.id("quantity"));
        inputQuantity.clear();
        inputQuantity.sendKeys("15");
        var inputActive = driver.findElement(By.id("active"));
        inputActive.click();
        Select manufacturerSelect = new Select(driver.findElement(By.id("manufacturer")));
        manufacturerSelect.selectByVisibleText("fabricante 1");
        driver.findElement(By.id("btnSend")).click();
        // Obtener producto de base de datos y comprobar campos modificados
        assertEquals("http://localhost:8080/productos", driver.getCurrentUrl());
        var productSaved = productRepository.findAll().getFirst();
        assertEquals("prod 1 modificado", productSaved.getName());
        assertEquals(55.43, productSaved.getPrice());
        assertEquals(15, productSaved.getQuantity());
        assertEquals(true, productSaved.getActive());
        assertEquals("fabricante 1", productSaved.getManufacturer().getName());
    }

    // casos especiales: límites y validaciones: qué pasa si pongo valores erróneos en todos los campos
    //dejar todos los campos sin rellenar
    @Test
    @DisplayName("Enviar errores erróneos y verificar validaciones del formulario HTML")
    void create_invalidValues(){
        driver.get("http://localhost:8080/productos/crear");

        var inputName = driver.findElement(By.id("name"));
        inputName.sendKeys("Producto thymeleaf");

        var inputPrice = driver.findElement(By.id("price"));
        inputPrice.sendKeys("600");

        var inputQuantity = driver.findElement(By.id("quantity"));
        inputQuantity.sendKeys("30");

        driver.findElement(By.id("btnSend")).click();

        assertEquals("http://localhost:8080/productos/crear", driver.getCurrentUrl());
        assertEquals(0, productRepository.count());

    }

    @Test
    @DisplayName("Comprobar id read only no deja editarlo")
    void checkIdReadOnly() {
        Product product = Product.builder()
                .name("prod1")
                .price(14.22)
                .quantity(4)
                .active(false)
                .build();
        productRepository.save(product);
        driver.get("http://localhost:8080/productos/editar/" + product.getId());
        var inputId = driver.findElement(By.id("id"));
        assertEquals(String.valueOf(product.getId()), inputId.getAttribute("value"));
        inputId.sendKeys("3");
        assertEquals(String.valueOf(product.getId()), inputId.getAttribute("value"));
        assertThrows(InvalidElementStateException.class, () -> inputId.clear());
        //significa que se está verificando que al intentar borrar el contenido de un campo de entrada (inputId)
        // mediante el metodo clear(), se lanza una excepción de tipo InvalidElementStateException.
        //  Es decir, se espera que ese campo no sea editable y, por lo tanto, no permita borrar su contenido.
        assertEquals(String.valueOf(product.getId()), inputId.getAttribute("value"));
    }

}
