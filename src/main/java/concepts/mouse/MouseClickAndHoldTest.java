package concepts.mouse;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Collections;

public class MouseClickAndHoldTest {

	public WebDriver driver;
	public ChromeOptions chromeOptions;
	public Actions actions;
	public PointerInput mouse;
	public Sequence sequence;

	@Test(priority = 1)
	public void mouseClickAndHold() {
		driver.get("https://letcode.in/buttons");
		actions = new Actions(driver);
		actions.clickAndHold(driver.findElement(By.xpath("(//button[@class='button is-primary'])[2]"))).perform();
		String result = driver.findElement(By.xpath("//h2[text()='Button has been long pressed']")).getText();
		Assert.assertEquals(result, "Button has been long pressed");
	}

	@Test(priority = 2)
	public void mouseClickAndRelease() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.click(driver.findElement(By.xpath("//input[@id='clickable']"))).perform();
		String result = driver.findElement(By.xpath("//strong[@id='click-status']")).getText();
		Assert.assertEquals(result, "focused");
	}

	@Test(priority = 3)
	public void mouseRightClick() {
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		actions = new Actions(driver);
		actions.contextClick(driver.findElement(By.xpath("//span[contains(@class,'context-menu-one')]"))).perform();
		String result = driver.findElement(By.cssSelector(".context-menu-item.context-menu-icon.context-menu-icon-copy")).getText();
		Assert.assertEquals(result, "Copy");
	}

	@Test(priority = 4)
	public void mouseBackClick() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		driver.findElement(By.id("click")).click();
		Assert.assertEquals(driver.getTitle(), "We Arrive Here");
		mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		sequence = new Sequence(mouse, 0).addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(driver.getTitle(), "BasicMouseInterfaceTest");
	}

	@Test(priority = 5)
	public void mouseForwardClick() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		driver.findElement(By.id("click")).click();
		Assert.assertEquals(driver.getTitle(), "We Arrive Here");
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(driver.getTitle(), "BasicMouseInterfaceTest");
		mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		sequence = new Sequence(mouse, 0).addAction(mouse.createPointerDown(PointerInput.MouseButton.FORWARD.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.FORWARD.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(driver.getTitle(), "We Arrive Here");
	}

	@Test(priority = 6)
	public void mouseDoubleClick() {
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		WebElement dblClkBtn = driver
				.findElement(By.xpath("//button[contains(text(),'Double-Click Me To See Alert')]"));
		new Actions(driver).doubleClick(dblClkBtn).perform();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "You double clicked me.. Thank You..");
		alert.dismiss();
	}

	@Test(priority = 7)
	public void moveToElement() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("//input[@id='hover']"))).perform();
		String result = driver.findElement(By.xpath("//strong[@id='move-status']")).getText();
		Assert.assertEquals(result, "hovered");
	}

	@Test(priority = 8)
	public void mouseMoveByOffsetElement() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.id("mouse-tracker")), 20, 0).perform();
		String result = driver.findElement(By.xpath("//span[@id='absolute-location']")).getText();
		Assert.assertEquals(result, "139, 589");
	}

	@Test(priority = 9)
	public void mouseMoveByViewport() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 8, 12));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		String[] result = driver.findElement(By.id("absolute-location")).getText().split(",");
		Assert.assertTrue(Math.abs(Integer.parseInt(result[0].strip()) - 8) < 2);
		Assert.assertTrue(Math.abs(Integer.parseInt(result[1].strip()) - 12) < 2);
	}

	@Test(priority = 10)
	public void mouseMoveByCurrentPosition() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 8, 11));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		new Actions(driver).moveByOffset(13, 15).perform();
		String[] result = driver.findElement(By.id("absolute-location")).getText().split(",");
		Assert.assertTrue(Math.abs(Integer.parseInt(result[0].strip()) - 13) < 2);
		Assert.assertTrue(Math.abs(Integer.parseInt(result[1].strip()) - 15) < 2);
	}

	@Test(priority = 11)
	public void mouseDragDrop() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.dragAndDrop(driver.findElement(By.id("draggable")), driver.findElement(By.id("droppable"))).perform();
		String result = driver.findElement(By.xpath("//strong[@id='drop-status']")).getText();
		Assert.assertEquals(result, "dropped");
	}

	@Test(priority = 12)
	public void mouseDragDropByOffset() {
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		Rectangle start = driver.findElement(By.id("draggable")).getRect();
		Rectangle finish = driver.findElement(By.id("droppable")).getRect();
		actions.dragAndDropBy(driver.findElement(By.id("draggable")), finish.getX() - start.getX(),
				finish.getY() - start.getY()).perform();
		String result = driver.findElement(By.xpath("//strong[@id='drop-status']")).getText();
		Assert.assertEquals(result, "dropped");
	}

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
