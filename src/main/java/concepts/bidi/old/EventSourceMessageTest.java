package concepts.bidi.old;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.Optional;

public class EventSourceMessageTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'cdpBrowserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.cdpBrowserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    /**
     *   Event source messages - On live Websockets, this even source messages sometimes they are called as
     * 	 server sent events, or one-way messaging is in directional. The data messages are delivered in
     * 	 one direction from the server to the client (basically to the browser). We can use them when
     * 	 there is no need to send data from the client to the server in message form.
     * 	 For example, event source messages are good for handling social media status updates, news feeds,
     * 	 and delivering data into the client-side storage mechanism.
     */
    @Test(priority = 1)
    public void testEventSourceMessage() {
        // Get the DevTools instance from the driver
        DevTools devTools = driver.getDevTools();

        // Create a new DevTools session if one doesn't already exist
        devTools.createSessionIfThereIsNotOne();

        // Enable the Network domain in DevTools
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Add a listener for the eventSourceMessageReceived event in the Network domain
        devTools.addListener(Network.eventSourceMessageReceived(), message -> {

            // Print the event name received
            System.out.println(message.getEventName());

            // Print the optional event ID (if present)
            System.out.println(Optional.ofNullable(message.getEventId()));

            // Print the request ID associated with the message
            System.out.println(message.getRequestId());

            // Print the data received in the message
            System.out.println(message.getData());
        });

        // Navigate the driver to a specific URL
        driver.get("https://www.w3schools.com/html/tryit.asp?filename=tryhtml5_sse");

        // Disable the Network domain in DevTools
        devTools.send(Network.disable());
    }

}