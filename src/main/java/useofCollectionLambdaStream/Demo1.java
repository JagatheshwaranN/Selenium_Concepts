package useofCollectionLambdaStream;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 
 * @author Jaga
 *
 */
public class Demo1 {

	private static WebDriver driver;

	public static void main(String[] args) {

		driver = new ChromeDriver();
		driver.get("http://demowebshop.tricentis.com/");

		List<WebElement> links = driver.findElements(By.tagName("a"));
		int totalLinks = links.size();
		System.out.println("Total links on the page : " + totalLinks);

		// Using lambda & streams
		links.forEach(link -> System.out.println(link.getText()));
		long hrefLinks = links.stream().filter(link -> link.getAttribute("href") != null).count();
		System.out.println("Working links on the page : " + hrefLinks);

		driver.close();
	}

}
