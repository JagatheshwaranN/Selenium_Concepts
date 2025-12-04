package concepts.grid.sequential;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;

public class SeleniumGridDemo2Test {

	public static ThreadLocal<WebDriver> driverLocal = new ThreadLocal<WebDriver>();
	public DesiredCapabilities capabilities = new DesiredCapabilities();
	
	public WebDriver getDriver() {
		return driverLocal.get();
	}

	public void setDriver(WebDriver driver) {
		driverLocal.set(driver);
	}

	@Test
	public void setup() throws MalformedURLException, InterruptedException {
		launchBrowser("chrome");
	}

	public void launchBrowser(String browser) throws MalformedURLException, InterruptedException {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.http.factory", "jdk-http-client");
			capabilities.setPlatform(Platform.WINDOWS);
			capabilities.setBrowserName(browser);
		}
		WebDriver driver = new RemoteWebDriver((URI.create("http://localhost:4444").toURL()), capabilities);
		driver.get("https://www.google.com/");
		System.out.println("Title of Page " + driver.getTitle() + " from Browser " + browser);
		Thread.sleep(10000);
		driver.quit();
	}

}
