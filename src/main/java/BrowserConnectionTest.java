
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserConnectionTest {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption(
            "debuggerAddress", "127.0.0.1:9222"
        );

        WebDriver driver = new ChromeDriver(options);

        System.out.println(
            "Connected browser title: " + driver.getTitle()
        );
        System.out.println(
            "Connected browser URL: " + driver.getCurrentUrl()
        );

        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());


                System.out.println(
            "Offer countdown present: " +
            driver.findElement(By.tagName("body"))
                .getText()
                .contains("Offer Ends in")
        );

        System.out.println(
            "Tray headings found: " +
            driver.findElements(By.cssSelector("h2")).size()
        );

        System.out.println("Content tray titles:");

List<WebElement> trays = driver.findElements(
    By.cssSelector("section[data-component='media-rail']")
);

for (WebElement tray : trays) {
    WebElement heading = tray.findElement(By.cssSelector("h2"));
    System.out.println("- " + heading.getText().trim());
}

        // Do not call driver.quit() here.
        // This browser session was opened separately.
    }
}

