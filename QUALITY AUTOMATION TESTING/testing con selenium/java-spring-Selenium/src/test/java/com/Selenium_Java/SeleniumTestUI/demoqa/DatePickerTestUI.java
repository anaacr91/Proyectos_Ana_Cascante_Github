package com.Selenium_Java.SeleniumTestUI.demoqa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatePickerTestUI {
    // https://demoqa.com/date-picker

    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("https://demoqa.com/date-picker");
        driver.manage().window().maximize();
    }
    @AfterEach
    void tearDown() {
        driver.quit();
    }
    @Test
    void checkDatePicker() {
    driver.findElement(By.id("datePickerMonthYearInput")).click();
    Select yearSelect = new Select(driver.findElement(By.className("react-datepicker__year-select")));
    yearSelect.selectByVisibleText("1996");
    Select monthSelect = new Select(driver.findElement(By.className("react-datepicker__month-select")));
    monthSelect.selectByVisibleText("May");
    driver.findElement(By.className("react-datepicker__day--018")).click();
    assertEquals("05/18/1996", driver.findElement(By.id("datePickerMonthYearInput")).getAttribute("value"));

    }
}
