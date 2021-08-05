package selenium4Features;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TakeScreenshotOfElement {

	public static void main(String ar[]) throws IOException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		driver.get("https://admin-demo.nopcommerce.com/login");
		WebElement email = driver.findElement(By.xpath("//input[@id='Email']"));
		WebElement password = driver.findElement(By.xpath("//input[@id='Password']"));

		// Take Screenshot Method 1
		File srcFile = email.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File("./target/screenshot/email.png"));

		// Take Screenshot Method 2
		TakesScreenshot screenShot = ((TakesScreenshot) password);
		File srcFile2 = screenShot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile2, new File("./target/screenshot/password.png"));

		driver.navigate().to("https://www.w3schools.com/html/html_tables.asp");
		WebElement webTable = driver.findElement(By.xpath("//table[@id='customers']"));

		// Take Screenshot Method 1
		File srcFile3 = webTable.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile3, new File("./target/screenshot/webTable.png"));

		driver.close();
	}

}
