import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LoginAdmin {
    private WebDriver driver;
    private WebDriverWait wait;



    @Before
    public void start () {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait (driver, 10);
    }

    @Test
    public void myTestLoginAdmin() throws InterruptedException {
        driver.get("http://localhost/litecart/admin/");
        Thread.sleep(1000);
        driver.findElement(By.name("username")). sendKeys("admin");
        Thread.sleep(1000);
        driver.findElement(By.name("password")). sendKeys("admin");
        Thread.sleep(1000);
        driver.findElement(By.name("login")). click();
        Thread.sleep(3000);


        wait.until(titleIs("My Store"));
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
