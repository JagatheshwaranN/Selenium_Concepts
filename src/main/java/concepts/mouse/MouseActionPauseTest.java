package concepts.mouse;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class MouseActionPauseTest {

	public WebDriver driver;
	public ChromeOptions chromeOptions;
	public Actions actions;
	public PointerInput mouse;
	public Sequence sequence;


	@Test(priority = 13)
	public void actionPause() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		long startTime = System.currentTimeMillis();
		new Actions(driver).moveToElement(driver.findElement(By.id("clickable"))).pause(Duration.ofSeconds(1))
				.clickAndHold().pause(Duration.ofSeconds(1)).sendKeys("action pause").perform();
		long endTime = System.currentTimeMillis() - startTime;
		Assert.assertTrue(endTime > 2000);
		Assert.assertTrue(endTime < 3000);
	}

	@Test(priority = 14)
	public void actionReset() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		WebElement clickable = driver.findElement(By.id("clickable"));
		actions.clickAndHold(clickable).keyDown(Keys.SHIFT).sendKeys("a").perform();
		((RemoteWebDriver) driver).resetInputState();
		actions.sendKeys("a").perform();
		Assert.assertEquals("A", String.valueOf(clickable.getAttribute("value").charAt(0)));
		Assert.assertEquals("a", String.valueOf(clickable.getAttribute("value").charAt(1)));
	}

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
