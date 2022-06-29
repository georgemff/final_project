package com.final_project.final_project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginUser {

    WebDriver webDriver;

    By userNameInput = By.id("userName");
    By passwordInput = By.id("password");
    By loginBtn = By.id("login");

    public LoginUser(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void setUsername(String username) {
        webDriver.findElement(userNameInput).sendKeys(username);
    }

    public void setPassword(String password) {
        webDriver.findElement(passwordInput).sendKeys(password);
    }

    public void login() {
        webDriver.findElement(loginBtn).click();
    }
}
