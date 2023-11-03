package concepts;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;
import org.testng.annotations.Test;

import com.google.common.io.Files;

public class _15_ScreenShot {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;

	@Test(priority = 1, enabled = false)
	private static void takePageScreenShot() throws IOException {
		browserSetup();
		driver.get("http://www.example.com");
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(source, new File("./screenshot.png"));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = false)
	private static void takeElementScreenShot() throws IOException {
		browserSetup();
		driver.get("http://www.example.com");
		WebElement header = driver.findElement(By.tagName("h1"));
		File source = header.getScreenshotAs(OutputType.FILE);
		Files.copy(source, new File("./element_screenshot.png"));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void printPage() throws IOException {
		browserSetupHeadLess();
		driver.get("http://www.example.com");
		PrintOptions printOptions = new PrintOptions();
		// printOptions.setPageRanges("1", "2");
		Pdf pdf = ((PrintsPage) driver).print(printOptions);
		String content = pdf.getContent();
		Files.write(OutputType.BYTES.convertFromBase64Png(content), new File("./Selenium_Print.pdf"));
		waitForSomeTime();
		driver.close();
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		return driver;
	}

	private static WebDriver browserSetupHeadLess() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless=new");
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
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
}
