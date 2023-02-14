package _01.selenium.basics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class _03_ChromeOptions extends _01_LaunchBrowser {

	public static WebDriver _driver;
	public static ChromeOptions options = new ChromeOptions();

	public static void main(String[] args) {

		openMaximizedBrowser();
		openChromeIncognito();
		acceptSSLSecurityIssue();
	}

	public static void openMaximizedBrowser() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		_driver = get_driver(options);
		_driver.get("https://www.selenium.dev/documentation/webdriver/browsers/chrome/");
		_driver.quit();
	}

	public static void openChromeIncognito() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("incognito");
		_driver = get_driver(options);
		_driver.get("https://www.google.com/");
		_driver.quit();
	}

	public static void acceptSSLSecurityIssue() {
		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		_driver = get_driver(options);
		_driver.get("https://untrusted-root.badssl.com/");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		_driver.quit();
	}

}
