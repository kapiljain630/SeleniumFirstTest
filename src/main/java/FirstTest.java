
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstTest {
    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://hoichoi.tv/");

        System.out.println("Page title: " + driver.getTitle());
        System.out.println("Current URL: " + driver.getCurrentUrl());

        Thread.sleep(5000);

        driver.quit();
    }
}


