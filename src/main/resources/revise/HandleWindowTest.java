package revise;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HandleWindowTest {

    static WebDriver driver;

    public static void main(String[] args) {
        browserView();
        browserSize();
        handleWindows();
        handleNewTab();
        handleNewWindow();
    }

    private static void browserLaunch() {
        driver = new ChromeDriver();
    }

    private static void browserView() {
        browserLaunch();
        driver.get("https://selenium.dev/");
        driver.manage().window().maximize();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.manage().window().minimize();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.close();
    }

    private static void browserSize() {
        browserLaunch();
        driver.get("https://selenium.dev/");
        Dimension dimension = driver.manage().window().getSize();
        System.out.println("Dimension");
        System.out.println(dimension.getWidth());
        System.out.println(dimension.getHeight());
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(500, 700));

        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        Point point = driver.manage().window().getPosition();
        System.out.println("Position");
        System.out.println(point.getX());
        System.out.println(point.getY());
        driver.manage().window().setPosition(new Point(20, 10));
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.close();
    }

    private static void handleWindows() {
        browserLaunch();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/windows");

        String parentHandle = driver.getWindowHandle();
        System.out.println(driver.getTitle());
        WebElement element = driver.findElement(By.xpath("//a[text()='Click Here']"));
        element.click();
        Set<String> handles = driver.getWindowHandles();
        for(String handle : handles) {
            if(!handle.equalsIgnoreCase(parentHandle)) {
                driver.switchTo().window(handle);
                System.out.println(driver.getTitle());
                Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
                driver.close();
            }
        }
        driver.switchTo().window("");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.close();
    }

    private static void handleNewTab() {
        browserLaunch();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/windows");
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://the-internet.herokuapp.com/windows/new");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.close();
    }

    private static void handleNewWindow() {
        browserLaunch();
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/windows");
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.navigate().to("https://the-internet.herokuapp.com/windows/new");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.quit();
    }

}
