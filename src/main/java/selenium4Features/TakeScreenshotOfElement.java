package selenium4Features;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 
 * @author Jaga
 *
 */
public class TakeScreenshotOfElement {

	private static WebDriver driver;
	private static WebElement email, password, webTable;
	private static File srcFile, srcFile2, srcFile3;

	public static void main(String ar[]) throws IOException {

		driver = new ChromeDriver();
		driver.get("https://admin-demo.nopcommerce.com/login");
		email = driver.findElement(By.xpath("//input[@id='Email']"));
		password = driver.findElement(By.xpath("//input[@id='Password']"));

		// Take Screenshot Method 1
		srcFile = email.getScreenshotAs(OutputType.FILE);
		// FileUtils.copyFile(srcFile, new File("./target/screenshot/email.png"));

		// Take Screenshot Method 2
		TakesScreenshot screenShot = ((TakesScreenshot) password);
		srcFile2 = screenShot.getScreenshotAs(OutputType.FILE);
		// FileUtils.copyFile(srcFile2, new File("./target/screenshot/password.png"));

		driver.navigate().to("https://www.w3schools.com/html/html_tables.asp");
		webTable = driver.findElement(By.xpath("//table[@id='customers']"));
		// Take Screenshot Method 1
		srcFile3 = webTable.getScreenshotAs(OutputType.FILE);
		// FileUtils.copyFile(srcFile3, new File("./target/screenshot/webTable.png"));
		driver.close();

	}

}
