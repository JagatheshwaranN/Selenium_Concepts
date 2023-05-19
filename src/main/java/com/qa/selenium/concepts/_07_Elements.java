package com.qa.selenium.concepts;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class _07_Elements {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	private static void elementDisplay() {
		gotoTestSite();
		boolean isElementDisplay = driver.findElement(By.cssSelector("input[name='no_type']")).isDisplayed();
		System.out.println("Element Display Status ==> " + isElementDisplay);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void elementEnabled() {
		gotoTestSite();
		boolean isElementEnabled = driver.findElement(By.cssSelector("input[name='no_type']")).isEnabled();
		System.out.println("Element Enable Status ==> " + isElementEnabled);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void elementSelected() {
		gotoTestSite();
		boolean isElementSelected = driver.findElement(By.cssSelector("input[name='checkbox_input']")).isSelected();
		System.out.println("Element Selected Status ==> " + isElementSelected);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private static void getElementTagName() {
		gotoTestSite();
		String elementTag = driver.findElement(By.cssSelector("input[name='number_input']")).getTagName();
		System.out.println("Element Tag Name ==> " + elementTag);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private static void getElementSize() {
		gotoTestSite();
		int elementHeight = driver.findElement(By.cssSelector("input[name='no_type']")).getSize().getHeight();
		int elementWidth = driver.findElement(By.cssSelector("input[name='no_type']")).getSize().getWidth();
		Rectangle rectangle = driver.findElement(By.cssSelector("input[name='no_type']")).getRect();
		System.out.println("Element Dimension Height ==> " + elementHeight);
		System.out.println("Element Dimension Width ==> " + elementWidth);
		System.out.println("Element Height Using Rectangle Class ==> " + rectangle.getHeight());
		System.out.println("Element Width Using Rectangle Class ==> " + rectangle.getWidth());
		System.out.println("Element X-Axis Using Rectangle Class ==> " + rectangle.getX());
		System.out.println("Element Y-Axis Using Rectangle Class ==> " + rectangle.getY());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	private static void getElementCSSProperties() {
		gotoTestSite();
		String elementColor = driver.findElement(By.cssSelector("input[name='color_input']")).getCssValue("color");
		String elementBGColor = driver.findElement(By.cssSelector("input[name='color_input']"))
				.getCssValue("background-color");
		System.out.println("Element Color ==> " + elementColor);
		System.out.println("Element Color ==> " + elementBGColor);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 7, enabled = true)
	private static void getElementText() {
		gotoTestSite();
		String elementText = driver.findElement(By.tagName("h1")).getText();
		System.out.println("Element Text Content ==> " + elementText);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	private static void getElementAttribute() {
		gotoTestSite();
		String elementValue = driver.findElement(By.cssSelector("input[name='no_type']")).getAttribute("value");
		String elementName = driver.findElement(By.cssSelector("input[name='no_type']")).getAttribute("name");
		System.out.println("Element Attribute Value ==> " + elementValue);
		System.out.println("Element Attribute Name ==> " + elementName);
		waitForSomeTime();
		driver.close();
	}

	private static void gotoTestSite() {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/inputs.html");
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
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
