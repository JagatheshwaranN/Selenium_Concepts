package scenarios.login_bypass;

import scenarios.DriverConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class HandleLoginByPassUsingSessionDataTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    // Declare a WebStorage instance to interact with the web browser session data.
    private WebStorage webStorage;

    // File name for the JSON file
    private static final String FILE_NAME = "nopcommerce";

    // Path for the JSON file
    private static final String JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/java/com/qa/selenium/scenarios/login_bypass/" + FILE_NAME + ".json";

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.browserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void loginByPassUsingSessionData() {
        // Augment the driver with WebStorage
        webStorage = (WebStorage) new Augmenter().augment(driver);

        // Navigate to the login page
        driver.get("https://admin-demo.nopcommerce.com/");

        // Use the previous session details for login
        usePreviousLoggedInSessionDetails();

        // Navigate to the admin page
        driver.navigate().to("https://admin-demo.nopcommerce.com/admin/");

        // Wait for some time before performing the next action
        waitForSomeTime();

        // Check if the 'Dashboard' heading is displayed
        driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();
    }

    private void usePreviousLoggedInSessionDetails() {
        // Clear all cookies from the driver's cookie storage
        driver.manage().getCookies().clear();

        // Initialize a JSONObject
        JSONObject jsonObject;

        // Parse the JSON file using the provided file path
        jsonObject = parseJSONFile();

        // Extract the "session_data" JSONObject from the main JSONObject
        JSONObject sessionData = jsonObject.getJSONObject("session_data");

        // Apply cookies to the current session using the session data
        applyCookiesToCurrentSession(sessionData);

        // Apply local storage using the session data
        applyLocalStorage(sessionData);

        // Apply session storage using the session data
        applySessionStorage(sessionData);

        // Wait for some time before continuing
        waitForSomeTime();

        // Refresh the driver's current page
        driver.navigate().refresh();
    }

    private JSONObject parseJSONFile() {
        // Initialize content as null
        String content = null;

        try {
            // Read the content of the JSON file
            content = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
        } catch (IOException ex) {
            // Print the stack trace in case of an exception
            ex.printStackTrace();
        }

        // Assert that the content is not null
        assert content != null;

        // Return the JSON object created from the content
        return new JSONObject(content);
    }

    public void applyCookiesToCurrentSession(JSONObject jsonObject) {
        // Retrieve the JSON array of cookies from the input JSON object
        JSONArray cookiesArray = jsonObject.getJSONArray("cookies");

        // Iterate through each JSON object within the cookies array
        for (int i = 0; i < cookiesArray.length(); i++) {

            // Get the individual cookie JSONObject at the current index
            JSONObject cookies = cookiesArray.getJSONObject(i);

            // Build a Cookie object using the information extracted from the JSON
            Cookie cookie = new Cookie.Builder(cookies.get("name").toString(), cookies.get("value").toString())
                    .path(cookies.get("path").toString())
                    .domain(cookies.get("domain").toString())
                    .expiresOn(!cookies.has("expiry") ? null : new Date(new Date().getTime() + 3600 * 1000))
                    .isSecure((Boolean) cookies.get("isSecure"))
                    .isHttpOnly((Boolean) cookies.get("isHttpOnly"))
                    .build();

            // Add the created Cookie to the current session in the WebDriver
            driver.manage().addCookie(cookie);
        }
    }

    private void applyLocalStorage(JSONObject sessionData) {
        // Retrieve the JSONObject for the local storage data from the sessionData object
        JSONObject localStorageObject = sessionData.getJSONObject("local_storage");

        // Iterate through each key in the localStorageObject
        localStorageObject.keySet().forEach(localStorage -> {

            // Set the local storage key-value pair for each key in the webStorage
            webStorage.getLocalStorage().setItem(localStorage, localStorageObject.get(localStorage).toString());
        });

    }

    private void applySessionStorage(JSONObject sessionData) {
        // Retrieve the JSONObject for the session storage data from the sessionData object
        JSONObject sessionStorageObject = sessionData.getJSONObject("session_storage");

        // Iterate through each key in the sessionStorageObject
        sessionStorageObject.keySet().forEach(sessionStorage -> {

            // Set the session storage key-value pair for each key in the webStorage
            webStorage.getSessionStorage().setItem(sessionStorage, sessionStorageObject.get(sessionStorage).toString());
        });

    }

    private void waitForSomeTime() {
        // Sleep the thread for 3 milliseconds
        try {
            Thread.sleep(3);
        } catch (InterruptedException ex) {
            // Print the stack trace if an InterruptedException occurs
            ex.printStackTrace();
        }
    }

}
