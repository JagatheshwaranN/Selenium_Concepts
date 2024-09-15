package revise;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HandleDriverOptionTest {

    protected static WebDriver driver;

    public static void main(String[] args) {
        //driverOptionsTest();
        htmlDriverTest();
    }

    private static void driverOptionsTest() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("incognito");
        chromeOptions.addArguments("start-maximized");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.google.com/");
        System.out.println(chromeOptions.getBrowserName());
        Capabilities capabilities = ((RemoteWebDriver)driver).getCapabilities();
        System.out.println(capabilities.getBrowserVersion());
        System.out.println(driver.getTitle());
        driver.close();
    }

    private static void htmlDriverTest() {
        HtmlUnitDriver driver = new HtmlUnitDriver();
        driver.get("https://www.google.com/");
        System.out.println(driver.getTitle());
        driver.close();
    }
}
