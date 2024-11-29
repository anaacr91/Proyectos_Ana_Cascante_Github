package com.Selenium_Java.SeleniumTestUI.manufacturerSeleniumTest.PagePom;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManufacturerFormPage {
    public WebDriver driver;
    @FindBy(id = "h1")
    public WebElement h1;
    @FindBy(id = "id")
    public WebElement id;
    @FindBy(id = "name")
    public WebElement name;
    @FindBy(id = "description")
    public WebElement description;
    @FindBy(id = "imageUrl")
    public WebElement imageUrl;
    @FindBy(id = "year")
    public WebElement year;
    //Adress
    @FindBy(id = "street")
    public WebElement street;
    @FindBy(id = "city")
    public WebElement city;
    @FindBy(id = "state")
    public WebElement state;
    @FindBy(id = "zipcode")
    public WebElement zipcode;
    // Botones
    @FindBy(id = "saveNewButton")
    public WebElement saveNewButton;
    @FindBy(id = "saveEditButton")
    public WebElement saveEditButton;
    //driver

    public ManufacturerFormPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        //Inicializa los elementos de la página y guarda el driver para interactuar con la página y sus elementos.
        this.driver = driver;
        //guardamos el driver para poder interactuar con la página y sus elementos
    }
    public WebElement getManufacturerName(Long id) {
        return driver.findElement(By.id("name"));
    }
    public void fillInput(WebElement input, String text) {
        input.clear();//limpia el input
        input.sendKeys(text);//escribe el texto
    }
    public ManufacturerFormPage.ManufacturerCard getManufacturer(Long manufacturerId) {
        var image = driver.findElement(By.id("imageUrl"));
        var name = driver.findElement(By.id("name"));
        var descr = driver.findElement(By.id("description"));
        var year = driver.findElement(By.id("year"));
        var street = driver.findElement(By.id("street"));
        var city = driver.findElement(By.id("city"));
        var state = driver.findElement(By.id("state"));
        var zipcode = driver.findElement(By.id("zipcode"));
        var saveNewButton = driver.findElement(By.id("saveNewButton"));
        var saveEditButton = driver.findElement(By.id("saveEditButton"));
        return ManufacturerFormPage.ManufacturerCard.builder()
                .image(image).name(name).descr(descr).year(year)
                .street(street).city(city).state(state).zipcode(zipcode)
                .saveNewButton(saveNewButton).saveEditButton(saveEditButton).build();
    }
    @Builder
    @Getter
    @Setter
    //clase dentro de otra clase: especifico y concreto: agrupar cosas de esta clase
    //para indicar que solo se tiene que usar con este listpage y no se use en ningun otro sitio
    public static class ManufacturerCard {
       public WebElement image;
       public WebElement name;
       public WebElement descr;
       public WebElement year;
        public WebElement street;
        public WebElement city;
        public WebElement state;
        public WebElement zipcode;
       public WebElement saveNewButton;
       public WebElement saveEditButton;
    }
}
