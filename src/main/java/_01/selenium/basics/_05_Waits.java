package _01.selenium.basics;

import java.time.Clock;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class _05_Waits extends _01_LaunchBrowser {

	public static WebDriver _driver;
	public static WebDriverWait _wait;
	public static Wait<WebDriver> _wait2;

	public static void main(String[] args) {

		implicitWait();
		waitUntilType1();
		waitUntilType2();
		waitUntilType3();
		fluentWait();
		fluentWait2();
	}

	public static void implicitWait() {

		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		WebElement username = new WebDriverWait(_driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='username']")));
		username.sendKeys("admin");
		_driver.quit();
	}

	public static void waitUntilType1() {

		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		WebElement username = new WebDriverWait(_driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='username']")));
		username.sendKeys("admin");
		_driver.quit();
		/**
		 * More Options available under ExpectedCondition Class
		 * https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html
		 */
	}

	public static void waitUntilType2() {

		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		WebElement loginButton = new WebDriverWait(_driver, Duration.ofSeconds(5), Duration.ofMillis(1500))
				.until(ExpectedConditions.elementToBeClickable(
						By.cssSelector(".oxd-button.oxd-button--medium.oxd-button--main.orangehrm-login-button")));
		loginButton.click();
		_driver.quit();
	}

	public static void waitUntilType3() {

		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		WebElement password = new WebDriverWait(_driver, Duration.ofSeconds(10), Duration.ofMillis(2000),
				Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER)
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='password']")));
		password.sendKeys("admin123");
		_driver.quit();
	}

	public static void fluentWait() {

		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		_wait2 = new FluentWait<WebDriver>(_driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(1500)).ignoring(NoSuchElementException.class)
				.ignoring(NotFoundException.class);
		WebElement username = _wait2.until(_driver -> _driver.findElement(By.cssSelector("input[name='username']")));
		username.sendKeys("admin");
		_driver.quit();
	}

	public static void fluentWait2() {

		_driver = get_driver();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		_wait2 = new FluentWait<WebDriver>(_driver, Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER)
				.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(1500))
				.ignoring(NotFoundException.class);
		WebElement username = _wait2.until(_driver -> _driver.findElement(By.cssSelector("input[name='username']")));
		username.sendKeys("admin");
		_driver.quit();
	}
}
