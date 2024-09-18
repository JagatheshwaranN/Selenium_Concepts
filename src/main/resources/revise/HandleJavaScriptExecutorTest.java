package revise;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class HandleJavaScriptExecutorTest {

    protected static WebDriver driver;

    public static void main(String[] args) {
        jsActions();
    }

    private static void browserLaunch() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private static void jsActions() {
        browserLaunch();
        driver.get("https://www.selenium.dev/selenium/web/inputs.html");
        WebElement input = driver.findElement(By.name("no_type"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].clear()", input);
        js.executeScript("arguments[0].value=''", input);
        WebElement checkbox = driver.findElement(By.name("checkbox_input"));
        js.executeScript("arguments[0].click()", checkbox);
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        js.executeScript("window.location='https://www.selenium.dev/'");
        WebElement devPartners = driver.findElement(By.xpath("(//h2[@class='selenium text-center'])[position()=1]"));
        js.executeScript("arguments[0].scrollIntoView(true)", devPartners);
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        js.executeScript("document.location.reload()");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        js.executeScript("window.scrollBy(0, 500)");
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        driver.close();
    }

}
