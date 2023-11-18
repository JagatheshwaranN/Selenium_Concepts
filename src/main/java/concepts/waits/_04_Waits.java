package concepts.waits;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Clock;
import java.time.Duration;

public class _04_Waits {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;
	private static Wait<WebDriver> wait;

	@Test(priority = 7, enabled = true)
	private static void fluentWaitType3() {
		browserSetup();
		driver.get("https://admin-demo.nopcommerce.com/login");
		wait = new FluentWait<WebDriver>(driver, Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER)
				.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(1500))
				.ignoring(NotFoundException.class);
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement element = driver.findElement(By.cssSelector(".button-1.login-button"));
				if (element.isDisplayed()) {
					return true;
				}
				return false;
			}
		};
		wait.until(driver -> function);
		WebElement loginButton = driver.findElement(By.cssSelector(".button-1.login-button"));
		loginButton.click();
		var result = driver.findElement(By.xpath("//div[@class='content-header']//h1")).getText();
		Assert.assertEquals(result, "Dashboard");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	private static void pageLoadWait() {
		browserSetup();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebElement orangeHRMLogo = driver.findElement(By.cssSelector("div[class='orangehrm-login-logo']"));
		Assert.assertTrue(orangeHRMLogo.isDisplayed());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 9, enabled = true)
	private static void scriptWait() {
		browserSetup();
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(15));
		((JavascriptExecutor) driver).executeScript("alert('hello world');");
		driver.switchTo().alert().accept();
		((JavascriptExecutor) driver).executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 500);");
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
