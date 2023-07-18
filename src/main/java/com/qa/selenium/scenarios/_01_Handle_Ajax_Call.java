package com.qa.selenium.scenarios;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _01_Handle_Ajax_Call {

	private WebDriver driver;
	private WebDriverWait wait;

	@Test(priority = 1, enabled = true)
	private void handleAjaxCall() throws InterruptedException {
		browserSetup();
		driver.get("https://omayo.blogspot.com/");
		untilPageLoadComplete(driver, Duration.ofSeconds(10));
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		WebElement dropDown = driver.findElement(By.cssSelector("button[class='dropbtn']"));
		dropDown.click();
		untilJqueryIsDone(driver, Duration.ofSeconds(5));
		WebElement flipkart = driver.findElement(By.xpath("//div[@id='myDropdown']//a[text()='Flipkart']"));
		new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(flipkart))
				.click();
		String result = driver.getCurrentUrl();
		Assert.assertEquals(result, "https://www.flipkart.com/");
		waitForSomeTime();
		driver.close();
	}

	private void untilJqueryIsDone(WebDriver driver, Duration timeInSeconds) {
		until(driver, new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return jQuery.active==0").toString().equals("true");
			}
		}, timeInSeconds);
	}

	private void untilPageLoadComplete(WebDriver driver, Duration timeInSeconds) {
		until(driver, new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		}, timeInSeconds);
	}

	private void until(WebDriver driver, ExpectedCondition<Boolean> waitCondition, Duration timeInSeconds) {
		wait = new WebDriverWait(driver, timeInSeconds);
		wait.withTimeout(timeInSeconds);
		try {
			wait.until(waitCondition);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private void waitForSomeTime() {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(3));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
