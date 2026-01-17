package takeaway;

import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Objects;

public class WindowsHandler {

    public static void handler(WebDriver driver, String title, boolean shouldClose, Runnable action){
        List<String> allBrowserWindows = driver.getWindowHandles().stream().toList();
        String mainBrowserWindow = driver.getWindowHandle();
        for(var currentBrowserWindow : allBrowserWindows){
            if(!mainBrowserWindow.equalsIgnoreCase(currentBrowserWindow)){
                var childBrowserWindow = driver.switchTo().window(currentBrowserWindow);
                childBrowserWindow.manage().window().maximize();
                if(Objects.requireNonNull(driver.getTitle()).equalsIgnoreCase(title)){
                    action.run();
                }
                if(shouldClose) {
                    driver.close();
                }
                driver.switchTo().window(mainBrowserWindow);
            }
        }
    }

}
