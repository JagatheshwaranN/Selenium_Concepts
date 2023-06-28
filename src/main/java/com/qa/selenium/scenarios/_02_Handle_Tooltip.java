package com.qa.selenium.scenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _02_Handle_Tooltip {

	private WebDriver driver;

	@Test(priority = 1, enabled = true)
	private void handleToolTip() {
		browserSetup();
		driver.get("https://jqueryui.com/tooltip/");
		WebElement frameElement = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
		driver.switchTo().frame(frameElement);
		new Actions(driver).moveToElement(driver.findElement(By.id("age"))).perform();
		String toolTip = driver.findElement(By.xpath("//div[@class='ui-tooltip-content']")).getText();
		Assert.assertEquals(toolTip, "We ask for your age only for statistical purposes.");
		waitForSomeTime();
		driver.close();
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
