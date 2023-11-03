package concepts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class _01_LaunchBrowser {

	private static WebDriver driver;

	@Test(priority = 1, enabled = true)
	private static void launchChromeBrowser() {
		driver = new ChromeDriver();
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void launchEdgeBrowser() {
		driver = new EdgeDriver();
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void launchFirefoxBrowser() {
		driver = new FirefoxDriver();
		driver.get("https://www.google.com/");
		Assert.assertEquals(driver.getTitle(), "Google");
		driver.close();
	}
}
