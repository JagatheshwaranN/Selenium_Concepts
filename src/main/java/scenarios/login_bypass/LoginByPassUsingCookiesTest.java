package scenarios.login_bypass;

import scenarios.DriverConfiguration;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

public class LoginByPassUsingCookiesTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

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
    public void testLoginByPassUsingCookies() {
        // Open the specified URL in the web browser
        driver.get("https://admin-demo.nopcommerce.com/");

        // Set the cookies using the previous session details
        setCookies(previousSessionDetail());

        // Navigate to the specified URL in the web browser
        driver.navigate().to("https://admin-demo.nopcommerce.com/admin/");

        // Wait for a certain amount of time
        waitForSomeTime();

        // Check if the element with the specified XPath is displayed on the page
        driver.findElement(By.xpath("//h1[contains(text(),'Dashboard')]")).isDisplayed();
    }

    private JSONObject previousSessionDetail() {
        // Create a JSONObject for storing previous session details
        JSONObject previousSession = new JSONObject();

        // Add path information to the JSONObject
        previousSession.put("path", "/");

        // Add domain information to the JSONObject
        previousSession.put("domain", "admin-demo.nopcommerce.com");

        // Add name information to the JSONObject
        previousSession.put("name", ".Nop.Authentication");

        // Add isHttpOnly attribute to the JSONObject
        previousSession.put("isHttpOnly", true);

        // Add isSecure attribute to the JSONObject
        previousSession.put("isSecure", true);

        // Add expiry information to the JSONObject
        previousSession.put("expiry", "Tue Jul 09 16:27:34 IST 2024");

        // Add value information to the JSONObject
        previousSession.put("value",
                "CfDJ8GzHBbiRzehKoRKEgRkEcjxx2Ot0gXFOzrK0e1vQBaoPLpszfPtRBu5QgBmrNQkve2JDHkWrjt8kl_EklzA4kyQnnQJpOSmBO8Ma-5vRS9oDo7KZswTaDgDFeSPVYKFkbt219HONhp8kRsS0bMOcHTj8b92Lke8AoU5fMnYnLULaxwE8YFAWQ04NTZZgw84t4XwUx8-rwIgioaZGJVP7UhsDp99jhpkqMayNHnO6MCebZqXTyk9uTNUM1YaJW-vhRhLgAW2tikJGenhNQiIz0xMY0KDsRGXY9hAGnEz-GQCf8KBUD9vDiMVpCfTPShYHWfe_HvsKIJ42WTVUSnMgi3RV3bHl93ZjUYr7XCjb7jLHj73axqoKxdXKBha0Z3gUP4lTC6rzUx-jtUFTDZl3qdec0cgL4dwsr7PUda02Qn8Go__uYt_JGIeCubqJkthQotHzciqhQRBu9-cBx6x725eWNnVPIvR21ij5ZH0jnyZZTFU0W2IyUMBtAlh6fvHz8dXz1QOSKJxNntrzbzNs_L-rmdq8QGtE-vCC7eTrVbbYf2QxsiXahJlv3810ZkdHQQ");

        // Return the populated JSONObject
        return previousSession;

    }

    private void setCookies(JSONObject cookies) {
        System.out.println("========== Deletion of all existing cookies ===========");

        // Delete all cookies from the current session
        driver.manage().deleteAllCookies();

        // Create a new Cookie object using the Cookie.Builder
        Cookie cookie = new Cookie.Builder(cookies.get("name").toString(), cookies.get("value").toString())

                // Set the path for the cookie
                .path(cookies.get("path").toString())

                // Set the domain for the cookie
                .domain(cookies.get("domain").toString())

                // Set the expiry date for the cookie if available, else set it to null
                .expiresOn(!cookies.has("expiry") ? null : new Date(new Date().getTime() + 3600 * 1000))

                // Set the secure attribute for the cookie
                .isSecure((Boolean) cookies.get("isSecure"))

                // Set the HttpOnly attribute for the cookie
                .isHttpOnly((Boolean) cookies.get("isHttpOnly"))

                // Build the Cookie object
                .build();

        // Add the newly created cookie to the current session
        driver.manage().addCookie(cookie);

        // Print a success message to indicate that the cookies were added successfully
        System.out.println("Cookies added successfully");

        // Refresh the current page to apply the changes from the added cookie
        driver.navigate().refresh();
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
