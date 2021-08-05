package selenium4Features;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DimensonOfElement {

	public static void main(String ar[]) {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		driver.get("https://opensource-demo.orangehrmlive.com/index.php");
		WebElement login = driver.findElement(By.xpath("//input[@id='btnLogin']"));

		Rectangle loginButton = login.getRect();
		System.out.println(loginButton.getHeight());
		System.out.println(loginButton.getWidth());
		System.out.println(loginButton.getX());
		System.out.println(loginButton.getY());

		driver.close();
	}
}
