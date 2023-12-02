package concepts.cdp;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.log.Log;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

/**
 * @resource <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests">...</a>!
 */
public class GetConsoleLogTest {

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

	// Have to update new logic.
	@Deprecated
	@Test(priority = 1)
	public void testGetConsoleLog() {
		driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
		driver.findElement(By.id("consoleLog")).click();
		DevTools devTools = driver.getDevTools();
		devTools.createSessionIfThereIsNotOne();
		devTools.send(Log.enable());
		devTools.addListener(Log.entryAdded(), log -> {
			System.out.println("Log              : " + log.getText());
			System.out.println("Level            : " + log.getLevel());
			System.out.println("Category         : " + log.getCategory().isPresent());
			System.out.println("NetworkRequestId : " + log.getNetworkRequestId().isPresent());
			System.out.println("Source           : " + log.getSource());
			System.out.println("StackTrace       : " + log.getStackTrace().isPresent());
		});
	}


}