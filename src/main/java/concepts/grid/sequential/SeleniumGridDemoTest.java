package concepts.grid.sequential;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SeleniumGridDemoTest {

	public static ThreadLocal<WebDriver> driverLocal = new ThreadLocal<WebDriver>();
	public DesiredCapabilities capabilities = new DesiredCapabilities();
	
	public WebDriver getDriver() {
		return driverLocal.get();
	}

	public void setDriver(WebDriver driver) {
		driverLocal.set(driver);
	}

	@Parameters({ "browser" })
	@Test
	public void launchBrowser(String browser) throws MalformedURLException, InterruptedException {

		ChromeOptions chromeOptions = null;
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.http.factory", "jdk-http-client");
			capabilities.setPlatform(Platform.WINDOWS);
			capabilities.setBrowserName(browser);
			chromeOptions = new ChromeOptions();
			// Selenium 4.21.0 version bug - Unable to launch session with Grid without below parameter
			chromeOptions.setEnableDownloads(true);
			chromeOptions.merge(capabilities);
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.http.factory", "jdk-http-client");
			capabilities.setPlatform(Platform.WINDOWS);
			capabilities.setBrowserName(browser);
			FirefoxOptions options = new FirefoxOptions();
			// Selenium 4.21.0 version bug - Unable to launch session with Grid without below parameter
			options.setEnableDownloads(true);
			options.merge(capabilities);
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.http.factory", "jdk-http-client");
			capabilities.setPlatform(Platform.WINDOWS);
			capabilities.setBrowserName("MicrosoftEdge");
			EdgeOptions options = new EdgeOptions();
			// Selenium 4.21.0 version bug - Unable to launch session with Grid without below parameter
			options.setEnableDownloads(true);
			options.merge(capabilities);
		}
		WebDriver driver = new RemoteWebDriver((URI.create("http://localhost:4444").toURL()), capabilities);
		driver.get("https://www.google.com/");
		System.out.println("Title of Page " + driver.getTitle() + " from Browser " + browser);
		Thread.sleep(10000);
		driver.quit();
	}

}
