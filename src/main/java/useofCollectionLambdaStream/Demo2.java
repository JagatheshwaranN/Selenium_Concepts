package useofCollectionLambdaStream;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author Jaga
 *
 */
public class Demo2 {
	
	private static WebDriver driver;

	public static void main(String ar[]) {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("https://www.amazon.in/");
		driver.switchTo().newWindow(WindowType.TAB);
		driver.get("https://www.flipkart.com/");

		//Using lambda expression
		Set<String> windowIds = driver.getWindowHandles();
		windowIds.forEach(window -> System.out.println(driver.switchTo().window(window).getCurrentUrl()));

		driver.quit();
	}
}
