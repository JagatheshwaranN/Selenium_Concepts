package selenium4Features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author Jaga
 *
 */
public class ActionClassUpdate {

	private static WebDriver driver;
	private static WebElement rightClickButton, doubleClickButton, clickAndHoldButtton;
	private static Actions action;

	public static void main(String ar[]) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		rightClickDemo();
		doubleClickDemo();
		clickAndHoldDemo();
	}

	public static void rightClickDemo() throws InterruptedException {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");
		action = new Actions(driver);
		rightClickButton = driver.findElement(By.xpath("//span[@class='context-menu-one btn btn-neutral']"));
		action.contextClick(rightClickButton).perform();
		Thread.sleep(5000);
		driver.close();
	}

	public static void doubleClickDemo() throws InterruptedException {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://selenium08.blogspot.com/2019/11/double-click.html");
		action = new Actions(driver);
		doubleClickButton = driver.findElement(By.xpath("//button[text()='Double-Click me to see Alert message']"));
		action.doubleClick(doubleClickButton).perform();
		Thread.sleep(5000);
		driver.close();
	}

	public static void clickAndHoldDemo() throws InterruptedException {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://selenium08.blogspot.com/2020/01/click-and-hold.html");
		action = new Actions(driver);
		clickAndHoldButtton = driver.findElement(By.xpath("//li[text()='C']"));
		action.clickAndHold(clickAndHoldButtton).perform();
		Thread.sleep(5000);
		driver.close();
	}

}
