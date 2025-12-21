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

@SuppressWarnings("ALL")
public class GetCredentialTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    /**
     * A pkcs#8 encoded encrypted RSA private key as a base64url string.
     */
    private final static String base64EncodedRsaPK =
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDbBOu5Lhs4vpowbCnmCyLUpIE7JM9sm9QXzye2G+jr+Kr"
                    + "MsinWohEce47BFPJlTaDzHSvOW2eeunBO89ZcvvVc8RLz4qyQ8rO98xS1jtgqi1NcBPETDrtzthODu/gd0sjB2Tk3TLuB"
                    + "GVoPXt54a+Oo4JbBJ6h3s0+5eAfGplCbSNq6hN3Jh9YOTw5ZA6GCEy5l8zBaOgjXytd2v2OdSVoEDNiNQRkjJd2rmS2oi"
                    + "9AyQFR3B7BrPSiDlCcITZFOWgLF5C31Wp/PSHwQhlnh7/6YhnE2y9tzsUvzx0wJXrBADW13+oMxrneDK3WGbxTNYgIi1P"
                    + "vSqXlqGjHtCK+R2QkXAgMBAAECggEAVc6bu7VAnP6v0gDOeX4razv4FX/adCao9ZsHZ+WPX8PQxtmWYqykH5CY4TSfsui"
                    + "zAgyPuQ0+j4Vjssr9VODLqFoanspT6YXsvaKanncUYbasNgUJnfnLnw3an2XpU2XdmXTNYckCPRX9nsAAURWT3/n9ljc/"
                    + "XYY22ecYxM8sDWnHu2uKZ1B7M3X60bQYL5T/lVXkKdD6xgSNLeP4AkRx0H4egaop68hoW8FIwmDPVWYVAvo8etzWCtib"
                    + "RXz5FcNld9MgD/Ai7ycKy4Q1KhX5GBFI79MVVaHkSQfxPHpr7/XcmpQOEAr+BMPon4s4vnKqAGdGB3j/E3d/+4F2swyko"
                    + "QKBgQD8hCsp6FIQ5umJlk9/j/nGsMl85LgLaNVYpWlPRKPc54YNumtvj5vx1BG+zMbT7qIE3nmUPTCHP7qb5ERZG4CdMC"
                    + "S6S64/qzZEqijLCqepwj6j4fV5SyPWEcpxf6ehNdmcfgzVB3Wolfwh1ydhx/96L1jHJcTKchdJJzlfTvq8wwKBgQDeCnK"
                    + "ws1t5GapfE1rmC/h4olL2qZTth9oQmbrXYohVnoqNFslDa43ePZwL9Jmd9kYb0axOTNMmyrP0NTj41uCfgDS0cJnNTc63"
                    + "ojKjegxHIyYDKRZNVUR/dxAYB/vPfBYZUS7M89pO6LLsHhzS3qpu3/hppo/Uc/AM /r8PSflNHQKBgDnWgBh6OQncChPUl"
                    + "OLv9FMZPR1ZOfqLCYrjYEqiuzGm6iKM13zXFO4AGAxu1P/IAd5BovFcTpg79Z8tWqZaUUwvscnl+cRlj+mMXAmdqCeO8V"
                    + "ASOmqM1ml667axeZDIR867ZG8K5V029Wg+4qtX5uFypNAAi6GfHkxIKrD04yOHAoGACdh4wXESi0oiDdkz3KOHPwIjn6B"
                    + "hZC7z8mx+pnJODU3cYukxv3WTctlUhAsyjJiQ/0bK1yX87ulqFVgO0Knmh+wNajrb9wiONAJTMICG7tiWJOm7fW5cfTJw"
                    + "WkBwYADmkfTRmHDvqzQSSvoC2S7aa9QulbC3C/qgGFNrcWgcT9kCgYAZTa1P9bFCDU7hJc2mHwJwAW7/FQKEJg8SL33KI"
                    + "NpLwcR8fqaYOdAHWWz636osVEqosRrHzJOGpf9x2RSWzQJ+dq8+6fACgfFZOVpN644+sAHfNPAI/gnNKU5OfUv+eav8fB"
                    + "nzlf1A3y3GIkyMyzFN3DE7e0n/lyqxE4HBYGpI8g==";

    private final static PKCS8EncodedKeySpec rsaPrivateKey =
            new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(base64EncodedRsaPK));

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.browserSetup();
    }

    @Test
    public void testGetCredential() {

        // Expected number of credentials stored in the virtual authenticator
        int expectedCredentialSet = 1;

        // Configure virtual authenticator to support resident keys and user verification
        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
                .setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2) // WebAuthn CTAP2 protocol
                .setHasResidentKey(true)                                  // Enable resident (discoverable) credentials
                .setHasUserVerification(true)                             // Authenticator supports user verification
                .setIsUserVerified(true);                                 // User is already verified

        // Add a virtual authenticator to the current browser session
        VirtualAuthenticator virtualAuthenticator =
                ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options);

        // Define credential ID and user handle
        byte[] credentialId = {1, 2, 3, 4};
        byte[] userHandle = {1};

        // Create a resident credential bound to the relying party
        Credential residentCredential = Credential.createResidentCredential(
                credentialId,
                "localhost",        // Relying Party ID
                rsaPrivateKey,      // RSA private key for signing assertions
                userHandle,         // User handle associated with the credential
                0                   // Initial signature counter
        );

        // Add the resident credential to the virtual authenticator
        virtualAuthenticator.addCredential(residentCredential);

        // Retrieve all credentials stored in the virtual authenticator
        List<Credential> credentialList = virtualAuthenticator.getCredentials();

        // Validate that exactly one credential exists
        Assert.assertEquals(credentialList.size(), expectedCredentialSet);

        // Retrieve the stored credential and validate its properties
        Credential credential = credentialList.getFirst();
        Assert.assertEquals(credential.getId(), credentialId);
        Assert.assertEquals(
                rsaPrivateKey.getEncoded(),
                credential.getPrivateKey().getEncoded()
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
