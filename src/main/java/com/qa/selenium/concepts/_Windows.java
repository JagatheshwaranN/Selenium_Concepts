package com.qa.selenium.concepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class _Windows {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = false)
	private static void getWindowHandle() {
		browserSetup();
		driver.get("https://letcode.in/frame");
		System.out.println(driver.getWindowHandle());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = false)
	private static void switchTabOrWindow() {
		browserSetup();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://the-internet.herokuapp.com/windows");
		String parentWindow = driver.getWindowHandle();
		System.out.println(parentWindow);
		assert driver.getWindowHandles().size() == 1;
		driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		for (String handle : driver.getWindowHandles()) {
			if (!parentWindow.contentEquals(handle)) {
				driver.switchTo().window(handle);
				System.out.println(handle);
				break;
			}
		}
		wait.until(ExpectedConditions.titleIs("New Window"));
		waitForSomeTime();
		driver.close();
		driver.switchTo().window(parentWindow);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void createNewTabOrWindow() {
		browserSetup();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://the-internet.herokuapp.com/windows");
		String parentWindow = driver.getWindowHandle();
		System.out.println(parentWindow);
		driver.switchTo().newWindow(WindowType.TAB);
		driver.navigate().to("https://the-internet.herokuapp.com/windows/new");
		String childWindow = driver.getWindowHandle();
		System.out.println(childWindow);
		wait.until(ExpectedConditions.titleIs("New Window"));
		waitForSomeTime();
		driver.close();
		driver.switchTo().window(parentWindow);
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
