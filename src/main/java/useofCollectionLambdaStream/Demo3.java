package useofCollectionLambdaStream;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;



/**
 * 
 * @author Jaga
 *
 */
public class Demo3 {
	
	private static WebDriver driver;

	public static void main(String ar[]) {

		
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("http://demowebshop.tricentis.com/");
		driver.findElement(By.xpath("//ul[@class='top-menu']//a[normalize-space()='Books']")).click();

		Select dropDown = new Select(driver.findElement(By.id("products-orderby")));
		dropDown.selectByVisibleText("Name: A to Z");

		List<WebElement> productTiles = driver.findElements(By.xpath("//h2[@class='product-title']"));

		// Using lambda & streams
		List<String> beforeSorting = productTiles.stream().map(productTitle -> productTitle.getText())
				.collect(Collectors.toList());
		List<String> afterSorting = productTiles.stream().map(productTitle -> productTitle.getText()).sorted()
				.collect(Collectors.toList());

		if (beforeSorting.equals(afterSorting)) {
			System.out.println("Product Titles are sorted");

		} else {
			System.out.println("Product Titles are not sorted");
		}
		driver.quit();
	}
}
