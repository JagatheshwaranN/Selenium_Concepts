package concepts.jsexecutor.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@SuppressWarnings("DataFlowIssue")
public class ViewPortUtil {


    /**
     * Determines whether the specified element is within the visible viewport of the browser.
     *
     * @param element the WebElement to check
     * @param driver  the WebDriver instance
     * @return true if the element is visible in the viewport, false otherwise
     */
    public static boolean inViewport(WebElement element, WebDriver driver) {
        // Define a JavaScript script to check if the element is within the viewport
        String script = """
        // Calculate the cumulative offset positions of the element and its ancestors
        for (var e = arguments[0], f = e.offsetTop, t = e.offsetLeft, o = e.offsetWidth, n = e.offsetHeight;
            e.offsetParent;) {
            f += (e = e.offsetParent).offsetTop;
            t += e.offsetLeft;
        }

        // Check if the element's top and left positions are within the viewport's boundaries
        return f < window.pageYOffset + window.innerHeight &&
            t < window.pageXOffset + window.innerWidth &&
            f + n > window.pageYOffset &&
            t + o > window.pageXOffset;
    """;

        // Execute the JavaScript script and return the result (whether the element is in viewport)
        return (boolean) ((JavascriptExecutor) driver).executeScript(script, element);
    }
}
