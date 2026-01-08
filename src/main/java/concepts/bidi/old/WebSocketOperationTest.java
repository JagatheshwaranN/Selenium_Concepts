package concepts.bidi.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.devtools.v142.network.model.Initiator;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.Optional;

public class WebSocketOperationTest {

    // Declare a WebDriver instance to interact with the web browser.
    private ChromeDriver driver;

    // Define a constant duration for the maximum wait time, set to 5 seconds
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

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
     * Websocket - It is a next generation bidirectional communication technology for web
     * applications which operates over a single socket. It's exposed a javascript interface
     * in an HTML5 component browsers. It makes it possible to open a 2way interactive
     * communication session between the user browser (client) and server.
     * Using this API, we can send the messages to the server and receive event driven responses
     * without calling the server for a while.
     * Many modern chat applications, for example, Whatsapp, viber and multiplier games use
     * underneath Websockets to operate. So, we are able to work with them.
     */
    @Test(priority = 1, enabled = false, description = "Relies on external PieSocket demo API which may reject connections. Need to create API Key with own account")
    public void testWebSocketOperation() {
        // Define expected values for connection status messages
        String expectedValue1 = "Connection Established";
        String expectedValue2 = "Connection closed";

        // Getting the DevTools instance from the WebDriver
        DevTools devTools = driver.getDevTools();

        // Creating a new DevTools session if one doesn't exist
        devTools.createSessionIfThereIsNotOne();

        // Enable the Network domain for capturing network events (including websockets)
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty(), Optional.empty(), Optional.empty()));

        // Listen for websocket creation events, printing details like URL, request ID, and initiator line number
        devTools.addListener(Network.webSocketCreated(), socket -> {
            System.out.println("WebSocket Created");
            System.out.println(socket.getUrl());
            System.out.println(socket.getRequestId());
            System.out.println(socket.getInitiator().flatMap(Initiator::getLineNumber));
        });

        // Listen for received websocket frames, printing payload and opcode
        devTools.addListener(Network.webSocketFrameReceived(), socket -> {
            System.out.println("WebSocket Received");
            System.out.println(socket.getResponse().getPayloadData());
            System.out.println(socket.getResponse().getOpcode());
        });

        // Listen for websocket frame errors and print the error message
        devTools.addListener(Network.webSocketFrameError(), socket -> System.out.println(socket.getErrorMessage()));

        // Listen for websocket closure events and print the timestamp
        devTools.addListener(Network.webSocketClosed(), socket -> {
            System.out.println("WebSocket Closed");
            System.out.println(socket.getTimestamp());
        });

        // Navigate to the websocket tester page
        driver.get("https://www.piesocket.com/websocket-tester");

        // Using WebDriverWait for specific elements to load before interacting with them
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        // Wait for the 'Connect' button to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']")));

        // Find the "Connect" button and scroll it into view
        WebElement connect = driver.findElement(By.xpath("//button[@type='submit']"));
        new Actions(driver).scrollToElement(connect).perform();

        // Click the "Connect" button
        connect.click();

        // Wait for the 'Connection Established' text to appear after connection
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Connection Established']")));

        // Get the actual value of an element from the web page
        String actualValue1 = driver.findElement(By.xpath("//span[text()='Connection Established']")).getText();

        // Assert the actual value matches the expected value for 'Connection Established'
        Assert.assertEquals(actualValue1, expectedValue1);

        // Locate and click the 'Disconnect' button
        WebElement disconnect = driver.findElement(By.xpath("//button[text()='Disconnect']"));
        disconnect.click();

        // Wait for the 'Connection closed' text to appear after disconnection
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Connection closed']")));

        // Getting the actual value of an element from the web page
        String actualValue2 = driver.findElement(By.xpath("//span[text()='Connection closed']")).getText();

        // Assert the actual value matches the expected value for 'Connection closed'
        Assert.assertEquals(actualValue2, expectedValue2);

        // Disable the network domain
        devTools.send(Network.disable());
    }

}