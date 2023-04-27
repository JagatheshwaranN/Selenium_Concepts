package _01.selenium.basics;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class _WheelActions extends _01_LaunchBrowser {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	public static void main(String[] args) {
		scrollToElement();
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = get_driver(chromeOptions);
		driver.manage().window().maximize();
		return driver;
	}

	private static void scrollToElement() {
		browserSetup();
		driver.get("https://www.selenium.dev/");
		WebElement seleniumSponsors = driver.findElement(By.cssSelector(".selenium.text-center"));
		new Actions(driver).scrollToElement(seleniumSponsors).perform();
		Assert.assertTrue(inViewport(seleniumSponsors));
		driver.close();
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
