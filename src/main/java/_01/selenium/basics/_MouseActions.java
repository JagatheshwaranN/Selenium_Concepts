package _01.selenium.basics;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class _MouseActions extends _01_LaunchBrowser {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;
	private static Actions actions;
	private static PointerInput mouse;
	private static Sequence sequence;

	public static void main(String[] args) {

		try {
			mouseClickAndHold();
			mouseClickAndRelease();
			mouseRightClick();
			mouseBackClick();
			mouseForwardClick();
			mouseDoubleClick();
			mouseHover_MoveToElement();
			mouseMoveByOffsetElement();
			mouseMoveByViewport();
			mouseMoveByCurrentPosition();
			mouseDragDrop();
			mouseDragDropByOffset();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = get_driver(chromeOptions);
		return driver;
	}

	private static void mouseClickAndHold() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://letcode.in/buttons");
		actions = new Actions(driver);
		actions.clickAndHold(driver.findElement(By.xpath("(//button[@class='button is-primary'])[2]"))).perform();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//h2[text()='Button has been long pressed']")).isDisplayed();
		driver.quit();
	}

	private static void mouseClickAndRelease() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.click(driver.findElement(By.xpath("//input[@id='clickable']"))).perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//strong[@id='click-status']")).isDisplayed();
		driver.quit();
	}

	private static void mouseRightClick() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		actions = new Actions(driver);
		actions.contextClick(driver.findElement(By.xpath("//span[contains(@class,'context-menu-one')]"))).perform();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".context-menu-item.context-menu-icon.context-menu-icon-copy")).isDisplayed();
		driver.quit();
	}

	private static void mouseBackClick() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		driver.findElement(By.id("click")).click();
		Assert.assertEquals(driver.getTitle(), "We Arrive Here");
		Thread.sleep(5000);
		mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		sequence = new Sequence(mouse, 0).addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(driver.getTitle(), "BasicMouseInterfaceTest");
		Thread.sleep(5000);
		driver.quit();
	}

	private static void mouseForwardClick() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		driver.findElement(By.id("click")).click();
		Assert.assertEquals(driver.getTitle(), "We Arrive Here");
		Thread.sleep(3000);
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(driver.getTitle(), "BasicMouseInterfaceTest");
		Thread.sleep(3000);
		mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		sequence = new Sequence(mouse, 0).addAction(mouse.createPointerDown(PointerInput.MouseButton.FORWARD.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.FORWARD.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(driver.getTitle(), "We Arrive Here");
		Thread.sleep(2000);
		driver.quit();
	}

	private static void mouseDoubleClick() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		actions = new Actions(driver);
		actions.doubleClick(driver.findElement(By.xpath("//button[contains(text(),'Double-Click Me To See Alert')]")))
				.perform();
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		System.out.println(alert.getText());
		alert.dismiss();
		driver.quit();
	}

	private static void mouseHover_MoveToElement() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("//input[@id='hover']"))).perform();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//strong[@id='move-status']")).isDisplayed();
		driver.quit();
	}

	private static void mouseMoveByOffsetElement() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.id("mouse-tracker")), 20, 0).perform();
		Thread.sleep(5000);
		driver.quit();
	}

	private static void mouseMoveByViewport() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 10, 12));
		((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
		Thread.sleep(5000);
		driver.quit();
	}

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
		Thread.sleep(5000);
		driver.quit();
	}

	private static void mouseDragDrop() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		actions.dragAndDrop(driver.findElement(By.id("draggable")), driver.findElement(By.id("droppable"))).perform();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//strong[@id='drop-status']")).isDisplayed();
		driver.quit();
	}

	private static void mouseDragDropByOffset() throws InterruptedException {
		browserSetup();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		actions = new Actions(driver);
		Rectangle start = driver.findElement(By.id("draggable")).getRect();
		Rectangle finish = driver.findElement(By.id("droppable")).getRect();
		actions.dragAndDropBy(driver.findElement(By.id("draggable")), finish.getX() - start.getX(),
				finish.getY() - start.getY()).perform();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//strong[@id='drop-status']")).isDisplayed();
		driver.quit();
	}
}
