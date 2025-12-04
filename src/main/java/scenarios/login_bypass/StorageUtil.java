package scenarios.login_bypass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class StorageUtil {

    private final JavascriptExecutor js;

    public StorageUtil(WebDriver driver) {
        this.js = (JavascriptExecutor) driver;
    }

    // ------------ Local Storage ------------
    public void setLocalStorageItem(String key, String value) {
        js.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);", key, value);
    }

    public void clearLocalStorage() {
        js.executeScript("window.localStorage.clear();");
    }

    // ------------ Session Storage ------------
    public void setSessionStorageItem(String key, String value) {
        js.executeScript("window.sessionStorage.setItem(arguments[0], arguments[1]);", key, value);
    }

    public void clearSessionStorage() {
        js.executeScript("window.sessionStorage.clear();");
    }
}
