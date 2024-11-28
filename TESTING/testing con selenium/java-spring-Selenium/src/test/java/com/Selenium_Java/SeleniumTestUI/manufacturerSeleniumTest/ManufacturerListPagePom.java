package com.Selenium_Java.SeleniumTestUI.manufacturerSeleniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
POM de Manufacturer List, es decir, esta clase tiene todos los WebElement importantes
que se van a testear en ManufacturerList
 */
public class ManufacturerListPagePom {
    private WebDriver driver;

    @FindBy(tagName = "h1")
    public WebElement h1;

    @FindBy(id = "createButton")
    public WebElement createButton;

    @FindBy(id = "manufacturersTable")
    public WebElement manufacturersTable;

    @FindBy(id = "manufacturersEmpty")
    public WebElement manufacturersEmpty;

    public ManufacturerListPagePom(WebDriver driver) {
        PageFactory.initElements(driver, this);
        //Pagefactory es una clase que inicializa los elementos de la página
    }

    public void clickViewButton(Long manufacturerId) {
        driver.findElement(
                By.id("manufacturerActionView_" + manufacturerId)
        ).click();
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
}
