import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class TaskEight {
    private WebDriver driver;
    private WebDriverWait wait;
    int prodQuantity, stickerQuantity;
    WebElement productUnit;
    List<WebElement> prodList, stickerList;

    @Before
    public void start() {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    }

    @Test
    public void СheckingStickers() throws InterruptedException {
        driver.get("http://localhost/litecart/en/");
        wait = new WebDriverWait(driver,10);

        prodList = driver.findElements(By.id("li.product"));
        prodQuantity=prodList.size();

        for (int i=0; i<prodQuantity; i++  ) {
            prodList = driver.findElements(By.id("li.product"));
            productUnit = prodList.get(i);

            stickerList = driver.findElements(By.cssSelector("li.product .sticker"));
            stickerQuantity = stickerList.size();
            Assert.assertTrue(stickerQuantity == 1);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}