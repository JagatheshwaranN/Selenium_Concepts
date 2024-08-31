package revise;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HandleDropdownTest {

    static WebDriver driver;

    public static void main(String[] args) {
        //handleDropUsingIndex();
        //handleMultiDropDown();
        handleCustomDropdown();
    }

    private static void browserLaunch() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private static void handleDropUsingIndex() {
        browserLaunch();
        driver.get("https://letcode.in/dropdowns");
        new Select(driver.findElement(By.id("fruits"))).selectByIndex(1);
        Uninterruptibles.sleepUninterruptibly(3000, TimeUnit.MILLISECONDS);
    }

    private static void handleMultiDropDown() {
        browserLaunch();
        driver.get("https://letcode.in/dropdowns");
        Select multi = new Select(driver.findElement(By.id("superheros")));
        System.out.println(multi.isMultiple());
        multi.selectByIndex(0);
        multi.selectByValue("aq");
        multi.selectByVisibleText("The Avengers");
        Uninterruptibles.sleepUninterruptibly(3000, TimeUnit.MILLISECONDS);

        List<WebElement> options = multi.getOptions();

        System.out.println("*** All Options ***");
        for (WebElement option : options) {
            System.out.println(option.getText());
        }

        System.out.println("*** Selected Options ***");
        List<WebElement> selectedOptions = multi.getAllSelectedOptions();
        for(WebElement option : selectedOptions){
            System.out.println(option.getText());
        }

        System.out.println("*** First Selected Option ***");
        WebElement firstOption = multi.getFirstSelectedOption();
        System.out.println(firstOption.getText());

        System.out.println(multi.getOptions().get(0).getText());
        System.out.println(multi.getOptions().get(5).getText());
        System.out.println(multi.getOptions().get(multi.getOptions().size()-1).getText());

        multi.deselectByIndex(1);
        multi.deselectAll();
    }

    public static void handleCustomDropdown() {
        browserLaunch();
        driver.get("https://www.leafground.com/select.xhtml");
        driver.findElement(By.id("j_idt87:country_label")).click();
        List<WebElement> options = driver.findElements(By.xpath("//ul[@id='j_idt87:country_items']//li"));
        for(WebElement option : options) {
            if(option.getText().equalsIgnoreCase("India")) {
                System.out.println(option.getText());
                option.click();
                break;
            }
        }
        driver.navigate().to("https://letcode.in/dropdowns");
    }


}
