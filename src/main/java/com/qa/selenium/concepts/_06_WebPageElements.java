package com.qa.selenium.concepts;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;

public class _06_WebPageElements extends _01_LaunchBrowser {

	public static WebDriver _driver = get_driver();

	public static void main(String[] args) {

		elementDisplay();
		elementEnabled();
		elementSelected();
		getElementTagName();
		getElementSize();
		getElementCSSProperties();
		getElementText();
		getElementAttribute();
	}

	public static void gotoTestSite() {

		_driver.manage().window().maximize();
		_driver.get("https://www.selenium.dev/selenium/web/inputs.html");
	}

	public static void elementDisplay() {

		gotoTestSite();
		boolean isElementDisplay = _driver.findElement(By.cssSelector("input[name='no_type']")).isDisplayed();
		System.out.println("Element Display Status ==> " + isElementDisplay);
	}

	public static void elementEnabled() {

		gotoTestSite();
		boolean isElementEnabled = _driver.findElement(By.cssSelector("input[name='no_type']")).isEnabled();
		System.out.println("Element Enable Status ==> " + isElementEnabled);
	}

	public static void elementSelected() {

		gotoTestSite();
		boolean isElementSelected = _driver.findElement(By.cssSelector("input[name='checkbox_input']")).isSelected();
		System.out.println("Element Selected Status ==> " + isElementSelected);
	}

	public static void getElementTagName() {

		gotoTestSite();
		String elementTag = _driver.findElement(By.cssSelector("input[name='number_input']")).getTagName();
		System.out.println("Element Tag Name ==> " + elementTag);
	}

	public static void getElementSize() {

		gotoTestSite();
		int elementHeight = _driver.findElement(By.cssSelector("input[name='no_type']")).getSize().getHeight();
		int elementWidth = _driver.findElement(By.cssSelector("input[name='no_type']")).getSize().getWidth();
		Rectangle rectangle = _driver.findElement(By.cssSelector("input[name='no_type']")).getRect();
		System.out.println("Element Dimension Height ==> " + elementHeight);
		System.out.println("Element Dimension Width ==> " + elementWidth);
		System.out.println("Element Height Using Rectangle Class ==> " + rectangle.getHeight());
		System.out.println("Element Width Using Rectangle Class ==> " + rectangle.getWidth());
		System.out.println("Element X-Axis Using Rectangle Class ==> " + rectangle.getX());
		System.out.println("Element Y-Axis Using Rectangle Class ==> " + rectangle.getY());
	}

	public static void getElementCSSProperties() {

		gotoTestSite();
		String elementColor = _driver.findElement(By.cssSelector("input[name='color_input']")).getCssValue("color");
		String elementBGColor = _driver.findElement(By.cssSelector("input[name='color_input']"))
				.getCssValue("background-color");
		System.out.println("Element Color ==> " + elementColor);
		System.out.println("Element Color ==> " + elementBGColor);
	}

	public static void getElementText() {

		gotoTestSite();
		String elementText = _driver.findElement(By.tagName("h1")).getText();
		System.out.println("Element Text Content ==> " + elementText);
	}
	
	public static void getElementAttribute() {

		gotoTestSite();
		String elementValue = _driver.findElement(By.cssSelector("input[name='no_type']")).getAttribute("value");
		String elementName = _driver.findElement(By.cssSelector("input[name='no_type']")).getAttribute("name");
		System.out.println("Element Attribute Value ==> " + elementValue);
		System.out.println("Element Attribute Name ==> " + elementName);
	}
}
