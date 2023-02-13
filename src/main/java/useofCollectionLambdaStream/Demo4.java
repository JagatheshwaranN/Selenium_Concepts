package useofCollectionLambdaStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 
 * @author Jaga
 *
 */
public class Demo4 {

	private static WebDriver driver;

	public static void main(String ar[]) {

		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("http://demowebshop.tricentis.com/");

		List<WebElement> prodTitles = driver.findElements(By.xpath("//h2[@class='product-title']"));
		List<WebElement> prodPrices = driver.findElements(By.xpath("//div[@class='prices']"));

		Map<String, Double> prodMap = new HashMap<String, Double>();

		for (int i = 0; i < prodTitles.size(); i++) {
			String title = prodTitles.get(i).getText();
			double price = Double.parseDouble(prodPrices.get(i).getText());
			prodMap.put(title, price);
		}
		// Using lambda & stream
		prodMap.forEach((t, p) -> System.out.println(t + " " + p));
		prodMap.entrySet().stream().filter(e -> e.getValue() > 800).forEach(v -> System.out.println(v));
		driver.quit();
	}
}
