package com.qa.selenium.scenarios;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class _15_Handle_TypeAhead_Suggestion {

	private WebDriver driver;
	private WebDriverWait wait;

	@Test(priority = 1, enabled = true)
	private void selecValueFromTypeAheadSuggestion() {
		String suggestionToSelect = "Scala";
		String input = "sca";
		browserSetup();
		driver.get("http://jqueryui.com/autocomplete/");
		driver.switchTo().frame(driver.findElement(By.className("demo-frame")));
		WebElement searchBar = driver.findElement(By.id("tags"));
		typeCharsOneByOne(searchBar, input, 2);
		WebElement typeAheadSuggestion = driver.findElement(By.id("ui-id-1"));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(typeAheadSuggestion));
		List<WebElement> suggestionList = typeAheadSuggestion.findElements(By.tagName("li"));
		for (WebElement suggestion : suggestionList) {
			if (suggestion.getText().equals(suggestionToSelect)) {
				suggestion.click();
				break;
			}
		}
		waitForSomeTime();
		driver.close();
	}

	private void typeCharsOneByOne(WebElement element, String value, int option) {
		if (option == 1) {
			for (int i = 0; i < value.length(); i++) {
				element.sendKeys(value.substring(i, i + 1));
				try {
					Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(1));
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		} else {
			for (int i = 0; i < value.length(); i++) {
				char c = value.charAt(i);
				String s = new StringBuilder().append(c).toString();
				element.sendKeys(s);
				try {
					Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(1));
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private void waitForSomeTime() {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(3));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
