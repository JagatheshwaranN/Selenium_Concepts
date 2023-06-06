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
		driver.get(
				"D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\DisabledElement.html");
		WebElement input = driver.findElement(By.id("myText"));
		input.sendKeys("Selenium");
		driver.findElement(By.xpath("//button")).click();
		waitForSomeTime();
		String getTextWhenDisable = input.getText();
		Assert.assertEquals(getTextWhenDisable, "");
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('disabled');", input);
		String getTextWhenEnable = input.getAttribute("value");
		Assert.assertEquals(getTextWhenEnable, "Selenium");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private void enterText() {
		var text = "Selenium";
		browserSetup();
		driver.get(
				"D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\DisabledElement.html");
		WebElement input = driver.findElement(By.id("myText"));
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].value='" + text + "';", input);
		String result = input.getAttribute("value");
		Assert.assertEquals(result, "Selenium");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private void clickElement() {
		browserSetup();
		driver.get(
				"D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\DisabledElement.html");
		WebElement input = driver.findElement(By.id("myText"));
		input.sendKeys("Selenium");
		WebElement button = driver.findElement(By.xpath("//button"));
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click()", button);
		boolean flag = input.getAttribute("disabled") != null;
		Assert.assertEquals(flag, true);
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 4, enabled = true)
	private void clearElement() {
		browserSetup();
		driver.get(
				"D:\\Environment_Collection\\Eclipse_Env\\Workspace\\Selenium_Concepts\\src\\main\\resources\\supportFiles\\DisabledElement.html");
		WebElement input = driver.findElement(By.id("myText"));
		input.sendKeys("Selenium");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor)driver;
		jsExecutor.executeScript("arguments[0].value='';", input);
		String getTextWhenEnable = input.getAttribute("value");
		Assert.assertEquals(getTextWhenEnable, "");
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
