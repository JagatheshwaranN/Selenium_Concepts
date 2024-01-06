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
import java.util.List;

public class CreateAuthenticatorAndAddNonResidentKeyTest {

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
    public void testCreateAuthenticatorAndAddNonResidentKey() {
        int expectedCredentialSet = 1;

        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
                .setProtocol(VirtualAuthenticatorOptions.Protocol.U2F)
                .setHasResidentKey(false);

        VirtualAuthenticator virtualAuthenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options);

        byte[] credentialId = {1, 2, 3, 4};
        Credential nonResidentCredential = Credential.createNonResidentCredential(
                credentialId, "localhost", ec256PrivateKey, 0);

        virtualAuthenticator.addCredential(nonResidentCredential);

        List<Credential> credentialList = virtualAuthenticator.getCredentials();
        Assert.assertEquals(credentialList.size(), expectedCredentialSet);

        Credential credential = credentialList.get(0);
        Assert.assertEquals(credential.getId(), credentialId);
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
