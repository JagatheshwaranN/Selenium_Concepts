package _01.selenium.basics;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _WheelActions extends _01_LaunchBrowser {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(enabled = true, priority = 1)
	private static void scrollToElement() {
		browserSetup();
		driver.get("https://www.selenium.dev/");
		WebElement seleniumSponsors = driver.findElement(By.cssSelector(".selenium.text-center"));
		new Actions(driver).scrollToElement(seleniumSponsors).perform();
		Assert.assertTrue(inViewport(seleniumSponsors));
		waitForSomeTime();
		driver.close();
	}

	@Test(enabled = true, priority = 2)
	private static void scrollByGivenAmount() {
		browserSetup();
		driver.get("https://www.selenium.dev/");
		WebElement seleniumLearnMoreButton = driver
				.findElement(By.xpath("//a[contains(@class,'selenium-button selenium-white-cyan')]"));
		int yAxis = seleniumLearnMoreButton.getRect().y;
		new Actions(driver).scrollByAmount(0, yAxis).perform();
		Assert.assertTrue(inViewport(seleniumLearnMoreButton));
		waitForSomeTime();
		driver.close();
	}

	@Test(enabled = true, priority = 3)
	private static void scrollFromElementByGivenAmount() {
		browserSetup();
		driver.get("https://www.selenium.dev/");
		WebElement seleniumDonation = driver.findElement(By.xpath("//input[@type='image']"));
		WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(seleniumDonation);
		new Actions(driver).scrollFromOrigin(scrollOrigin, 0, 200).perform();
		WebElement copyRightContent = driver.findElement(By.xpath("//small[@class='text-white']"));
		Assert.assertTrue(inViewport(copyRightContent));
		waitForSomeTime();
		driver.close();
	}

	@Test(enabled = true, priority = 4)
	private static void scrollFromElementByGivenAmountWithOffset() {
		browserSetup();
		driver.get("https://www.selenium.dev/");
		WebElement seleniumDonation = driver.findElement(By.xpath("//input[@type='image']"));
		WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(seleniumDonation, 0, -50);
		new Actions(driver).scrollFromOrigin(scrollOrigin, 0, 200).perform();
		WebElement copyRightContent = driver.findElement(By.xpath("//small[@class='text-white']"));
		Assert.assertTrue(inViewport(copyRightContent));
		waitForSomeTime();
		driver.close();
	}

	@Test(enabled = true, priority = 5)
	private static void scrollFromViewportByGivenAmountFromOrigin() {
		browserSetup();
		driver.get("https://www.selenium.dev/");
		WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromViewport(10, 10);
		new Actions(driver).scrollFromOrigin(scrollOrigin, 0, 600).perform();
		WebElement seleniumSponsors = driver.findElement(By.cssSelector(".selenium.text-center"));
		Assert.assertTrue(inViewport(seleniumSponsors));
		waitForSomeTime();
		driver.close();
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = get_driver(chromeOptions);
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

	private static boolean inViewport(WebElement element) {
		String script = """
				for(var e=arguments[0],f=e.offsetTop,t=e.offsetLeft,o=e.offsetWidth,n=e.offsetHeight;\
				 e.offsetParent;)f+=(e=e.offsetParent).offsetTop,t+=e.offsetLeft;\
				return f<window.pageYOffset+window.innerHeight&&t<window.pageXOffset+window.innerWidth&&f+n>\
				window.pageYOffset&&t+o>window.pageXOffset
				""";
		return (boolean) ((JavascriptExecutor) driver).executeScript(script, element);
	}
}
