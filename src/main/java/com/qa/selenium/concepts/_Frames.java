package com.qa.selenium.concepts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class _Frames {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	private static void moveInsideFrameUsingID() {
		browserSetup();
		driver.get("https://demo.automationtesting.in/Frames.html");
		driver.switchTo().frame("singleframe");
		driver.findElement(By.tagName("input")).sendKeys("John");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void moveInsideFrameUsingName() {
		browserSetup();
		driver.get("https://demo.automationtesting.in/Frames.html");
		driver.switchTo().frame("SingleFrame");
		driver.findElement(By.tagName("input")).sendKeys("John");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void moveInsideFrameUsingIndex() {
		browserSetup();
		driver.get("https://demo.automationtesting.in/Frames.html");
		driver.switchTo().frame(0);
		driver.findElement(By.tagName("input")).sendKeys("John");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private static void moveInsideFrameUsingWebElement() {
		browserSetup();
		driver.get("https://demo.automationtesting.in/Frames.html");
		driver.switchTo()
				.frame(driver.findElement(By.xpath("//iframe[@src='SingleFrame.html' and @name='SingleFrame']")));
		driver.findElement(By.tagName("input")).sendKeys("John");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private static void moveOutofFrame() {
		browserSetup();
		driver.get("https://demo.automationtesting.in/Frames.html");
		driver.switchTo().frame(0);
		driver.findElement(By.tagName("input")).sendKeys("John");
		driver.switchTo().defaultContent();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	private static void parentFrameDemo() {
		browserSetup();
		driver.get("https://letcode.in/frame");
		driver.switchTo().frame("firstFr");
		WebElement childFr = driver.findElement(By.xpath("//iframe[@src='innerFrame']"));
		driver.switchTo().frame(childFr);
		driver.findElement(By.name("email")).sendKeys("abc@gmail.com");
		driver.switchTo().parentFrame();
		driver.switchTo().defaultContent();
		waitForSomeTime();
		driver.close();
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
