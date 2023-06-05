package com.qa.selenium.concepts;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _20_JavaScriptExecutor {

	private WebDriver driver;
	private JavascriptExecutor jsExecutor;
	
	@Test(priority = 1, enabled = true)
	private void EnableElement() {
		browserSetup();
		driver.get("D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\DisabledElement.html");
		WebElement input = driver.findElement(By.id("myText"));
		input.sendKeys("Selenium");
		driver.findElement(By.xpath("//button")).click();
		waitForSomeTime();
		String getTextWhenDisable = input.getText();
		Assert.assertEquals(getTextWhenDisable, "");
		jsExecutor = (JavascriptExecutor)driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('disabled');", input);
		String getTextWhenEnable = input.getAttribute("value");
		Assert.assertEquals(getTextWhenEnable, "Selenium");
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
