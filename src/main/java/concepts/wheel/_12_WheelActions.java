package concepts.wheel;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class _12_WheelActions {

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

	@Test(enabled = true, priority = 3)
	public void scrollFromElementByGivenAmount() {
	
		driver.get("https://www.selenium.dev/");
		WebElement seleniumDonation = driver.findElement(By.xpath("//input[@type='image']"));
		WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(seleniumDonation);
		new Actions(driver).scrollFromOrigin(scrollOrigin, 0, 200).perform();
		WebElement copyRightContent = driver.findElement(By.xpath("//small[@class='text-white']"));
		Assert.assertTrue(inViewport(copyRightContent));
	
		
	}

	@Test(enabled = true, priority = 4)
	public void scrollFromElementByGivenAmountWithOffset() {
	
		driver.get("https://www.selenium.dev/");
		WebElement seleniumDonation = driver.findElement(By.xpath("//input[@type='image']"));
		WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(seleniumDonation, 0, -50);
		new Actions(driver).scrollFromOrigin(scrollOrigin, 0, 200).perform();
		WebElement copyRightContent = driver.findElement(By.xpath("//small[@class='text-white']"));
		Assert.assertTrue(inViewport(copyRightContent));
	
		
	}

	@Test(enabled = true, priority = 5)
	public void scrollFromViewportByGivenAmountFromOrigin() {
	
		driver.get("https://www.selenium.dev/");
		WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromViewport(10, 10);
		new Actions(driver).scrollFromOrigin(scrollOrigin, 0, 600).perform();
		WebElement seleniumSponsors = driver.findElement(By.cssSelector(".selenium.text-center"));
		Assert.assertTrue(inViewport(seleniumSponsors));
	
		
	}

	public boolean inViewport(WebElement element) {
		String script = """
				for(var e=arguments[0],f=e.offsetTop,t=e.offsetLeft,o=e.offsetWidth,n=e.offsetHeight;\
				 e.offsetParent;)f+=(e=e.offsetParent).offsetTop,t+=e.offsetLeft;\
				return f<window.pageYOffset+window.innerHeight&&t<window.pageXOffset+window.innerWidth&&f+n>\
				window.pageYOffset&&t+o>window.pageXOffset
				""";
		return (boolean) ((JavascriptExecutor) driver).executeScript(script, element);
	}
}
