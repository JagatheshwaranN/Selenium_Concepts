package concepts.jsexecutor;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import junit.framework.Assert;
import scenarios.DriverConfiguration;

public class _20_JavaScriptExecutor {

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

	private JavascriptExecutor jsExecutor;

	@Test(priority = 7, enabled = true)
	private void scrollPageUp() {
		browserSetup();
		driver.get("https://www.selenium.dev/");
		WebElement element = driver.findElement(By.xpath("//h2[@class='selenium text-center']"));
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView()", element);
		waitForSomeTime();
		jsExecutor.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
		WebElement seleniumTagLine = driver.findElement(By.xpath("//h1[@class='display-1']"));
		Assert.assertTrue(inViewport(seleniumTagLine));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	private void scrollPageDown() {
		browserSetup();
		driver.get("https://www.selenium.dev/");
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		WebElement seleniumCopyRight = driver.findElement(By.xpath("//small[@class='text-white']"));
		Assert.assertTrue(inViewport(seleniumCopyRight));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 9, enabled = true)
	private void scrollPageDownByPixel() {
		int pixel = 1000;
		browserSetup();
		driver.get("https://www.selenium.dev/");
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0, '" + pixel + "')");
		WebElement result = driver.findElement(By.xpath("//h2[@class='selenium text-center']"));
		Assert.assertTrue(inViewport(result));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 10, enabled = true)
	private void scrollPageUpByPixel() {
		int pixel = 1000;
		browserSetup();
		driver.get("https://www.selenium.dev/");
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0, '" + pixel + "')");
		waitForSomeTime();
		jsExecutor.executeScript("window.scrollBy(0, -'" + pixel + "')");
		WebElement result = driver.findElement(By.xpath("//h1[@class='display-1']"));
		Assert.assertTrue(inViewport(result));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 11, enabled = true)
	private void scrollPageRight() {
		browserSetup();
		driver.get("https://www.selenium.dev/");
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		WebElement seleniumTagLine = driver.findElement(By.xpath("//h1[@class='display-1']"));
		Assert.assertTrue(inViewport(seleniumTagLine));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 12, enabled = true)
	private void scrollPageLeft() {
		browserSetup();
		driver.get("https://www.selenium.dev/");
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		waitForSomeTime();
		jsExecutor.executeScript("window.scrollTo(-document.body.scrollHeight, 0)");
		WebElement seleniumTagLine = driver.findElement(By.xpath("//h1[@class='display-1']"));
		Assert.assertTrue(inViewport(seleniumTagLine));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 13, enabled = true)
	private void pageZoomByPercent() {
		String percent = "50%";
		browserSetup();
		driver.get("https://www.selenium.dev/");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("document.body.style.zoom='" + percent + "'");
		WebElement result = driver.findElement(By.xpath("//h2[text()='Getting Started']"));
		Assert.assertTrue(inViewport(result));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 14, enabled = true)
	private void pageZoomOnFirefox() {
		String percent = "0.75";
		firefoxSetup();
		driver.get("https://www.selenium.dev/");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("document.body.style.MozTransform='scale(" + percent + ")'");
		WebElement result = driver.findElement(By.xpath("//h2[text()='Getting Started']"));
		Assert.assertTrue(inViewport(result));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 15, enabled = true)
	private void highlightElement() {
		browserSetup();
		driver.get(
				"D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\DisabledElement.html");
		WebElement input = driver.findElement(By.id("myText"));
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid green;')",
				input);
		String result = driver.findElement(By.id("myText")).getCssValue("background");
		Assert.assertEquals(result.contains("rgb(255, 255, 0)"), true);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 16, enabled = true)
	private void refreshPage() {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");
		waitForSomeTime();
		WebElement input = driver.findElement(By.id("textInput"));
		new Actions(driver).sendKeys(input, "Automation!").perform();
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("document.location.reload()");
		input = driver.findElement(By.id("textInput"));
		Assert.assertEquals("", input.getAttribute("value"));
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 17, enabled = true)
	private void refreshPageType2() {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");
		waitForSomeTime();
		WebElement input = driver.findElement(By.id("textInput"));
		new Actions(driver).sendKeys(input, "Automation!").perform();
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("history.go(0)");
		input = driver.findElement(By.id("textInput"));
		Assert.assertEquals("", input.getAttribute("value"));
		waitForSomeTime();
		driver.close();
	}
	
	
	@Test(priority = 18, enabled = true)
	private void getText() {
		browserSetup();
		driver.get("https://www.google.com/");
		waitForSomeTime();
		WebElement input = driver.findElement(By.xpath("(//a[@class='gb_v'])[1]"));
		jsExecutor = (JavascriptExecutor) driver;
		String value = jsExecutor.executeScript("return document.documentElement.innerText;").toString();
		input = driver.findElement(By.xpath("(//a[@class='gb_v'])[1]"));
		Assert.assertEquals(value.contains(input.getText()), true);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 19, enabled = true)
	private void getTitle() {
		browserSetup();
		driver.get("https://www.google.com/");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		String value = jsExecutor.executeScript("return document.title").toString();
		Assert.assertEquals(value,"Google");
		waitForSomeTime();
		driver.close();
	}
	
	
	@Test(priority = 20, enabled = true)
	private void getElement() {
		browserSetup();
		driver.get("https://www.google.com/");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		WebElement searchBar = null;
		searchBar = (WebElement) jsExecutor.executeScript("return document.getElementById('APjFqb');", searchBar);
		searchBar.sendKeys("javascript");
		new Actions(driver).sendKeys(Keys.ENTER).perform();
		Assert.assertEquals(driver.getTitle(), "javascript - Google Search");
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 21, enabled = true)
	@SuppressWarnings("unchecked")
	private void getElements() {
		browserSetup();
		driver.get("https://demoqa.com/broken");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		List<WebElement> images = null;
		images = (List<WebElement>) jsExecutor.executeScript("return document.getElementsByTagName('img');", images);		
		Assert.assertEquals(images.size(), 4);
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 22, enabled = true)
	private void getWindowSize() {
		browserSetup();
		driver.get("https://www.google.com/");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		long height = (long) jsExecutor.executeScript("return window.innerHeight;");
		long width = (long) jsExecutor.executeScript("return window.innerWidth;");
		System.out.println(height);
		System.out.println(width);
		Assert.assertEquals(height, 612);
		Assert.assertEquals(width, 1366);
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 23, enabled = true)
	private void navigateToDifferentPage() {
		browserSetup();
		driver.get("https://www.google.com/");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = 'https://www.selenium.dev/'");
		Assert.assertEquals(driver.getTitle(), "Selenium");
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 24, enabled = true)
	private void scrollIntoElement() {
		browserSetup();
		driver.get("https://admin-demo.nopcommerce.com/login");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		waitForSomeTime();
		jsExecutor.executeScript("document.getElementById('Email').scrollIntoView(true);");
		WebElement email = driver.findElement(By.id("Email"));
		Assert.assertEquals(email.isDisplayed(), true);
		waitForSomeTime();
		driver.close();
	}
		
	private WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	private WebDriver firefoxSetup() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private boolean inViewport(WebElement element) {
		String script = """
				for(var e=arguments[0],f=e.offsetTop,t=e.offsetLeft,o=e.offsetWidth,n=e.offsetHeight;\
				 e.offsetParent;)f+=(e=e.offsetParent).offsetTop,t+=e.offsetLeft;\
				return f<window.pageYOffset+window.innerHeight&&t<window.pageXOffset+window.innerWidth&&f+n>\
				window.pageYOffset&&t+o>window.pageXOffset
				""";
		return (boolean) ((JavascriptExecutor) driver).executeScript(script, element);
	}
}
