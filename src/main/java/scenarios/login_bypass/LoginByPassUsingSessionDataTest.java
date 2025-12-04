package scenarios.login_bypass;

import scenarios.DriverConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class LoginByPassUsingSessionDataTest {

    private WebDriver driver;
    private StorageUtil storage;

    private static final String FILE_NAME = "nopcommerce";
    private static final String JSON_FILE_PATH =
            System.getProperty("user.dir")
                    + "/src/main/java/com/qa/selenium/scenarios/login_bypass/"
                    + FILE_NAME + ".json";

    @BeforeMethod
    public void setUp() {
        driver = DriverConfiguration.browserSetup();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testLoginByPassUsingSessionData() {

        storage = new StorageUtil(driver);

        // 1️⃣ Load login page
        driver.get("https://admin-demo.nopcommerce.com/");

        // 2️⃣ Read saved JSON
        JSONObject json = parseJSONFile();
        JSONObject sessionData = json.getJSONObject("session_data");

        // 3️⃣ Apply session data
        applySessionDetails(sessionData);

        // 4️⃣ Refresh after restore
        driver.navigate().refresh();

        // 5️⃣ Validate
        driver.navigate().to("https://admin-demo.nopcommerce.com/admin/");
        driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();
    }

    private void applySessionDetails(JSONObject sessionData) {

        // Clear everything first
        driver.manage().deleteAllCookies();
        storage.clearLocalStorage();
        storage.clearSessionStorage();

        // Apply data back
        applyCookies(sessionData);
        applyLocalStorage(sessionData);
        applySessionStorage(sessionData);

        waitFor(1000);
    }

    private JSONObject parseJSONFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
            return new JSONObject(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    private void applyCookies(JSONObject sessionData) {
        JSONArray cookiesArray = sessionData.getJSONArray("cookies");

        for (int i = 0; i < cookiesArray.length(); i++) {
            JSONObject c = cookiesArray.getJSONObject(i);

            Cookie cookie = new Cookie.Builder(c.getString("name"), c.getString("value"))
                    .domain(c.getString("domain"))
                    .path(c.getString("path"))
                    .isHttpOnly(c.getBoolean("isHttpOnly"))
                    .isSecure(c.getBoolean("isSecure"))
                    .expiresOn(
                            c.has("expiry")
                                    ? new Date(c.getLong("expiry") * 1000)
                                    : null
                    )
                    .build();

            driver.manage().addCookie(cookie);
        }
    }

    private void applyLocalStorage(JSONObject sessionData) {
        JSONObject ls = sessionData.getJSONObject("local_storage");

        for (String key : ls.keySet()) {
            storage.setLocalStorageItem(key, ls.get(key).toString());
        }
    }

    private void applySessionStorage(JSONObject sessionData) {
        JSONObject ss = sessionData.getJSONObject("session_storage");

        for (String key : ss.keySet()) {
            storage.setSessionStorageItem(key, ss.get(key).toString());
        }
    }

    private void waitFor(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ignored) {
        }
    }
}
