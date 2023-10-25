package com.qa.selenium.scenarios.login_bypass;

import com.qa.selenium.scenarios.DriverConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class HandleLoginByPassUsingSessionDataTest {

    public WebDriver driver;

    public WebStorage webStorage;


    @Test(priority = 1)
    public void loginByPassUsingSessionData() {
        String fileName = "nopcommerce";
        driver = DriverConfiguration.browserSetup();
        driver.manage().window().maximize();
        webStorage = (WebStorage) new Augmenter().augment(driver);
        driver.get("https://admin-demo.nopcommerce.com/");
        usePreviousLoggedInSessionDetails(fileName);
        driver.navigate().to("https://admin-demo.nopcommerce.com/admin/");
        waitForSomeTime();
        driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();
        driver.quit();
    }

    private void usePreviousLoggedInSessionDetails(String fileName) {
        driver.manage().getCookies().clear();
        JSONObject jsonObject;
        jsonObject = parseJSONFile(System.getProperty("user.dir") + "/src/main/java/com/qa/selenium/scenarios/login_bypass/" + fileName + ".json");
        JSONObject sessionData = jsonObject.getJSONObject("session_data");
        applyCookiesToCurrentSession(sessionData);
        applyLocalStorage(sessionData);
        applySessionStorage(sessionData);
        waitForSomeTime();
        driver.navigate().refresh();
    }

    private JSONObject parseJSONFile(String fileName) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        assert content != null;
        return new JSONObject(content);
    }

    public void applyCookiesToCurrentSession(JSONObject jsonObject) {
        JSONArray cookiesArray = jsonObject.getJSONArray("cookies");
        for (int i = 0; i < cookiesArray.length(); i++) {
            JSONObject cookies = cookiesArray.getJSONObject(i);
            Cookie cookie = new Cookie.Builder(cookies.get("name").toString(), cookies.get("value").toString())
                    .path(cookies.get("path").toString()).domain(cookies.get("domain").toString())
                    .expiresOn(!cookies.has("expiry") ? null : new Date(new Date().getTime() + 3600 * 1000))
                    .isSecure((Boolean) cookies.get("isSecure")).isHttpOnly((Boolean) cookies.get("isHttpOnly"))
                    .build();
            driver.manage().addCookie(cookie);
        }
    }

    private void applyLocalStorage(JSONObject sessionData) {
        JSONObject localStorageObject = sessionData.getJSONObject("local_storage");
        localStorageObject.keySet().forEach(localStorage -> webStorage.getLocalStorage().setItem(localStorage, localStorageObject.get(localStorage).toString()));
    }

    private void applySessionStorage(JSONObject sessionData) {
        JSONObject sessionStorageObject = sessionData.getJSONObject("session_storage");
        sessionStorageObject.keySet().forEach(sessionStorage -> webStorage.getSessionStorage().setItem(sessionStorage, sessionStorageObject.get(sessionStorage).toString()));
    }

    private void waitForSomeTime() {
        try {
            Thread.sleep(3);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
