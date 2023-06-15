package com.qa.selenium.scenarios;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class _01_Handle_Ajax_Call {

	private WebDriver driver;
	private WebDriverWait wait;

	@Test(priority = 1, enabled = true)
	private void handleAjaxCall() throws InterruptedException {
		browserSetup();
		driver.get("https://omayo.blogspot.com/");
//		Object obj = ((JavascriptExecutor) driver).executeScript("return document.readystate").equals("complete");
//		System.out.println("Object Created : " + obj);
		waitForPageLoaded();
		untilPageLoadComplete(driver, Duration.ofSeconds(10));
		// WebElement dropDown =
		// driver.findElement(By.cssSelector("button[class='dropbtn']"));
		// new
		// Actions(driver).scrollToElement(driver.findElement(By.xpath("//button[text()='My
		// Button']")));
//		((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
//		dropDown.click();
//		untilJqueryIsDone(driver, Duration.ofSeconds(10));
//		driver.findElement(By.xpath("//a[text()='Flipkart']")).click();
//		String result = driver.getCurrentUrl();
//		Assert.assertEquals(result, "https://www.flipkart.com/");
		waitForSomeTime();
		driver.close();
	}

	private void untilJqueryIsDone(WebDriver driver, Duration timeInSeconds) {
		until(driver, (d) -> {
			Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
			if (!isJqueryCallDone)
				System.out.println("JQuery call is in Progress");
			return isJqueryCallDone;
		}, timeInSeconds);
	}

	private void untilPageLoadComplete(WebDriver driver, Duration timeInSeconds) {
		until(driver, (d) -> {
			Object obj = ((JavascriptExecutor) d).executeScript("return document.readystate");
			System.out.println("Object : " + obj);
			Boolean isPageLoaded = (Boolean) ((JavascriptExecutor) d).executeScript("return document.readystate")
					.equals("complete");
			System.out.println("isPageLoaded : " + isPageLoaded);
			if (!isPageLoaded) {
				System.out.println("JQuery call is inprogress");
			}
			return isPageLoaded;
		}, timeInSeconds);
	}

	private void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, Duration timeInSeconds) {
		wait = new WebDriverWait(driver, timeInSeconds);
		wait.withTimeout(timeInSeconds);
		try {
			wait.until(waitCondition);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				System.out.println(((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete"));
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println(error.getMessage());
		}
	}

	private WebDriver browserSetup() {
		driver = new ChromeDriver();
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

}
