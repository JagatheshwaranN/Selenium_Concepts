package concepts.windows;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class _08_Windows {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;



	@Test(priority = 3, enabled = false)
	private static void createNewTabOrWindow() {
		browserSetup();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://the-internet.herokuapp.com/windows");
		String parentWindow = driver.getWindowHandle();
		System.out.println(parentWindow);
		driver.switchTo().newWindow(WindowType.TAB);
		driver.navigate().to("https://the-internet.herokuapp.com/windows/new");
		String childWindow = driver.getWindowHandle();
		System.out.println(childWindow);
		wait.until(ExpectedConditions.titleIs("New Window"));
		waitForSomeTime();
		driver.close();
		driver.switchTo().window(parentWindow);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = false)
	private static void setGetWindowSize() {
		browserSetup();
		driver.get("https://the-internet.herokuapp.com/windows");
		// Approach 1
		int height = driver.manage().window().getSize().getHeight();
		int width = driver.manage().window().getSize().getWidth();
		System.out.println("Window's Height ==> " + height);
		System.out.println("Window's Width ==> " + width);
		// Set window size
		driver.manage().window().setSize(new Dimension(1024, 768));
		// Approach 2
		Dimension dimension = driver.manage().window().getSize();
		System.out.println("Window's Height ==> " + dimension.getHeight());
		System.out.println("Window's Width ==> " + dimension.getWidth());
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = false)
	private static void setGetWindowPosition() {
		browserSetup();
		driver.get("https://the-internet.herokuapp.com/windows");
		// Approach 1
		int x = driver.manage().window().getPosition().getX();
		int y = driver.manage().window().getPosition().getY();
		System.out.println("Window's X Axis ==> " + x);
		System.out.println("Window's Y Axis ==> " + y);
		//Set window position
		driver.manage().window().setPosition(new Point(5,5));
		// Approach 2
		Point position = driver.manage().window().getPosition();
		System.out.println("Window's X Axis ==> " + position.getX());
		System.out.println("Window's Y Axis ==> " + position.getY());
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 6, enabled = true)
	private static void adjustWindowSize() {
		browserSetup();
		driver.get("https://the-internet.herokuapp.com/windows");
		waitForSomeTime();
		driver.manage().window().minimize();
		waitForSomeTime();
		driver.manage().window().fullscreen();
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

	private static void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
