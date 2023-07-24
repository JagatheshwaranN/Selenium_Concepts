package com.qa.selenium.scenarios;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _18_Handle_SyncUpIssue_CustomApproach {

	private WebDriver driver;

	@Test(priority = 1, enabled = true)
	private void handleSyncUpIssueUsingCustomApproach() {
		browserSetup();
		driver.get(
				"file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/SiteLoadDelay.html");
		driver.findElement(By.xpath("//button[@onclick='load()']")).click();
		handleElementSyncUp(driver, By.xpath("//a[@href='/market/login.jsp']"), 10).click();
		Assert.assertEquals(driver.getTitle(), "Login - Video Courses, eBooks, Prime Packs | Tutorialspoint");
		waitForSomeTime();
		driver.close();
	}

	private WebElement handleElementSyncUp(WebDriver driver, By locator, int time) {
		WebElement element = null;
		for (int i = 0; i < time; i++) {
			try {
				element = driver.findElement(locator);
				break;
			} catch (Exception ex) {
				try {
					Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(1));
				} catch (InterruptedException e) {
					System.out.println("Waiting for element to show up on the DOM");
				}
			}
		}

		return element;
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
