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

public class _MouseActions {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;
	private static Actions actions;
	private static PointerInput mouse;
	private static Sequence sequence;

	@Test(priority = 1, enabled = true)
	private static void mouseClickAndHold() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/buttons");
		actions = new Actions(driver);
		actions.clickAndHold(driver.findElement(By.xpath("(//button[@class='button is-primary'])[2]"))).perform();
		driver.findElement(By.xpath("//h2[text()='Button has been long pressed']")).isDisplayed();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = true)
	private static void mouseClickAndRelease() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.click(driver.findElement(By.xpath("//input[@id='clickable']"))).perform();
		driver.findElement(By.xpath("//strong[@id='click-status']")).isDisplayed();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void mouseRightClick() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		actions = new Actions(driver);
		actions.contextClick(driver.findElement(By.xpath("//span[contains(@class,'context-menu-one')]"))).perform();
		driver.findElement(By.cssSelector(".context-menu-item.context-menu-icon.context-menu-icon-copy")).isDisplayed();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 4, enabled = true)
	private static void mouseBackClick() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
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
		driver.manage().window().maximize();
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
		Thread.sleep(2000);
		driver.close();
	}

	@Test(priority = 6, enabled = true)
	private static void mouseDoubleClick() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		actions = new Actions(driver);
		actions.doubleClick(driver.findElement(By.xpath("//button[contains(text(),'Double-Click Me To See Alert')]")))
				.perform();
		waitForSomeTime();
		Alert alert = driver.switchTo().alert();
		System.out.println(alert.getText());
		alert.dismiss();
		driver.close();
	}

	@Test(priority = 7, enabled = true)
	private static void mouseHover_MoveToElement() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("//input[@id='hover']"))).perform();
		driver.findElement(By.xpath("//strong[@id='move-status']")).isDisplayed();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 8, enabled = true)
	private static void mouseMoveByOffsetElement() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.id("mouse-tracker")), 20, 0).perform();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 9, enabled = true)
	private static void mouseMoveByViewport() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 10, 12));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
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
				.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 10, 12));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		new Actions(driver).moveByOffset(12, 15).perform();
		String[] result = driver.findElement(By.id("absolute-location")).getText().split(",");
		Assert.assertTrue(Math.abs(Integer.parseInt(result[0]) - 10 - 12) < 2);
		Assert.assertTrue(Math.abs(Integer.parseInt(result[1]) - 12 - 15) < 3);
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
		waitForSomeTime();
		driver.findElement(By.xpath("//strong[@id='drop-status']")).isDisplayed();
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
		waitForSomeTime();
		driver.findElement(By.xpath("//strong[@id='drop-status']")).isDisplayed();
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
		Actions actions = new Actions(driver);
		WebElement clickable = driver.findElement(By.id("clickable"));
		actions.clickAndHold(clickable).keyDown(Keys.SHIFT).sendKeys("a").perform();
		((RemoteWebDriver) driver).resetInputState();
		actions.sendKeys("a").perform();
		Assert.assertEquals("A", String.valueOf(clickable.getAttribute("value").charAt(0)));
		Assert.assertEquals("a", String.valueOf(clickable.getAttribute("value").charAt(1)));
		waitForSomeTime();
		driver.close();
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
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
