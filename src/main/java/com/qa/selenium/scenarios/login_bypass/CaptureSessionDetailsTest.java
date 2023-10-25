package com.qa.selenium.scenarios.login_bypass;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class CaptureSessionDetailsTest {

    public WebDriver driver;
    public WebStorage webStorage;

    @Test(priority = 1, enabled = false)
    public void loginOnceToCaptureSessionDetails() throws IOException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        webStorage = (WebStorage) new Augmenter().augment(driver);

        driver.get("https://admin-demo.nopcommerce.com/");
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys("admin@yourstore.com");
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys("admin");
        driver.findElement(By.xpath("//button[contains(@class,'login-button')]")).click();

        driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();
        storeSessionFile("nopcommerce", "admin@yourstore.com");
    }

    public void storeSessionFile(String fileName, String userName) throws IOException {
        if (Files.exists(Paths.get(System.getProperty("user.dir") + "/" + fileName + ".json"))) {
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "/" + fileName + ".json"));
        }

        JSONObject sessionObject = new JSONObject();
        sessionObject.put("username", userName);
        sessionObject.put("createdAt", LocalDateTime.now());
        sessionObject.put("session_data", getSessionData());
        System.out.println("JSON Object - Session Data : " + sessionObject);
        writeJSONObjectToFile(sessionObject, "./" + fileName + ".json");
    }

    private JSONObject getSessionData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("session_storage", getSessionStorageData());
        jsonObject.put("local_storage", getLocalStorageData());
        jsonObject.put("cookies", getCookiesData());
        return jsonObject;
    }

    private JSONArray getCookiesData() {
        JSONArray cookies = new JSONArray();
        driver.manage().getCookies().stream().forEach(cookie -> {
            JSONObject cookieJsonObject = new JSONObject();
            cookieJsonObject.put("name", cookie.getName());
            cookieJsonObject.put("value", cookie.getValue());
            cookieJsonObject.put("path", cookie.getPath());
            cookieJsonObject.put("domain", cookie.getDomain());
            cookieJsonObject.put("expiry", cookie.getExpiry());
            cookieJsonObject.put("isSecure", cookie.isSecure());
            cookieJsonObject.put("isHttpOnly", cookie.isHttpOnly());
            cookies.put(cookieJsonObject);
        });

        return cookies;
    }

    private JSONObject getLocalStorageData() {
        LocalStorage localStorage = webStorage.getLocalStorage();
        JSONObject localStorageJsonObject = new JSONObject();
        localStorageJsonObject.keySet().stream()
                .forEach(locStore -> localStorageJsonObject.put(locStore, localStorage.getItem(locStore)));
        return localStorageJsonObject;
    }

    private JSONObject getSessionStorageData() {
        SessionStorage sessionStorage = webStorage.getSessionStorage();
        JSONObject sessionStorageJsonObject = new JSONObject();
        sessionStorageJsonObject.keySet().stream()
                .forEach(sessionStore -> sessionStorageJsonObject.put(sessionStore, sessionStorage.getItem(sessionStore)));
        return sessionStorageJsonObject;
    }

    private void writeJSONObjectToFile(JSONObject sessionObject, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(sessionObject.toString());
        fileWriter.close();
        System.out.println("JSON object successfully written into the file");
    }


}
