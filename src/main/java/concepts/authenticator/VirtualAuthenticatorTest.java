package concepts.authenticator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class VirtualAuthenticatorTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    // Define a constant duration for the maximum wait time, set to 10 seconds
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.browserSetup();
    }

    @Test
    public void testVirtualAuthenticator() {
        VirtualAuthenticator authenticator = setupAuthenticator();

        driver.get("https://webauthn.io/");

        driver.findElement(By.id("input-email")).sendKeys("TestAutomation06");

        driver.findElement(By.id("register-button")).click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.id("login-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-lg-12']//h3[contains(text(),'logged in')]")));

        driver.findElement(By.xpath("//div[@class='col-lg-12']//h3[contains(text(),'logged in')]")).isDisplayed();
    }

    public VirtualAuthenticator setupAuthenticator() {
        VirtualAuthenticatorOptions virtualAuthenticatorOptions = new VirtualAuthenticatorOptions();
        virtualAuthenticatorOptions.setTransport(VirtualAuthenticatorOptions.Transport.INTERNAL)
                .setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2)
                .setHasUserVerification(true)
                .setIsUserVerified(true);
        return ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(virtualAuthenticatorOptions);
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

}
