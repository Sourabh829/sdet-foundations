package com.herokuapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private static final String BASE_URL = "https://the-internet.herokuapp.com/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By formAuthenticationLink = By.linkText("Form Authentication");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public HomePage open() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.titleContains("The Internet"));
        return this;
    }

    public LoginPage openFormAuthentication() {
        wait.until(ExpectedConditions.elementToBeClickable(formAuthenticationLink)).click();
        wait.until(ExpectedConditions.urlContains("/login"));
        return new LoginPage(driver, wait);
    }
}
