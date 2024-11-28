package com.Selenium_Java.SeleniumTestUI.demoqa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Test para comprobar el scroll de elementos que no están en el viewport
 */
public class CdvsScrollTest {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("https://certidevs.com/curso-selenium");
        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
// esperas a nivel global para no tener que aplicarlas manualmente
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Comprobar el scroll de la página")
    void testClickLesson() {

        // By.cssSelector("a[href*='/manufacturers/']")
//        WebElement lesson = driver.findElement(By.linkText("/tutorial-selenium-introduccion"));
//        WebElement lesson = driver.findElement(By.cssSelector("a[title='Introducción a Selenium']"));
//        assertTrue(lesson.isDisplayed());
//        lesson.click();
//        assertEquals(
//                "https://certidevs.com/tutorial-selenium-introduccion",
//                driver.getCurrentUrl()
//        );


        // Opción 1: Realizando scroll
        // el elemento no está en el viewport, es decir, está abajo, por tanto podría ser necesario hacer scroll para ubicarlo:
//        WebElement lesson = driver.findElement(By.cssSelector("a[title='Introducción a Selenium']"));
//        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true)", lesson);
//        lesson.click();
//        assertEquals(
//                "https://certidevs.com/tutorial-selenium-introduccion",
//                driver.getCurrentUrl()
//        );

        // Opción 2: Action
//        WebElement lesson = driver.findElement(By.cssSelector("a[title='Introducción a Selenium']"));
//        new Actions(driver).moveToElement(lesson).perform();
//        lesson.click();
//        assertEquals(
//                "https://certidevs.com/tutorial-selenium-introduccion",
//                driver.getCurrentUrl()
//        );

        // Opción 3: Action con waits para evitar des-sincronización de comprobar algo que todavía no haya
        // dado tiempo a que se ejecute
        WebElement lesson = driver.findElement(By.cssSelector("a[title='Introducción a Selenium']"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> {
//                    new Actions(driver).moveToElement(lesson).perform();
                    new Actions(driver).scrollToElement(lesson).perform();
                    return lesson.isDisplayed();
                });
        lesson.click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(driver ->
                        driver.getCurrentUrl().equals("https://certidevs.com/tutorial-selenium-introduccion")
                );

        assertEquals(
                "https://certidevs.com/tutorial-selenium-introduccion",
                driver.getCurrentUrl()
        );

        // POSIBLE PROBLEMA: CertiDevs tiene una segunda navbar dinámica que se muestra solo en algunos
        // casos pero puede estar interfiriendo
//        WebElement lesson = driver.findElement(By.cssSelector("a[title='Introducción a Selenium']"));
//        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(lesson));
//        lesson.click();
//        assertEquals(
//                "https://certidevs.com/tutorial-selenium-introduccion",
//                driver.getCurrentUrl()
//        );
    }
}