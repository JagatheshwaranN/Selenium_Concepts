package revise;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandleElementTest {

    protected static WebDriver driver;

    public static void main(String[] args) {
        elementTest();
    }

    private static void browserLaunch() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static void elementTest() {
        browserLaunch();
        driver.get("https://www.selenium.dev/selenium/web/inputs.html");
        WebElement input = driver.findElement(By.xpath("//input[@name='no_type']"));
        System.out.println(input.getDomAttribute("name"));
        System.out.println(input.getAccessibleName());
        System.out.println(input.getAriaRole());
        System.out.println(input.getDomProperty("value"));
        System.out.println(input.getRect().getX());
        System.out.println(input.getRect().getY());
    }
}
