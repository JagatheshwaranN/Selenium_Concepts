package com.qa.selenium.concepts;

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

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;
	private static Actions actions;
	private static PointerInput mouse;
	private static Sequence sequence;

	@Test(priority = 1, enabled = true)
	private static void mouseClickAndHold() throws InterruptedException {
		browserSetup();
		driver.get("https://letcode.in/buttons");
		actions = new Actions(driver);
		actions.clickAndHold(driver.findElement(By.xpath("(//button[@class='button is-primary'])[2]"))).perform();
		waitForSomeTime();
		String result = driver.findElement(By.xpath("//h2[text()='Button has been long pressed']")).getText();
		Assert.assertEquals(result, "Button has been long pressed");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void mouseClickAndRelease() throws InterruptedException {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.click(driver.findElement(By.xpath("//input[@id='clickable']"))).perform();
		String result = driver.findElement(By.xpath("//strong[@id='click-status']")).getText();
		Assert.assertEquals(result, "focused");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void mouseRightClick() throws InterruptedException {
		browserSetup();
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		actions = new Actions(driver);
		actions.contextClick(driver.findElement(By.xpath("//span[contains(@class,'context-menu-one')]"))).perform();
		String result = driver.findElement(By.cssSelector(".context-menu-item.context-menu-icon.context-menu-icon-copy")).getText();
		Assert.assertEquals(result, "Copy");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private static void mouseBackClick() throws InterruptedException {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		driver.findElement(By.id("click")).click();
		Assert.assertEquals(driver.getTitle(), "We Arrive Here");
		waitForSomeTime();
		mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		sequence = new Sequence(mouse, 0).addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(driver.getTitle(), "BasicMouseInterfaceTest");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 5, enabled = true)
	private static void mouseForwardClick() throws InterruptedException {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		driver.findElement(By.id("click")).click();
		Assert.assertEquals(driver.getTitle(), "We Arrive Here");
		waitForSomeTime();
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(driver.getTitle(), "BasicMouseInterfaceTest");
		waitForSomeTime();
		mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		sequence = new Sequence(mouse, 0).addAction(mouse.createPointerDown(PointerInput.MouseButton.FORWARD.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.FORWARD.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(driver.getTitle(), "We Arrive Here");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	private static void mouseDoubleClick() throws InterruptedException {
		browserSetup();
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		WebElement dblClkBtn = driver
				.findElement(By.xpath("//button[contains(text(),'Double-Click Me To See Alert')]"));
		new Actions(driver).doubleClick(dblClkBtn).perform();
		waitForSomeTime();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "You double clicked me.. Thank You..");
		alert.dismiss();
		driver.close();
	}

	@Test(priority = 7, enabled = true)
	private static void moveToElement() throws InterruptedException {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("//input[@id='hover']"))).perform();
		String result = driver.findElement(By.xpath("//strong[@id='move-status']")).getText();
		Assert.assertEquals(result, "hovered");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	private static void mouseMoveByOffsetElement() throws InterruptedException {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.id("mouse-tracker")), 20, 0).perform();
		String result = driver.findElement(By.xpath("//span[@id='absolute-location']")).getText();
		Assert.assertEquals(result, "139, 589");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 9, enabled = true)
	private static void mouseMoveByViewport() throws InterruptedException {
		browserSetup();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 8, 12));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		String[] result = driver.findElement(By.id("absolute-location")).getText().split(",");
		Assert.assertTrue(Math.abs(Integer.parseInt(result[0].strip()) - 8) < 2);
		Assert.assertTrue(Math.abs(Integer.parseInt(result[1].strip()) - 12) < 2);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 10, enabled = true)
	private static void mouseMoveByCurrentPosition() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 8, 11));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		new Actions(driver).moveByOffset(13, 15).perform();
		String[] result = driver.findElement(By.id("absolute-location")).getText().split(",");
		Assert.assertTrue(Math.abs(Integer.parseInt(result[0].strip()) - 13) < 2);
		Assert.assertTrue(Math.abs(Integer.parseInt(result[1].strip()) - 15) < 2);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 11, enabled = true)
	private static void mouseDragDrop() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.dragAndDrop(driver.findElement(By.id("draggable")), driver.findElement(By.id("droppable"))).perform();
		String result = driver.findElement(By.xpath("//strong[@id='drop-status']")).getText();
		Assert.assertEquals(result, "dropped");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 12, enabled = true)
	private static void mouseDragDropByOffset() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		Rectangle start = driver.findElement(By.id("draggable")).getRect();
		Rectangle finish = driver.findElement(By.id("droppable")).getRect();
		actions.dragAndDropBy(driver.findElement(By.id("draggable")), finish.getX() - start.getX(),
				finish.getY() - start.getY()).perform();
		String result = driver.findElement(By.xpath("//strong[@id='drop-status']")).getText();
		Assert.assertEquals(result, "dropped");
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 13, enabled = true)
	private static void actionPause() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		long startTime = System.currentTimeMillis();
		new Actions(driver).moveToElement(driver.findElement(By.id("clickable"))).pause(Duration.ofSeconds(1))
				.clickAndHold().pause(Duration.ofSeconds(1)).sendKeys("action pause").perform();
		long endTime = System.currentTimeMillis() - startTime;
		Assert.assertTrue(endTime > 2000);
		Assert.assertTrue(endTime < 3000);
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 14, enabled = true)
	private static void actionReset() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		WebElement clickable = driver.findElement(By.id("clickable"));
		actions.clickAndHold(clickable).keyDown(Keys.SHIFT).sendKeys("a").perform();
		((RemoteWebDriver) driver).resetInputState();
		actions.sendKeys("a").perform();
		Assert.assertEquals("A", String.valueOf(clickable.getAttribute("value").charAt(0)));
		Assert.assertEquals("a", String.valueOf(clickable.getAttribute("value").charAt(1)));
		waitForSomeTime();
		driver.close();
	}

	/*
	 * The below method is used to handle the moving slider usecase.
	 */
	@Test(priority = 15, enabled = true)
	private static void dragAndDropBy() {
		browserSetup();
		driver.get("https://jqueryui.com/slider/");
		WebElement frameElement = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
		driver.switchTo().frame(frameElement);
		WebElement slider = driver.findElement(By.id("slider"));
		new Actions(driver).dragAndDropBy(slider, 50, 0).build().perform();
		String targetPosition = driver.findElement(By.xpath("//span[contains(@class,'ui-slider-handle')]"))
				.getAttribute("style");
		Assert.assertEquals(targetPosition, "left: 59%;");
		waitForSomeTime();
		driver.close();
	}
	
	@Test(priority = 16, enabled = true)
	private void sendKeys() {
		browserSetup();
		driver.get("https://accounts.google.com/");
		new Actions(driver).sendKeys(driver.findElement(By.name("identifier")), "google").perform();
		var userName = driver.findElement(By.name("identifier")).getAttribute("data-initial-value");
		Assert.assertEquals(userName, "google");
		waitForSomeTime();
		driver.close();
	}


	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		return driver;
	}

	private static void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
