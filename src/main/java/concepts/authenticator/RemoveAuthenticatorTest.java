package concepts.authenticator;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;


public class RemoveAuthenticatorTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.browserSetup();
    }

    @Test
    public void testRemoveAuthenticator() {
        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
                .setProtocol(VirtualAuthenticatorOptions.Protocol.U2F)
                .setHasResidentKey(false);

        VirtualAuthenticator virtualAuthenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options);

        ((HasVirtualAuthenticator)driver).removeVirtualAuthenticator(virtualAuthenticator);

        Assert.assertThrows(InvalidArgumentException.class, virtualAuthenticator::getCredentials);
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
