import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class FirstTest {
    private WebDriver driver;
    private WebDriverWait wait;



    @Before
    public void start () {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait (driver, 10);
    }

    @Test
    public void myFirstTest() throws InterruptedException {
        driver.get("https://yandex.ru");
        wait = new WebDriverWait (driver, 10);
        driver.findElement(By.name("text")). sendKeys("что такое селениум");
        wait = new WebDriverWait (driver, 10);
        driver.findElement(By.name("text")). sendKeys(Keys.ENTER);
        wait = new WebDriverWait (driver, 10);
        wait.until(titleIs("Яндекс"));
    }



    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
