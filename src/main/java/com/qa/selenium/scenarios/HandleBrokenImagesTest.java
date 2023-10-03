package com.qa.selenium.scenarios;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HandleBrokenImagesTest {

	// Declare a WebDriver instance.
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Initialize WebDriver and maximize the browser window before each test.
		driver = DriverConfiguration.browserSetup();
	}

	@AfterMethod
	public void tearDown() {
		// Close the WebDriver after each test.
		driver.quit();
	}

	@Test(priority = 1)
	public void findBrokenImageApproach1() {
		// Open the web page containing images to check.
		driver.get("https://demoqa.com/broken");

		// Find all image elements on the page.
		List<WebElement> images = driver.findElements(By.tagName("img"));

		// Loop through each image and verify if it is broken.
		for (WebElement image : images) {

			// Check if the image is displayed using JavaScript.
			try {
				boolean imageDisplay = (Boolean) ((JavascriptExecutor) driver).executeScript(
						"return (typeof arguments[0].naturalWidth !== 'undefined' && arguments[0].naturalWidth > 0);",
						image);
				if (imageDisplay) {
					System.out.println("Image display Ok");
				} else {
					System.out.println("Image display Broken");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Test(priority = 2)
	public void findBrokenImageApproach2() {
		// Open the web page containing images to check.
		driver.get("https://demoqa.com/broken");

		// Find a specific image element using XPath.
		WebElement image = driver.findElement(By.xpath("//img[@src='/images/Toolsqa_1.jpg']"));

		// Check if the image is displayed by examining its naturalWidth attribute.
		if (image.getAttribute("naturalWidth").equals("0")) {
			System.out.println("Image display Broken");
		} else {
			System.out.println("Image display Ok");
		}
	}

}
