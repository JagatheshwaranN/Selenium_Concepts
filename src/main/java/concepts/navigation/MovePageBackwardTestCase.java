package concepts.navigation;

import junit.framework.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class MovePageBackwardTestCase {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = true)
	private static void reloadBrowser() throws InterruptedException {
		browserSetup();
		driver.get("https://github.com/");
		driver.navigate().refresh();
		Assert.assertEquals(driver.getTitle(), "GitHub: Let’s build from here · GitHub");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void movePageBackward() throws InterruptedException {
		browserSetup();
		driver.get("https://github.com/");
		waitForSomeTime();
		driver.navigate().to("https://www.selenium.dev/");
		driver.navigate().back();
		Assert.assertEquals(driver.getTitle(), "GitHub: Let’s build from here · GitHub");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void movePageForward() throws InterruptedException {
		browserSetup();
		driver.get("https://github.com/");
		waitForSomeTime();
		driver.navigate().to("https://www.selenium.dev/");
		driver.navigate().back();
		waitForSomeTime();
		driver.navigate().forward();
		Assert.assertEquals(driver.getTitle(), "Selenium");
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
