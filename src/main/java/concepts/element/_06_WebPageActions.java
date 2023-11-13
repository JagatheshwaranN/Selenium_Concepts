package concepts.element;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class _06_WebPageActions {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	private void clearAnElement() {
		browserSetup();
		driver.get("https://admin-demo.nopcommerce.com/login");
		waitForSomeTime();
		WebElement email = driver.findElement(By.cssSelector("input[name='Email']"));
		email.clear();
		email.click();
		new Actions(driver).keyDown(Keys.ENTER).perform();
		var errorText = driver.findElement(By.xpath("//span[@id='Email-error']")).getText();
		Assert.assertEquals(errorText, "Please enter your email");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private void clickOnAnElement() {
		browserSetup();
		driver.get("https://admin-demo.nopcommerce.com/login");
		waitForSomeTime();
		driver.findElement(By.xpath("//button[@class='button-1 login-button']")).click();
		Assert.assertEquals(driver.getTitle(), "Dashboard / nopCommerce administration");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private void typeInAnElement() {
		browserSetup();
		driver.get("https://accounts.google.com/");
		driver.findElement(By.name("identifier")).sendKeys("google");
		var userName = driver.findElement(By.name("identifier")).getAttribute("data-initial-value");
		Assert.assertEquals(userName, "google");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private void getPageSource() {
		browserSetup();
		driver.get("https://www.example.com/");
		var pageSource = driver.getPageSource();
		Assert.assertEquals(pageSource.contains("<title>Example Domain</title>"), true);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private void getPageTitle() {
		browserSetup();
		driver.get("https://www.example.com/");
		var pageTitle = driver.getTitle();
		Assert.assertEquals(pageTitle, "Example Domain");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	private void getPageUrl() {
		browserSetup();
		driver.get("https://www.example.com/");
		var pageURL = driver.getCurrentUrl();
		Assert.assertEquals(pageURL, "https://www.example.com/");
		waitForSomeTime();
		driver.close();
	}
	
	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--remote-allow-origins=*");
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
