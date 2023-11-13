package concepts.dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

import java.util.List;

public class DeselectMultipleOptionTest {

	// Declare a WebDriver instance to interact with the web browser.
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
		driver = DriverConfiguration.browserSetup();
	}

	@AfterMethod
	public void tearDown() {
		// Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
		if (driver != null) {
			// If a WebDriver instance exists, quit/close the browser session.
			driver.quit();
		}
	}

	@Test(priority = 1, enabled = true)
	public void selectDropDownSingleOptionByVisibleText() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		WebElement dropDown = driver.findElement(By.cssSelector("#fruits"));
		new Select(dropDown).selectByVisibleText("Apple");
		Assert.assertTrue(dropDown.getText().contains("Apple"));
	}

	@Test(priority = 2, enabled = true)
	public void selectDropDownSingleOptionByValue() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		new Select(driver.findElement(By.cssSelector("#fruits"))).selectByValue("1");
	}

	@Test(priority = 3, enabled = true)
	public void selectDropDownSingleOptionByIndex() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		new Select(driver.findElement(By.cssSelector("#fruits"))).selectByIndex(2);
	}

	@Test(priority = 4, enabled = true)
	public void selectDropDownMultipleOptions() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		Select selectObj = new Select(driver.findElement(By.cssSelector("#superheros")));
		boolean isMultiSelect = selectObj.isMultiple();
		System.out.println("DropDown Has Multi Select Option : " + isMultiSelect);
		selectObj.selectByValue("am");
		selectObj.selectByValue("aq");
	}

	@Test(priority = 5, enabled = true)
	public void deSelectDropDownMultipleOptions() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		Select selectObj = new Select(driver.findElement(By.cssSelector("#superheros")));
		boolean isMultiSelect = selectObj.isMultiple();
		System.out.println("DropDown Has Multi Select Option : " + isMultiSelect);
		selectObj.selectByIndex(0);
		selectObj.selectByValue("aq");
		selectObj.selectByVisibleText("The Avengers");
		selectObj.deselectByIndex(0);
		selectObj.deselectByValue("aq");
		selectObj.deselectByVisibleText("The Avengers");
	}

	@Test(priority = 6, enabled = true)
	public void dropDownOptions() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		List<WebElement> dropDownOptions = new Select(driver.findElement(By.cssSelector("#superheros"))).getOptions();
		dropDownOptions.forEach(e -> System.out.println(e.getText()));
	}

	@Test(priority = 7, enabled = true)
	public void dropDownSelectedOption() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get("https://letcode.in/dropdowns");
		Select selectObject = new Select(driver.findElement(By.cssSelector("#superheros")));
		selectObject.selectByValue("aq");
		List<WebElement> dropDownOptions = selectObject.getAllSelectedOptions();
		dropDownOptions.forEach(e -> System.out.println(e.getText()));
	}

	@Test(priority = 8, enabled = true)
	public void selectDisabledOption() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get(
				"file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/disabledSelect.html");
		Select selectObject = new Select(driver.findElement(By.name("single_disabled")));
		Assert.assertThrows(UnsupportedOperationException.class, () -> {
			selectObject.selectByValue("disabled");
		});
	}

	@Test(priority = 9, enabled = true)
	public void chooseOptionFromDropdownWithoutSelectClass() {
		driver.get("file:///D:/Environment_Collection/Eclipse_Env/Workspace/Selenium_Concepts/src/main/resources/supportFiles/Dropdown.html");
		WebElement dropDown = driver.findElement(By.xpath("//div[@class='select-selected']"));
		dropDown.click();
		List<WebElement> dropDownOptions = driver.findElements(By.xpath("//ul[@class='select-items']//li"));
		boolean flag = false;
		for (WebElement option : dropDownOptions) {
			if (option.getText().equalsIgnoreCase("Google Chrome")) {
				flag = true;
				option.click();
				break;
			}
		}
		if (!flag)
			System.out.println("The option is not in the dropdown list");
	}

}
