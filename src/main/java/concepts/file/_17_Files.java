package concepts.file;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class _17_Files {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
		driver = DriverConfiguration.browserSetup();
	}

	@AfterMethod
	public void tearDown() {
		// Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
		if (driver != null) {
			// If a WebDriver instance exists, quit/close the browser session.
			driver.quit();
		}
	}


	@Test(priority = 4, enabled = true)
	private void firefoxFileDownload() {
		firefoxBrowserSetupForDownload();
		driver.get("https://chromedriver.storage.googleapis.com/index.html?path=114.0.5735.90/");

		WebElement downloadLink = driver.findElement(By.xpath("//a[text()='chromedriver_win32.zip']"));
		downloadLink.click();

		File folder = new File(System.getProperty("user.dir"));
		File[] files = folder.listFiles();
		boolean found = false;
		File downloadedfile = null;
		for (File file : files) {
			if (file.isFile()) {
				String fileName = file.getName();
				System.out.println("File : " + file.getName());
				if (fileName.contains("chromedriver_win32.zip")) {
					downloadedfile = new File(fileName);
					found = true;
				}
			}
		}
		Assert.assertTrue(found, "Downloaded document is not found");
		downloadedfile.deleteOnExit();

		driver.close();
	}


	private WebDriver browserSetupForDownload() {
		HashMap<String, Object> preferences = new HashMap<String, Object>();
		preferences.put("profile.default_content_settings.popups", 0);
		preferences.put("download.default_directory", System.getProperty("user.dir"));
		//chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--remote-allow-origins=*");
		//chromeOptions.setExperimentalOption("prefs", preferences);
		//driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver firefoxBrowserSetupForDownload() {
//		firefoxProfile = new FirefoxProfile();
//		firefoxOptions = new FirefoxOptions();
//		firefoxProfile.setPreference("browser.download.folderList", 2);
//		firefoxProfile.setPreference("browser.download.dir",System.getProperty("user.dir"));
//		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv,application/zip");
//		firefoxOptions.setProfile(firefoxProfile);
//		driver = new FirefoxDriver(firefoxOptions);
		driver.manage().window().maximize();
		return driver;
	}
	
}
