package concepts.eventlistener;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

@SuppressWarnings("ALL")
public class WebDriverEventListener implements WebDriverListener {
	
	public void beforeGet(WebDriver driver, String url) {
		System.out.println("Before performing the GET with driver " + driver.toString() + " and the URL is " + url);
	}

	public void afterGet(WebDriver driver, String url) {
		System.out.println("After performing the GET with driver " + driver.toString() + " and the URL is " + url);
	}

	public void beforeGetCurrentUrl(WebDriver driver) {
		System.out.println("Before performing the BeforeGetCurrentUrl with driver " + driver.toString());
	}

	public void afterGetCurrentUrl(String result, WebDriver driver) {
		System.out.println(
				"After performing the AfterGetCurrentUrl with driver " + driver.toString() + " and URL is " + result);
	}

	public void beforeGetTitle(WebDriver driver) {
		System.out.println("After performing the BeforeGetTitle with driver " + driver.toString());
	}

	public void afterGetTitle(WebDriver driver, String result) {
		System.out.println(
				"After performing the AfterGetTitle with driver " + driver.toString() + " and Title is " + result);
	}

	public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
		System.out.println("After performing the BeforeSendKeys with driver " + element.toString() + " and value is "
				+ Arrays.toString(keysToSend));
	}

	public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
		System.out.println("After performing the AfterSendKeys with driver " + element.toString() + " and value is "
				+ Arrays.toString(keysToSend));
	}

	public void beforeClear(WebElement element) {
		System.out.println("After performing the BeforeClear with driver " + element.toString());
	}

	public void afterClear(WebElement element) {
		System.out.println("After performing the AfterClear with driver " + element.toString());
	}

	public void beforeClick(WebElement element) {
		System.out.println("After performing the BeforeClick with driver " + element.toString());
	}

	public void afterClick(WebElement element) {
		System.out.println("After performing the AfterClick with driver " + element.toString());
	}

	public void beforeClose(WebDriver driver) {
		System.out.println("After performing the BeforeClose with driver " + driver.toString());
	}

	public void afterClose(WebDriver driver) {
		System.out.println("After performing the AfterClose with driver " + driver.toString());
	}

}

