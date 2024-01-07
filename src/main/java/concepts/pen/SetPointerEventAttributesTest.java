package concepts.pen;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class SetPointerEventAttributesTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.browserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @Test
    public void testSetPointerEventAttributes() {
        // Navigate to a specific page
        driver.get("https://www.selenium.dev/selenium/web/pointerActionsPage.html");

        // Find the pointer area element on the page
        WebElement pointerArea = driver.findElement(By.id("pointerArea"));

        // Define a PointerInput for a pen with specified properties
        PointerInput penInput = new PointerInput(PointerInput.Kind.PEN, "default pen");

        // Define properties for the pen input (tiltX, tiltY, twist)
        PointerInput.PointerEventProperties eventProperties = PointerInput.eventProperties()
                .setTiltX(-72)
                .setTiltY(9)
                .setTwist(86);

        // Set the origin of the pointer actions to be relative to the pointerArea element
        PointerInput.Origin origin = PointerInput.Origin.fromElement(pointerArea);

        // Create a sequence of pointer actions using the pen
        Sequence penSequence = new Sequence(penInput, 0)
                // Move the pen to the center of the pointer area (0, 0 offset)
                .addAction(penInput.createPointerMove(Duration.ZERO, origin, 0, 0))
                // Press the pen down
                .addAction(penInput.createPointerDown(0))
                // Move the pen by 2 pixels to the right and 2 pixels down, applying event properties
                .addAction(penInput.createPointerMove(Duration.ZERO, origin, 2, 2, eventProperties))
                // Release the pen
                .addAction(penInput.createPointerUp(0));

        // Perform the sequence of pen actions on the WebDriver instance
        ((RemoteWebDriver) driver).perform(Collections.singletonList(penSequence));

        // Find elements representing various pointer actions (move, down, up)
        List<WebElement> moves = driver.findElements(By.className("pointermove"));
        Map<String, String> moveTo = getPropertyInfo(moves.get(0));
        Map<String, String> down = getPropertyInfo(driver.findElement(By.className("pointerdown")));
        Map<String, String> moveBy = getPropertyInfo(moves.get(1));
        Map<String, String> up = getPropertyInfo(driver.findElement(By.className("pointerup")));

        // Get the rectangle information of the pointer area
        Rectangle rectangle = pointerArea.getRect();

        // Calculate the center of the pointer area
        int centerXAxis = (int) (double) (rectangle.width / 2 + rectangle.getX());
        int centerYAxis = (int) (double) (rectangle.height / 2 + rectangle.getY());

        // Assertions to validate the properties of pointer actions
        Assert.assertEquals("-1", moveTo.get("button"));
        Assert.assertEquals("pen", moveTo.get("pointerType"));
        Assert.assertEquals(String.valueOf(centerXAxis), moveTo.get("pageX"));
        Assert.assertEquals(String.valueOf(centerYAxis), moveTo.get("pageY"));
        Assert.assertEquals("0", down.get("button"));
        Assert.assertEquals("pen", down.get("pointerType"));
        Assert.assertEquals(String.valueOf(centerXAxis), down.get("pageX"));
        Assert.assertEquals(String.valueOf(centerYAxis), down.get("pageY"));
        Assert.assertEquals("-1", moveBy.get("button"));
        Assert.assertEquals("pen", moveBy.get("pointerType"));
        Assert.assertEquals(String.valueOf(centerXAxis + 2), moveBy.get("pageX"));
        Assert.assertEquals(String.valueOf(centerYAxis + 2), moveBy.get("pageY"));
        Assert.assertEquals("0", up.get("button"));
        Assert.assertEquals("pen", up.get("pointerType"));
        Assert.assertEquals(String.valueOf(centerXAxis + 2), up.get("pageX"));
        Assert.assertEquals(String.valueOf(centerYAxis + 2), up.get("pageY"));
    }

    // Method to extract properties from a WebElement representing a pointer action
    private Map<String, String> getPropertyInfo(WebElement element) {
        // Get the text content of the WebElement
        String text = element.getText();

        // Extract property information by removing the initial identifier
        text = text.substring(text.indexOf(' ') + 1);

        // Split the text by "," to separate key-value pairs
        return Arrays.stream(text.split(", "))
                // Split each pair by ":" to separate keys and values
                .map(t -> t.split(": "))
                // Collect the split pairs into a Map<String, String>
                .collect(Collectors.toMap(c -> c[0], c -> c[1]));
    }

}
