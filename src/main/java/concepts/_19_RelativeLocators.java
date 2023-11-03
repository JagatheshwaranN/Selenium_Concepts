package concepts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;

import static org.openqa.selenium.support.locators.RelativeLocator.*;

public class _19_RelativeLocators {

	private WebDriver driver;

	@Test(priority = 1, enabled = false)
	private void getBelowElement() {
		browserSetup();
		driver.get("https://automationbookstore.dev/");
		String bookId = driver.findElement(with(By.tagName("li")).below(By.id("pid1"))).getAttribute("id");
		Assert.assertEquals(bookId, "pid5");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = false)
	private void getAboveElement() {
		browserSetup();
		driver.get("https://automationbookstore.dev/");
		String bookId = driver.findElement(with(By.tagName("li")).above(By.id("pid8"))).getAttribute("id");
		Assert.assertEquals(bookId, "pid4");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = false)
	private void getToLeftOfElement() {
		browserSetup();
		driver.get("https://automationbookstore.dev/");
		String bookId = driver.findElement(with(By.tagName("li")).toLeftOf(By.id("pid2"))).getAttribute("id");
		Assert.assertEquals(bookId, "pid1");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = false)
	private void getToRightOfElement() {
		browserSetup();
		driver.get("https://automationbookstore.dev/");
		String bookId = driver.findElement(with(By.tagName("li")).toRightOf(By.id("pid5"))).getAttribute("id");
		Assert.assertEquals(bookId, "pid6");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private void getNearElement() {
		browserSetup();
		driver.get("https://www.google.com/");
		String buttonValue = driver.findElement(with(By.tagName("input")).near(By.name("btnI"))).getAttribute("value");
		Assert.assertEquals(buttonValue, "Google Search");
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
