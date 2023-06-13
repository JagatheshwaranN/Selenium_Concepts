package com.qa.selenium.concepts;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _07_Elements {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	private static void elementDisplay() {
		gotoTestSite();
		Assert.assertTrue(driver.findElement(By.cssSelector("input[name='no_type']")).isDisplayed());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void elementEnabled() {
		gotoTestSite();
		Assert.assertTrue(driver.findElement(By.cssSelector("input[name='no_type']")).isEnabled());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void elementSelected() {
		gotoTestSite();
		Assert.assertTrue(driver.findElement(By.cssSelector("input[name='checkbox_input']")).isSelected());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private static void getElementTagName() {
		gotoTestSite();
		String elementTag = driver.findElement(By.cssSelector("input[name='number_input']")).getTagName();
		Assert.assertEquals(elementTag,"input");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private static void getElementSize() {
		gotoTestSite();
		int elementHeight = driver.findElement(By.cssSelector("input[name='no_type']")).getSize().getHeight();
		int elementWidth = driver.findElement(By.cssSelector("input[name='no_type']")).getSize().getWidth();
		Rectangle rectangle = driver.findElement(By.cssSelector("input[name='no_type']")).getRect();
		Point point = driver.findElement(By.cssSelector("input[name='no_type']")).getLocation();
		Assert.assertEquals(elementHeight, 21);
		Assert.assertEquals(elementWidth, 177);
		Assert.assertEquals(rectangle.getHeight(), 21);
		Assert.assertEquals(rectangle.getWidth(), 177);
		Assert.assertEquals(rectangle.getX(), 8);
		Assert.assertEquals(rectangle.getY(), 66);
		Assert.assertEquals(point.getX(), 8);
		Assert.assertEquals(point.getY(), 66);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	private static void getElementCSSProperties() {
		gotoTestSite();
		String elementColor = driver.findElement(By.cssSelector("input[name='color_input']")).getCssValue("color");
		String elementBGColor = driver.findElement(By.cssSelector("input[name='color_input']"))
				.getCssValue("background-color");
		Assert.assertEquals(elementColor, "rgba(0, 0, 0, 1)");
		Assert.assertEquals(elementBGColor, "rgba(240, 240, 240, 1)");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 7, enabled = true)
	private static void getElementText() {
		gotoTestSite();
		String elementText = driver.findElement(By.tagName("h1")).getText();
		Assert.assertEquals(elementText, "Testing Inputs");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	private static void getElementAttribute() {
		gotoTestSite();
		String elementValue = driver.findElement(By.cssSelector("input[name='no_type']")).getAttribute("value");
		String elementName = driver.findElement(By.cssSelector("input[name='no_type']")).getAttribute("name");
		Assert.assertEquals(elementValue, "input with no type");
		Assert.assertEquals(elementName, "no_type");
		waitForSomeTime();
		driver.close();
	}

	private static void gotoTestSite() {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/inputs.html");
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
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
