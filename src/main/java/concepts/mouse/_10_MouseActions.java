package concepts.mouse;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _10_MouseActions {

	public WebDriver driver;
	public ChromeOptions chromeOptions;
	public Actions actions;
	public PointerInput mouse;
	public Sequence sequence;

	/*
	 * The below method is used to handle the moving slider usecase.
	 */
	@Test(priority = 15)
	public void dragAndDropBy() {
		driver.get("https://jqueryui.com/slider/");
		WebElement frameElement = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
		driver.switchTo().frame(frameElement);
		WebElement slider = driver.findElement(By.id("slider"));
		new Actions(driver).dragAndDropBy(slider, 50, 0).build().perform();
		String targetPosition = driver.findElement(By.xpath("//span[contains(@class,'ui-slider-handle')]"))
				.getAttribute("style");
		Assert.assertEquals(targetPosition, "left: 59%;");
	}
	
	@Test(priority = 16)
	private void sendKeys() {
		driver.get("https://accounts.google.com/");
		new Actions(driver).sendKeys(driver.findElement(By.name("identifier")), "google").perform();
		var userName = driver.findElement(By.name("identifier")).getAttribute("data-initial-value");
		Assert.assertEquals(userName, "google");
	}
	
}
