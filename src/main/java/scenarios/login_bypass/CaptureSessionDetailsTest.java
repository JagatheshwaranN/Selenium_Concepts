package scenarios.login_bypass;

import scenarios.DriverConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class CaptureSessionDetailsTest {

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
    public void testCaptureSessionDetails() throws IOException {
        // Augment the driver with WebStorage
        webStorage = (WebStorage) new Augmenter().augment(driver);

        // Navigating to the specified URL
        driver.get("https://admin-demo.nopcommerce.com/");

        // Clearing the text field with the ID "Email" and entering the provided email
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys("admin@yourstore.com");

        // Clearing the text field with the ID "Password" and entering the provided password
        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys("admin");

        // Clicking the button with the specified XPath
        driver.findElement(By.xpath("//button[contains(@class,'login-button')]")).click();

        // Checking if the element containing the text "Dashboard" is displayed
        driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();

        // Storing the session file with the given parameters
        storeSessionFile("admin@yourstore.com");
    }

    public void storeSessionFile(String userName) throws IOException {
        // Creating a Path instance from the JSON_FILE_PATH
        Path path = Paths.get(JSON_FILE_PATH);

        // Checking if the file exists and deleting it if it does
        if (Files.exists(path)) {
            Files.deleteIfExists(path);
        }

        // Creating a new JSONObject to store session data
        JSONObject sessionObject = new JSONObject();

        // Adding username, creation timestamp, and session data to the JSONObject
        sessionObject.put("username", userName);
        sessionObject.put("createdAt", LocalDateTime.now());
        sessionObject.put("session_data", getSessionData());

        // Printing the created JSON object containing session data
        System.out.println("JSON Object - Session Data : " + sessionObject);

        // Writing the JSON object to a file
        writeJSONObjectToFile(sessionObject);
    }

    private JSONObject getSessionData() {
        // Creating a new JSONObject to store various session data
        JSONObject jsonObject = new JSONObject();

        // Adding session storage data, local storage data, and cookies data to the JSONObject
        jsonObject.put("session_storage", getSessionStorageData());
        jsonObject.put("local_storage", getLocalStorageData());
        jsonObject.put("cookies", getCookiesData());

        // Returning the JSONObject containing session data
        return jsonObject;
    }

    private JSONArray getCookiesData() {
        // Creating a new JSONArray to store cookies
        JSONArray cookies = new JSONArray();

        // Iterating over the cookies obtained from the driver
        driver.manage().getCookies().forEach(cookie -> {

            // Creating a new JSONObject to store cookie details
            JSONObject cookieJsonObject = new JSONObject();

            // Adding cookie attributes to the JSONObject
            cookieJsonObject.put("name", cookie.getName());
            cookieJsonObject.put("value", cookie.getValue());
            cookieJsonObject.put("path", cookie.getPath());
            cookieJsonObject.put("domain", cookie.getDomain());
            cookieJsonObject.put("expiry", cookie.getExpiry());
            cookieJsonObject.put("isSecure", cookie.isSecure());
            cookieJsonObject.put("isHttpOnly", cookie.isHttpOnly());

            // Adding the cookie JSONObject to the cookies JSONArray
            cookies.put(cookieJsonObject);
        });
        // Returning the JSONArray containing cookies data
        return cookies;
    }

    private JSONObject getLocalStorageData() {
        // Accessing the local storage from the web storage
        LocalStorage localStorage = webStorage.getLocalStorage();

        // Creating a new JSONObject to store local storage data
        JSONObject localStorageJsonObject = new JSONObject();

        // Iterating over the key set of the local storage and adding the items to the JSONObject
        localStorageJsonObject.keySet().forEach(locStore -> localStorageJsonObject.put(locStore, localStorage.getItem(locStore)));

        // Returning the JSONObject containing the local storage data
        return localStorageJsonObject;
    }

    private JSONObject getSessionStorageData() {
        // Accessing the session storage from the web storage
        SessionStorage sessionStorage = webStorage.getSessionStorage();

        // Creating a new JSONObject to store session storage data
        JSONObject sessionStorageJsonObject = new JSONObject();

        // Iterating over the key set of the session storage and adding the items to the JSONObject
        sessionStorageJsonObject.keySet().forEach(sessionStore -> sessionStorageJsonObject.put(sessionStore, sessionStorage.getItem(sessionStore)));

        // Returning the JSONObject containing the session storage data
        return sessionStorageJsonObject;
    }

    private void writeJSONObjectToFile(JSONObject sessionObject) throws IOException {
        // Creating a FileWriter to write to the specified file path
        FileWriter fileWriter = new FileWriter(JSON_FILE_PATH);

        // Writing the sessionObject as a string to the file
        fileWriter.write(sessionObject.toString());

        // Closing the file writer after writing is complete
        fileWriter.close();

        // Printing a success message indicating that the JSON object was successfully written to the file
        System.out.println("JSON object successfully written into the file");
    }

}
