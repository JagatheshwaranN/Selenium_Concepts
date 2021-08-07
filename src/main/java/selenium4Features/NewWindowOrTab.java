package selenium4Features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author Jaga
 *
 */
public class NewWindowOrTab {

	private static WebDriver driver;

	public static void main(String ar[]) {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.google.com/");
		driver.switchTo().newWindow(WindowType.TAB);
		driver.get("https://www.youtube.com/");
		driver.switchTo().newWindow(WindowType.WINDOW);
		driver.get("https://www.flipkart.com/");
		driver.quit();

	}
}
