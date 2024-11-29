package com.Selenium_Java.SeleniumTestUI.manufacturerSeleniumTest.PagePom;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/*
POM de Manufacturer List, es decir, esta clase tiene todos los WebElement importantes
que se van a testear en ManufacturerList
 */
public class ManufacturerListPage {
    private WebDriver driver;

    @FindBy(tagName = "h1")
    public WebElement h1;
    @FindBy(id = "createButton")
    public WebElement createButton;
    @FindBy(id = "manufacturersTable")
    public WebElement manufacturersTable;
    @FindBy(id = "manufacturersEmpty")
    public WebElement manufacturersEmpty;

    public ManufacturerListPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        //Pagefactory es una clase que inicializa los elementos de la página
        this.driver = driver;//guardamos el driver para poder interactuar con la página y sus elementos
    }

    public List<WebElement> getManufacturerNames() {
        return driver.findElements(By.cssSelector("[id^='manufacturerName_']"));
    ///id^->cualquier cosa que empiece por manufacturer name-> relacionado con data-text en el html
    }

    public WebElement getManufacturerName(Long id) {
        return driver.findElement(By.id("manufacturerName_" + id));
    }

    public void clickViewButton(Long manufacturerId) {
        driver.findElement(
                By.id("manufacturerActionView_" + manufacturerId)
        ).click();
    }

    public ManufacturerCard getManufacturer(Long manufacturerId) {
        var image = driver.findElement(By.id("manufacturerImageUrl_" + manufacturerId));
        var name = driver.findElement(By.id("manufacturerName_" + manufacturerId));
        var descr = driver.findElement(By.id("manufacturerDescription_" + manufacturerId));
        var year = driver.findElement(By.id("manufacturerYear_" + manufacturerId));
        var viewButton = driver.findElement(By.id("manufacturerActionView_" + manufacturerId));
        var editButton = driver.findElement(By.id("manufacturerActionEdit_" + manufacturerId));
        var deleteButton = driver.findElement(By.id("manufacturerActionDelete_" + manufacturerId));
        return ManufacturerCard.builder()
                .image(image).name(name).descr(descr).year(year).viewButton(viewButton)
                .editButton(editButton).deleteButton(deleteButton).build();
    }
    @Builder
    @Getter
    @Setter
    //clase dentro de otra clase: especifico y concreto: agrupar cosas de esta clase
    //para indicar que solo se tiene que usar con este listpage y no se use en ningun otro sitio
    public static class ManufacturerCard {
     public   WebElement image;
     public  WebElement name;
     public  WebElement descr;
     public  WebElement year;
     public  WebElement viewButton;
     public   WebElement editButton;
     public  WebElement deleteButton;
    }

}






    // Atributos: WebElement concretos de la página
    // botón crear
    // tabla
    // emptyList

    // métodos: acciones concretas
    // clic en crear
    // devolver boolean de si tabla está visible
    // devolver boolean de si emptyList está visible
    // obtener el número de productos
    // ver producto
    // editar producto
    // borrar producto

