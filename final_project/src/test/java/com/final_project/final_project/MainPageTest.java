package com.final_project.final_project;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;


public class MainPageTest {
    WebDriver webDriver;
    MainPage mainPage;
    LoginUser loginUser;
    RequestSpecification httpRequest;
    String username = UserCredentialsGenerator.generateUsername();
    String password = UserCredentialsGenerator.generatePassword();
    JSONObject userCredentials = new JSONObject();

    @BeforeTest
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        mainPage = new MainPage(webDriver);
    }


    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/Account/v1";
        httpRequest = RestAssured.given();

        userCredentials.put("userName", username);
        userCredentials.put("password", password);
    }

    @Test(description = "register")
    @Description("register user")
    public void addUser() {
        System.out.println(userCredentials);

        httpRequest.accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(userCredentials);

        Response response = httpRequest.post("/User");
        System.out.println(response.getBody().asString());

        Assert.assertEquals(response.statusCode(), 201);

        AddUserResponse addUserResponse = response.getBody().as(AddUserResponse.class);

        Assert.assertEquals(addUserResponse.books.length, 0);

    }

    @Test(description = "generate token")
    @Description("generate token for registered user")
    @Story("success scenario")
    public void generateToken() {

        httpRequest.accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(userCredentials);

        Response response = httpRequest.post("/GenerateToken");

        Assert.assertEquals(response.statusCode(), 200);
        TokenResponse tokenResponse = response.getBody().as(TokenResponse.class);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(tokenResponse.status, "Success");
        Assert.assertEquals(tokenResponse.result, "User authorized successfully.");
    }

    @Test (dependsOnMethods = { "generateToken", "addUser" })
    @Description("check if user is authorized after generate token")
    public void checkIfUserIsAuthorized() {
        httpRequest.accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(userCredentials);

        Response response = httpRequest.post("/Authorized");

        System.out.println(response.getBody().asString());
    }

    @Test(description = "Authenticate")
    @Description("authenticate registered user")
    @Story("correct username and password")
    public void loginUser() {
        webDriver.get("https://demoqa.com/login");
        loginUser = new LoginUser(webDriver);

        loginUser.setUsername(userCredentials.get("userName").toString());
        loginUser.setPassword(userCredentials.get("password").toString());
        loginUser.login();

        waitUntil(mainPage.userNameValue);
        Assert.assertEquals(mainPage.getUsername(), userCredentials.get("userName").toString());
    }

    @Test(description = "add book to users collection")
    @Description("add book to user collection and check if book is added")
    public void validateBookCollection() throws InterruptedException {

        waitUntil(mainPage.gotoStore);
        mainPage.goToBookStore();
        waitUntil(mainPage.uDontKnowJsYet);
        mainPage.chooseBook();
        waitUntil(mainPage.addNewRecordButton);
        mainPage.addBookToCollection();
        waitForAlert();
        Alert alert = webDriver.switchTo().alert();
        Assert.assertEquals(alert.getText(), "Book added to your collection.");
        Thread.sleep(1000);
        alert.accept();

        waitUntil(mainPage.profileItem);
        mainPage.goToProfile();

        waitUntil(mainPage.userNameValue);
        Assert.assertEquals(mainPage.getCollectionBookTitle(), "You Don't Know JS");
        webDriver.quit();

    }

    public void waitUntil(By element) {
        WebDriverWait wait = new WebDriverWait(webDriver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForAlert() {
        WebDriverWait wait = new WebDriverWait(webDriver, 15);
        wait.until(ExpectedConditions.alertIsPresent());
    }

}
