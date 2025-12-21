package concepts.authenticator;

import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VirtualAuthenticationOptionTest {

    @Test
    public void testVirtualAuthenticationOption() {

        // Expected number of configuration options set on the virtual authenticator
        int expectedOptionSize = 6;

        // Create and configure VirtualAuthenticatorOptions
        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
                .setIsUserVerified(true)                          // Simulate user already verified (e.g., biometrics passed)
                .setHasUserVerification(true)                     // Authenticator supports user verification
                .setIsUserConsenting(true)                        // User consent is granted automatically
                .setTransport(VirtualAuthenticatorOptions.Transport.INTERNAL) // Platform authenticator (built-in)
                .setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2)      // Use WebAuthn CTAP2 protocol
                .setHasResidentKey(false);                        // Resident keys are not supported

        // Convert options to a map and validate the number of configured properties
        // This ensures that all expected options are correctly applied
        Assert.assertEquals(options.toMap().size(), expectedOptionSize);
    }
}
