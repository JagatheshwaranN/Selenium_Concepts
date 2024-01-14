package concepts.cdp.old;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

/**
 * @resource <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/#:~:text=We%20can%20simulate%20things%20like,done%20from%20our%20automated%20tests">...</a>!
 */
public class JavaScriptNotificationTest {

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

	@Test(priority = 1)
	public void testJavaScriptNotification() {
		// Get the DevTools instance associated with the driver
		DevTools devTools = driver.getDevTools();

		// Create a new DevTools session if one doesn't exist
		devTools.createSessionIfThereIsNotOne();

		// Pin the 'notification' domain to inject JavaScript code to enable jQuery and jQuery-jGrowl libraries
		devTools.getDomains().javascript().pin("notification",
				"""
                // Injected JavaScript code to be executed on window onload
        
                // Check if jQuery is loaded, if not, load it from a CDN
                window.onload = () => {
                    if(!window.jQuery) {
                    	// Dynamically load jQuery library if not already present
                        var jquery = document.createElement('script');
                        jquery.type = 'text/javascript';
                        jquery.src = 'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js';
                        document.getElementsByTagName('head')[0].appendChild(jquery);
                    } else {
                    	// Use the existing jQuery instance if already loaded
                        $ = window.jQuery;
                    }
        
                    // Load jquery-jgrowl plugin (jQuery-jGrowl library) for notifications
                    $.getScript('https://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.4.8/jquery.jgrowl.min.js');
                    
                    // Append the jQuery-jGrowl CSS style sheet to the head section
                    $('head').append('<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.4.8/jquery.jgrowl.min.css" type="text/css" />');
                }
        
                // Define a function to highlight an element
                function highlight(element) {
                	// Store the original background color and outline style of the element
                    let defaultBG = element.style.backgroundColor;
                    let defaultOutline = element.style.outline;
                    
                    // Apply temporary highlighting to the element
                    element.style.backgroundColor = '#FAF8B1';
                    element.style.outline = '#09FA39 solid 3px';
        
                    // Reset the element's style back to original after a delay
                    setTimeout(function() {
                        element.style.backgroundColor = defaultBG;
                        element.style.outline = defaultOutline;
                    }, 1000);
                }
                """
		);

		// Navigate to the TodoMVC Backbone example page
		driver.get("https://todomvc.com/examples/backbone/");

		// Add a task item with the name "Clean the House" and highlight it
		enterTask("Clean the House", driver);

		// Add another task item with the name "Clean the Car" and highlight it
		enterTask("Clean the Car", driver);
	}

	private void enterTask(String task, WebDriver driver) {
		// Locate the task input field using XPath
		WebElement taskUpdateBar = driver.findElement(By.xpath("//input[@class='new-todo']"));

		// Highlight the task input field before entering the task
		((JavascriptExecutor) driver).executeScript("highlight(arguments[0])", taskUpdateBar);

		// Enter the task into the input field
		taskUpdateBar.sendKeys(task);

		// Perform a series of actions: click on the input field, enter the task, and press ENTER
		new Actions(driver).click(taskUpdateBar).sendKeys(Keys.ENTER).perform();

		// Generate a growl message notification for the added task
		generateGrowlMessage(task, driver);
	}

	private void generateGrowlMessage(String message, WebDriver driver) {
		// Use JavaScriptExecutor to execute the jQuery-jGrowl function to display a notification
		((JavascriptExecutor) driver).executeScript(String.format("$.jGrowl('%s', { header: 'Important!'});", message));

		// Add a delay of two seconds to allow the notification to be displayed properly
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			// Handle the InterruptedException exception if it occurs
			throw new RuntimeException(e);
		}
	}

}