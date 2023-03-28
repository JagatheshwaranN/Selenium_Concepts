package _01.selenium.basics;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class _04_WebPageActions extends _01_LaunchBrowser {

	public static WebDriver _driver;
	public static ChromeOptions _chromeOptions;
	public static Select _selectObject;
	public static Actions _actions;
	public static PointerInput mouse;
	public static Sequence sequence;

	public static void main(String[] args) {

		try {
//			clearAnElement();
//			clickOnAnElement();
//			dropDownOptions();
//			dropDownSelectedOption();
//			deSelectDropDownMultipleOptions();
//			selectDisabledOption();
//			selectDropDownSingleOptionByIndex();
//			selectDropDownSingleOptionByVisibleText();
//			selectDropDownSingleOptionByValue();
//			selectDropDownMultipleOptions();
//			typeInAnElement();
//			mouseHover();
//			mouseDragDrop();
//			mouseDragDropByOffset();
//			mouseClickAndHold();
//			mouseRightClick();
//			mouseDoubleClick();
//			mouseClick();
//			mouseMoveByOffset();
//			mouseBackClick();
//			mouseForwardClick();
//			mouseMoveByViewport();
			mouseMoveByOffset();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver browserSetup() {
		_chromeOptions = new ChromeOptions();
		_chromeOptions.addArguments("--remote-allow-origins=*");
		_driver = get_driver(_chromeOptions);
		return _driver;
	}

	public static void clearAnElement() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://admin-demo.nopcommerce.com/login");
		Thread.sleep(8000);
		_driver.findElement(By.cssSelector("input[name='Email']")).clear();
		Thread.sleep(3000);
		_driver.quit();
	}

	public static void clickOnAnElement() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		Thread.sleep(8000);
		_driver.findElement(By.cssSelector(".oxd-button.oxd-button--medium.oxd-button--main.orangehrm-login-button"))
				.click();
		_driver.quit();
	}

	public static void selectDropDownSingleOptionByVisibleText() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#fruits")));
		_selectObject.selectByVisibleText("Apple");
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void selectDropDownSingleOptionByValue() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#fruits")));
		_selectObject.selectByValue("1");
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void selectDropDownSingleOptionByIndex() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#fruits")));
		_selectObject.selectByIndex(2);
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void selectDropDownMultipleOptions() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#superheros")));
		boolean isMultiSelect = _selectObject.isMultiple();
		System.out.println("DropDown Has Multi Select Option : " + isMultiSelect);
		_selectObject.selectByValue("am");
		_selectObject.selectByValue("aq");
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void deSelectDropDownMultipleOptions() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#superheros")));
		boolean isMultiSelect = _selectObject.isMultiple();
		System.out.println("DropDown Has Multi Select Option : " + isMultiSelect);
		_selectObject.selectByIndex(0);
		_selectObject.selectByValue("aq");
		_selectObject.selectByVisibleText("The Avengers");
		Thread.sleep(2000);
		_selectObject.deselectByIndex(0);
		_selectObject.deselectByValue("aq");
		_selectObject.deselectByVisibleText("The Avengers");
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void dropDownOptions() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#superheros")));
		List<WebElement> _dropDownOptions = _selectObject.getOptions();
		_dropDownOptions.forEach(e -> System.out.println(e.getText()));
		_driver.quit();
	}

	public static void dropDownSelectedOption() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/dropdowns");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.cssSelector("#superheros")));
		_selectObject.selectByValue("aq");
		List<WebElement> _dropDownOptions = _selectObject.getAllSelectedOptions();
		_dropDownOptions.forEach(e -> System.out.println(e.getText()));
		_driver.quit();
	}

	public static void selectDisabledOption() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get(
				"file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/disabledSelect.html");
		Thread.sleep(8000);
		_selectObject = new Select(_driver.findElement(By.name("single_disabled")));
		Assert.assertThrows(UnsupportedOperationException.class, () -> {
			_selectObject.selectByValue("disabled");
		});
		_driver.quit();
	}

	public static void typeInAnElement() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://opensource-demo.orangehrmlive.com/web/index.php");
		Thread.sleep(8000);
		_driver.findElement(By.cssSelector("input[name='username']")).sendKeys("admin");
		_driver.quit();
	}

	public static void mouseHover() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		_actions = new Actions(_driver);
		_actions.moveToElement(_driver.findElement(By.xpath("//input[@id='hover']"))).perform();
		Thread.sleep(5000);
		_driver.findElement(By.xpath("//strong[@id='move-status']")).isDisplayed();
		_driver.quit();
	}

	public static void mouseDragDrop() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		_actions = new Actions(_driver);
		_actions.dragAndDrop(_driver.findElement(By.id("draggable")), _driver.findElement(By.id("droppable")))
				.perform();
		Thread.sleep(5000);
		_driver.findElement(By.xpath("//strong[@id='drop-status']")).isDisplayed();
		_driver.quit();
	}

	public static void mouseDragDropByOffset() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		_actions = new Actions(_driver);
		Rectangle start = _driver.findElement(By.id("draggable")).getRect();
		Rectangle finish = _driver.findElement(By.id("droppable")).getRect();
		_actions.dragAndDropBy(_driver.findElement(By.id("draggable")), finish.getX() - start.getX(),
				finish.getY() - start.getY()).perform();
		Thread.sleep(5000);
		_driver.findElement(By.xpath("//strong[@id='drop-status']")).isDisplayed();
		_driver.quit();
	}

	public static void mouseClickAndHold() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://letcode.in/buttons");
		_actions = new Actions(_driver);
		_actions.clickAndHold(_driver.findElement(By.xpath("(//button[@class='button is-primary'])[2]"))).perform();
		Thread.sleep(5000);
		_driver.findElement(By.xpath("//h2[text()='Button has been long pressed']")).isDisplayed();
		_driver.quit();
	}

	public static void mouseRightClick() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		_actions = new Actions(_driver);
		_actions.contextClick(_driver.findElement(By.xpath("//span[contains(@class,'context-menu-one')]"))).perform();
		Thread.sleep(2000);
		_driver.findElement(By.cssSelector(".context-menu-item.context-menu-icon.context-menu-icon-copy"))
				.isDisplayed();
		_driver.quit();
	}

	public static void mouseDoubleClick() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://demo.guru99.com/test/simple_context_menu.html");
		_actions = new Actions(_driver);
		_actions.doubleClick(_driver.findElement(By.xpath("//button[contains(text(),'Double-Click Me To See Alert')]")))
				.perform();
		Thread.sleep(2000);
		Alert alert = _driver.switchTo().alert();
		System.out.println(alert.getText());
		alert.dismiss();
		_driver.quit();
	}

	public static void mouseClick() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		_actions = new Actions(_driver);
		_actions.click(_driver.findElement(By.xpath("//input[@id='clickable']"))).perform();
		Thread.sleep(2000);
		_driver.findElement(By.xpath("//strong[@id='click-status']")).isDisplayed();
		_driver.quit();
	}

	public static void mouseMoveByOffset() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		_actions = new Actions(_driver);
		_actions.moveToElement(_driver.findElement(By.id("mouse-tracker")), 20, 0).perform();
		Thread.sleep(5000);
		_driver.quit();
	}

	public static void mouseBackClick() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		_driver.findElement(By.id("click")).click();
		Assert.assertEquals(_driver.getTitle(), "We Arrive Here");
		Thread.sleep(5000);
		mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		sequence = new Sequence(mouse, 0).addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg()));
		((RemoteWebDriver) _driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(_driver.getTitle(), "BasicMouseInterfaceTest");
		Thread.sleep(5000);
		_driver.quit();
	}

	public static void mouseForwardClick() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		_driver.findElement(By.id("click")).click();
		Assert.assertEquals(_driver.getTitle(), "We Arrive Here");
		Thread.sleep(3000);
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg()));
		((RemoteWebDriver) _driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(_driver.getTitle(), "BasicMouseInterfaceTest");
		Thread.sleep(3000);
		mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		sequence = new Sequence(mouse, 0).addAction(mouse.createPointerDown(PointerInput.MouseButton.FORWARD.asArg()))
				.addAction(mouse.createPointerUp(PointerInput.MouseButton.FORWARD.asArg()));
		((RemoteWebDriver) _driver).perform(Collections.singletonList(sequence));
		Assert.assertEquals(_driver.getTitle(), "We Arrive Here");
		Thread.sleep(2000);
		_driver.quit();
	}

	public static void mouseMoveByViewport() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 10, 12));
		((RemoteWebDriver) _driver).perform(Collections.singletonList(sequence));
		Thread.sleep(5000);
		_driver.quit();
	}
	
	public static void mouseMoveByCurrentPosition() throws InterruptedException {
		browserSetup();
		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
		PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "Default Mouse");
		Sequence sequence = new Sequence(mouse, 0)
				.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 10, 12));
		((RemoteWebDriver) _driver).perform(Collections.singletonList(sequence));
		new Actions(_driver).moveByOffset(12, 15).perform();
		String[] result = _driver.findElement(By.id("absolute-location")).getText().split(",");
		Assert.assertTrue(Math.abs(Integer.parseInt(result[0])-10-12) < 2);
		Assert.assertTrue(Math.abs(Integer.parseInt(result[1])-12-15) < 3);
		Thread.sleep(5000);
		_driver.quit();
	}

}
