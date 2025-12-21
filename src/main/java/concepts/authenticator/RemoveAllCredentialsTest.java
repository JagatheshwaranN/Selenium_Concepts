package concepts.authenticator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.virtualauthenticator.Credential;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RemoveAllCredentialsTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    /**
     * A pkcs#8 encoded unencrypted EC256 private key as a base64url string.
     */
    private final static String base64EncodedEC256PK =
            "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQg8_zMDQDYAxlU-Q"
                    + "hk1Dwkf0v18GZca1DMF3SaJ9HPdmShRANCAASNYX5lyVCOZLzFZzrIKmeZ2jwU"
                    + "RmgsJYxGP__fWN_S-j5sN4tT15XEpN_7QZnt14YvI6uvAgO0uJEboFaZlOEB";

    private final static PKCS8EncodedKeySpec ec256PrivateKey =
            new PKCS8EncodedKeySpec(Base64.getUrlDecoder().decode(base64EncodedEC256PK));

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.browserSetup();
    }

    @Test
    public void testRemoveAllCredentials() {

        // Expected number of credentials after removing all entries
        int expectedCredential = 0;

        // Configure virtual authenticator to use U2F protocol
        // Resident keys are disabled for this scenario
        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
                .setProtocol(VirtualAuthenticatorOptions.Protocol.U2F)
                .setHasResidentKey(false);

        // Add a virtual authenticator to the current browser session
        VirtualAuthenticator virtualAuthenticator =
                ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options);

        // Define a mock credential ID
        byte[] credentialId = {1, 2, 3, 4};

        // Create a non-resident credential for testing
        Credential nonResidentCredential = Credential.createNonResidentCredential(
                credentialId,
                "localhost",          // Relying Party ID
                ec256PrivateKey,      // Private key used for signing
                0                     // Initial signature counter
        );

        // Add the credential to the virtual authenticator
        virtualAuthenticator.addCredential(nonResidentCredential);

        // Remove all credentials associated with the virtual authenticator
        virtualAuthenticator.removeAllCredentials();

        // Verify that no credentials remain after cleanup
        Assert.assertEquals(
                virtualAuthenticator.getCredentials().size(),
                expectedCredential
        );
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
