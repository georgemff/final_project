package com.final_project.final_project;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.reporters.jq.Main;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

// https://www.jetbrains.com/
public class MainPage {

    WebDriver webDriver;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    By userNameValue = By.id("userName-value");
    By gotoStore = By.id("gotoStore");
    By uDontKnowJsYet = By.id("see-book-You Don't Know JS");
    By addNewRecordButton = By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div[2]/div[9]/div[2]/button");
    By profileItem = By.xpath("/html/body/div[2]/div/div/div[2]/div[1]/div/div/div[6]/div/ul/li[3]");

    public String getUsername() {
        return webDriver.findElement(userNameValue).getText();
    }

    public void goToBookStore() {
        scrollToBottom();
        webDriver.findElement(gotoStore).click();

    }

    public void chooseBook() {
        scrollToBottom();
        webDriver.findElement(uDontKnowJsYet).click();
    }

    public void addBookToCollection() {
        scrollToBottom();
        webDriver.findElement(addNewRecordButton).click();
    }

    public void goToProfile() {
        scrollToBottom();
        webDriver.findElement(profileItem).click();
    }

    public String getCollectionBookTitle() {
        return webDriver.findElement(By.xpath("//a[contains(text(),\"You Don't Know JS\")]")).getText();
    }

    private void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
    }
}
