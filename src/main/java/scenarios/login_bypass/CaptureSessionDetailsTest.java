package scenarios.login_bypass;

import scenarios.DriverConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

public class CaptureSessionDetailsTest {

    private WebDriver driver;
    private JavascriptExecutor js;

    private static final String FILE_NAME = "nopcommerce";
    private static final String JSON_FILE_PATH =
            System.getProperty("user.dir")
                    + "/src/main/java/com/qa/selenium/scenarios/login_bypass/"
                    + FILE_NAME + ".json";

    @BeforeMethod
    public void setUp() {
        driver = DriverConfiguration.browserSetup();
        js = (JavascriptExecutor) driver;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test(priority = 1)
    public void testCaptureSessionDetails() throws IOException {

        driver.get("https://admin-demo.nopcommerce.com/");

        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys("admin@yourstore.com");

        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys("admin");

        driver.findElement(By.xpath("//button[contains(@class,'login-button')]")).click();

        driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();

        storeSessionFile("admin@yourstore.com");
    }

    // -------------------------------------------------------------------------
    // Save JSON file
    // -------------------------------------------------------------------------
    public void storeSessionFile(String userName) throws IOException {

        Path path = Paths.get(JSON_FILE_PATH);

        if (Files.exists(path)) {
            Files.deleteIfExists(path);
        }

        JSONObject sessionObject = new JSONObject();
        sessionObject.put("username", userName);
        sessionObject.put("createdAt", LocalDateTime.now().toString());
        sessionObject.put("session_data", getSessionData());

        System.out.println("JSON Object - Session Data: " + sessionObject);

        writeJSONObjectToFile(sessionObject);
    }

    // -------------------------------------------------------------------------
    // Collect Session Data using JS + Selenium
    // -------------------------------------------------------------------------
    private JSONObject getSessionData() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("session_storage", getSessionStorageData());
        jsonObject.put("local_storage", getLocalStorageData());
        jsonObject.put("cookies", getCookiesData());

        return jsonObject;
    }

    // -------------------------------------------------------------------------
    // Capture Cookies
    // -------------------------------------------------------------------------
    private JSONArray getCookiesData() {
        JSONArray cookiesArray = new JSONArray();

        for (Cookie c : driver.manage().getCookies()) {

            JSONObject cookieJsonObject = new JSONObject();

            cookieJsonObject.put("name", c.getName());
            cookieJsonObject.put("value", c.getValue());
            cookieJsonObject.put("path", c.getPath());
            cookieJsonObject.put("domain", c.getDomain());
            cookieJsonObject.put("expiry", (c.getExpiry() != null) ? c.getExpiry().getTime() / 1000 : JSONObject.NULL);
            cookieJsonObject.put("isSecure", c.isSecure());
            cookieJsonObject.put("isHttpOnly", c.isHttpOnly());

            cookiesArray.put(cookieJsonObject);
        }
        return cookiesArray;
    }

    // -------------------------------------------------------------------------
    // Capture LocalStorage using JavascriptExecutor
    // -------------------------------------------------------------------------
    private JSONObject getLocalStorageData() {
        JSONObject localJson = new JSONObject();

        Long length = (Long) js.executeScript("return window.localStorage.length;");
        for (int i = 0; i < length; i++) {
            String key = (String) js.executeScript("return window.localStorage.key(arguments[0]);", i);
            String value = (String) js.executeScript("return window.localStorage.getItem(arguments[0]);", key);
            localJson.put(key, value);
        }
        return localJson;
    }

    // -------------------------------------------------------------------------
    // Capture SessionStorage using JavascriptExecutor
    // -------------------------------------------------------------------------
    private JSONObject getSessionStorageData() {
        JSONObject sessionJson = new JSONObject();

        Long length = (Long) js.executeScript("return window.sessionStorage.length;");
        for (int i = 0; i < length; i++) {
            String key = (String) js.executeScript("return window.sessionStorage.key(arguments[0]);", i);
            String value = (String) js.executeScript("return window.sessionStorage.getItem(arguments[0]);", key);
            sessionJson.put(key, value);
        }
        return sessionJson;
    }

    // -------------------------------------------------------------------------
    // Write JSON to File
    // -------------------------------------------------------------------------
    private void writeJSONObjectToFile(JSONObject sessionObject) throws IOException {
        FileWriter fileWriter = new FileWriter(JSON_FILE_PATH);
        fileWriter.write(sessionObject.toString(4));
        fileWriter.close();
        System.out.println("JSON successfully written → " + JSON_FILE_PATH);
    }
}
