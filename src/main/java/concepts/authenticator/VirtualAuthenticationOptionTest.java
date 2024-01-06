package concepts.authenticator;


import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VirtualAuthenticationOptionTest {

    @Test
    public void testVirtualAuthenticationOption() {
        int expectedOptionSize = 6;
        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
                .setIsUserVerified(true)
                .setHasUserVerification(true)
                .setIsUserConsenting(true)
                .setTransport(VirtualAuthenticatorOptions.Transport.INTERNAL)
                .setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2)
                .setHasResidentKey(false);
        Assert.assertEquals(options.toMap().size(), expectedOptionSize);
    }

}
