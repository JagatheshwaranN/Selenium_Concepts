package concepts.grid.sequential;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class SeleniumGridDemo1Test {

	public static ThreadLocal<WebDriver> driverLocal = new ThreadLocal<WebDriver>();
	public DesiredCapabilities capabilities = new DesiredCapabilities();
	public WebDriver getDriver() {
		return driverLocal.get();
	}

	public void setDriver(WebDriver driver) {
		driverLocal.set(driver);
	}


	public void launchBrowser(String browser) throws MalformedURLException, InterruptedException {

		try {
			switch (browser) {
				case "chrome" -> {
					System.setProperty("webdriver.http.factory", "jdk-http-client");
					capabilities.setPlatform(Platform.WINDOWS);
					capabilities.setBrowserName("chrome");
					WebDriver driver1 = new RemoteWebDriver(URI.create("http://localhost:4444").toURL(), capabilities);
					driver1.get("https://www.google.com/");
					System.out.println("Title of Page " + driver1.getTitle() + " from Browser ");
					Thread.sleep(2000);
                    driver1.close();
				}
				default -> throw new IllegalArgumentException("Browser Not Valid");
			};
		} catch (MalformedURLException ex) {
			System.out.println("Malformed URL for Remote WebDriver: " + ex.getMessage());
			throw new RuntimeException("Invalid URL for Remote WebDriver.", ex);
		}
	}

	@Test
	public  void setup() throws MalformedURLException, InterruptedException {
		launchBrowser("chrome");
//		driver.get("https://www.google.com/");
//		System.out.println("Title of Page " + driver.getTitle() + " from Browser ");
//		Thread.sleep(10000);
//		driver.quit();
	}

}
