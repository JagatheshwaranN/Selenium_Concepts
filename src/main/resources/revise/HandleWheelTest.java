package revise;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;

public class HandleWheelTest {

    protected static WebDriver driver;

    public static void main(String[] args) {
        //scrollActions();
        //scrollOrigin();
        scrollOriginFromViewPort();
    }

    private static void browserLaunch() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private static void scrollActions() {
        browserLaunch();
        int xAxis = 0;
        driver.get("https://www.selenium.dev/");
        int yAxis = driver.findElement(By.xpath("(//h2[@class='selenium text-center'])[2]")).getRect().getY();
        new Actions(driver).scrollByAmount(xAxis, yAxis).perform();
        WebElement cardTitle = driver.findElement(By.xpath("//h2[@class='card-title']"));
        new Actions(driver).scrollToElement(cardTitle).perform();
    }

    private static void scrollOrigin() {
        browserLaunch();
        int xAxis = 0;
        driver.get("https://www.selenium.dev/");
        WebElement element = driver.findElement(By.xpath("//h2[@class='selenium text-center']"));
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(element);
        WebElement cardTitle = driver.findElement(By.xpath("//h2[@class='card-title']"));
        new Actions(driver).scrollFromOrigin(scrollOrigin, xAxis, cardTitle.getRect().getY()).perform();
    }

    private static void scrollOriginFromViewPort() {
        browserLaunch();
        int xAxis = 100;
        int yAxis = 100;
        driver.get("https://www.selenium.dev/");
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromViewport(xAxis, yAxis);
        WebElement cardTitle = driver.findElement(By.xpath("//h2[@class='card-title']"));
        new Actions(driver).scrollFromOrigin(scrollOrigin, xAxis, cardTitle.getRect().getY()).perform();
    }
}
