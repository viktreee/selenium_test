import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class TaskSeven {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    }

    @Test
    public void LeftMenu() throws InterruptedException {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));


        List<WebElement> menuItems = driver.findElements(By.id("app-"));
        List<WebElement> submenuItems;
        WebElement menuItem, submenuItem;
        // определяем список пунктов меню
        int numberOfMenuItems = menuItems.size(); // сохраняем количество пунктов меню

        for (int i = 0; i < numberOfMenuItems; i++  ) {  //идём по пунктам меню
            menuItems = driver.findElements(By.id("app-"));
            menuItem = menuItems.get(i); //берём пункт меню
            wait = new WebDriverWait(driver,10);
            menuItem.click();  // кликаем по выбранному по меню
            
            menuItems = driver.findElements(By.id("app-")); //обновляем список
            menuItem = menuItems.get(i); //выбираем пункт меню
            // определение списка пунктов подменю
            submenuItems = menuItem.findElements(By.cssSelector("[id^=doc-]"));
            int subnumberOfMenuItems = submenuItems.size(); // сохраняем количество пунктов подменю

            if(subnumberOfMenuItems>0) { //подменю есть
                for (int j = 0; j < subnumberOfMenuItems; j++) {
                    menuItems = driver.findElements(By.id("app-"));  //обновляем список
                    menuItem = menuItems.get(i); //выбираем пункт меню
                    // определение списка пунктов подменю
                    submenuItems = menuItem.findElements(By.cssSelector("[id^=doc-]"));
                    submenuItem = submenuItems.get(j); //выбираем пункт подменю
                    submenuItem.click();  //кликаем по подменю
                    driver.findElement(By.cssSelector("h1"));  //проверка наличия заголовка
                }
            } else {   // подменю нет
                driver.findElement(By.cssSelector("h1"));  //проверка наличия заголовка
            }
        }

    }



    @After
    public void stop() {
        driver.quit();
        driver = null;
    }


}