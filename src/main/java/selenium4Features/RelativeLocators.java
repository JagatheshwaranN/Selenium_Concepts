package selenium4Features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import static org.openqa.selenium.support.locators.RelativeLocator.*;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author Jaga
 *
 */
public class RelativeLocators {

	private static WebDriver driver;
	private static WebElement loginButton, rememberMeCheckbox, rememberMeText;
	private static String bookId1, bookId2;

	public static void main(String ar[]) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://app.testproject.io/");
		Thread.sleep(5000);
		loginButton = driver.findElement(By.id("tp-sign-in"));
		rememberMeCheckbox = driver.findElement(By.id("rememberMe"));
		rememberMeText = driver.findElement(with(By.tagName("span")).above(loginButton).toRightOf(rememberMeCheckbox));
		System.out.println("Text : " + rememberMeText.getText());
		driver.navigate().to("https://automationbookstore.dev/");
		bookId1 = driver.findElement(with(By.tagName("li")).toLeftOf(By.id("pid6")).below(By.id("pid1")))
				.getAttribute("id");
		Assert.assertEquals(bookId1, "pid5");
		bookId2 = driver.findElement(with(By.tagName("li")).above(By.id("pid6")).toRightOf(By.id("pid1")))
				.getAttribute("id");
		Assert.assertEquals(bookId2, "pid2");
		driver.close();

	}

}
