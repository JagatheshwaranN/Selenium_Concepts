package com.qa.selenium.scenarios;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HandleAjaxCallTest {

	private WebDriver driver;

	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

	@BeforeMethod
	public void setUp() {
		// Initialize the WebDriver and open the desired URL before each test.
		driver = DriverConfiguration.browserSetup();
		driver.get("https://omayo.blogspot.com/");
	}

	@Test(priority = 1)
	public void testHandleAjaxCall() {
		// Wait for the page to load completely.
		untilPageLoadComplete();

		// Scroll to the bottom of the page.
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

		// Click on a dropdown element.
		WebElement dropDown = driver.findElement(By.cssSelector("button[class='dropbtn']"));
		dropDown.click();

		// Wait for Ajax calls to complete.
		untilJqueryIsDone();

		// Click on a link.
		WebElement flipkart = driver.findElement(By.xpath("//div[@id='myDropdown']//a[text()='Flipkart']"));
		new WebDriverWait(driver, WAIT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(flipkart)).click();

		// Verify the current URL.
		String result = driver.getCurrentUrl();
		Assert.assertEquals(result, "https://www.flipkart.com/");

	}

	@AfterMethod
	public void tearDown() {
		// Quit the WebDriver after each test.
		if (driver != null) {
			driver.quit();
		}
	}

	private void untilJqueryIsDone() {
		// Wait until jQuery Ajax calls are complete.
		until(driver -> {
			assert driver != null;
			return ((JavascriptExecutor) driver).executeScript("return jQuery.active==0").toString().equals("true");
		});
	}

	private void untilPageLoadComplete() {
		// Wait until the page is fully loaded.
		until(driver -> {
			assert driver != null;
			return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
					.equals("complete");
		});
	}

	private void until(ExpectedCondition<Boolean> waitCondition) {
		// General-purpose method to wait for a condition with a timeout.
		WebDriverWait wait = new WebDriverWait(driver, HandleAjaxCallTest.WAIT_TIMEOUT);
		wait.withTimeout(HandleAjaxCallTest.WAIT_TIMEOUT);
		try {
			wait.until(waitCondition);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
