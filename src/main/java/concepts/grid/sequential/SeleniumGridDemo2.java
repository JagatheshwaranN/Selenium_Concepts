package concepts.grid.sequential;

import java.net.MalformedURLException;
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

public class SeleniumGridDemo2 {
	
	private WebDriver driver;
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

		if (browser.equalsIgnoreCase("chrome")) {
			capabilities.setPlatform(Platform.ANY);
			capabilities.setBrowserName(browser);
			ChromeOptions options = new ChromeOptions();
			options.merge(capabilities);
		} else if (browser.equalsIgnoreCase("firefox")) {
			capabilities.setPlatform(Platform.ANY);
			capabilities.setBrowserName(browser);
			FirefoxOptions options = new FirefoxOptions();
			options.merge(capabilities);
		} else if (browser.equalsIgnoreCase("edge")) {
			capabilities.setPlatform(Platform.ANY);
			capabilities.setBrowserName("MicrosoftEdge");
			EdgeOptions options = new EdgeOptions();
			options.merge(capabilities);
		}

		driver = new RemoteWebDriver(new URL("http://192.168.1.10:4444/"), capabilities);
		driver.get("https://www.google.com/");
		System.out.println("Title of Page " + driver.getTitle() + " from Browser " + browser);
		Thread.sleep(10000);
		driver.quit();
	}

}
