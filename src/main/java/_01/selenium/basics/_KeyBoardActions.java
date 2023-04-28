package _01.selenium.basics;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _KeyBoardActions extends _01_LaunchBrowser {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(enabled = false, priority = 1)
	private static void KeyDown() {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");
		new Actions(driver).keyDown(Keys.SHIFT).sendKeys("app").perform();
		WebElement input = driver.findElement(By.id("textInput"));
		Assert.assertEquals("APP", input.getAttribute("value"));
		waitForSomeTime();
		driver.close();
	}
	
	@Test(enabled = true, priority = 2)
	private static void keyDownAndUp() {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");
		new Actions(driver).keyDown(Keys.SHIFT).sendKeys("app").keyUp(Keys.SHIFT).sendKeys("url").perform();
		WebElement input = driver.findElement(By.id("textInput"));
		Assert.assertEquals("APPurl", input.getAttribute("value"));
		waitForSomeTime();
		driver.close();
	}
	
	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = get_driver(chromeOptions);
		driver.manage().window().maximize();
		return driver;
	}
	
	private static void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
