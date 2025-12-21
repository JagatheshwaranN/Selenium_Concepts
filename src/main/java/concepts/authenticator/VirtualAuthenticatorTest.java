package concepts.authenticator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;

public class VirtualAuthenticatorTest {

    // WebDriver instance used to control the browser
    private WebDriver driver;

    // Explicit wait timeout used across the test
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    @BeforeMethod
    public void setUp() {
        // Initialize browser using centralized driver configuration
        driver = DriverConfiguration.browserSetup();
    }

    @Test
    public void testVirtualAuthenticator() {

        // Configure and attach a virtual WebAuthn authenticator to the browser
        setupAuthenticator();

        // Navigate to WebAuthn demo application
        driver.get("https://webauthn.io/");

        // Enter a username for registration
        driver.findElement(By.id("input-email")).sendKeys("TestAutomation06");

        // Click Register button to create a new WebAuthn credential
        driver.findElement(By.id("register-button")).click();

        // Temporary static wait to allow browser-native WebAuthn flow to complete
        // (Can be replaced with a smarter wait if application exposes a reliable signal)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Click Login button to authenticate using the virtual authenticator
        driver.findElement(By.id("login-button")).click();

        // Wait until successful login message is displayed
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='col-lg-12']//h3[contains(text(),'logged in')]")
        ));

        // Final assertion to confirm successful authentication
        driver.findElement(
                By.xpath("//div[@class='col-lg-12']//h3[contains(text(),'logged in')]")
        ).isDisplayed();
    }

    public void setupAuthenticator() {

        // Create virtual authenticator configuration
        VirtualAuthenticatorOptions virtualAuthenticatorOptions = new VirtualAuthenticatorOptions();

        // Configure authenticator properties to simulate a real platform authenticator
        virtualAuthenticatorOptions
                .setTransport(VirtualAuthenticatorOptions.Transport.INTERNAL) // Platform authenticator
                .setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2)       // WebAuthn protocol
                .setHasUserVerification(true)                                  // Supports user verification
                .setIsUserVerified(true);                                      // User is already verified

        // Attach the virtual authenticator to the current browser session
        ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(virtualAuthenticatorOptions);
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser and clean up resources after each test
        if (driver != null) {
            driver.quit();
        }
    }
}
