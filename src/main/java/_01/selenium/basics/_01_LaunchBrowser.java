package _01.selenium.basics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class _01_LaunchBrowser {

	public static WebDriver _driver;

	public static void main(String[] args) {

		launchChromeBrowser();
		launchEdgeBrowser();
		launchFirefoxBrowser();
	}

	public static void launchChromeBrowser() {

		_driver = new ChromeDriver();
		_driver.get("https://www.google.com/");
		_driver.quit();
	}

	public static void launchEdgeBrowser() {

		_driver = new EdgeDriver();
		_driver.get("https://www.google.com/");
		_driver.quit();
	}

	public static void launchFirefoxBrowser() {

		_driver = new FirefoxDriver();
		_driver.get("https://www.google.com/");
		_driver.quit();
	}

	/**
	 * After Chrome Version 110. The ChromeDriver() will not work as before due to
	 * the security constraint added. To fix this issue we must provide the
	 * ChromeOptions to the driver instance.
	 */
	public static WebDriver get_driver() {
		return _driver = new ChromeDriver();
	}

	public static WebDriver get_driver(ChromeOptions options) {
		return _driver = new ChromeDriver(options);
	}

	public static WebDriver get_ffDriver(FirefoxOptions options) {
		return _driver = new FirefoxDriver(options);
	}

	public static WebDriver get_edgeDriver(EdgeOptions options) {
		return _driver = new EdgeDriver(options);
	}
}
