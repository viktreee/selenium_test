import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class TaskEleven {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement select;
    String FirstName, LastName, emailName, testString;

    @Before
    public void start() {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    }

    @Test
    public void RegistrationUser() {
        driver.get("http://localhost/litecart/en/");
        wait = new WebDriverWait(driver,10);


        driver.findElement(By.cssSelector("[href*=create_account]")).click();
        wait = new WebDriverWait(driver,10);

        //используем текущее время - добавляем его к имени и получаем уникальный e-mail и пароль каждый раз
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(calendar.HOUR_OF_DAY);
        int m = calendar.get(calendar.MINUTE);
        int s = calendar.get(calendar.SECOND);

        FirstName = "Viktor";
        LastName = "Petukhov";
        emailName = FirstName + Integer.toString(h) + Integer.toString(m) + Integer.toString(s);

        driver.findElement(By.className("select2-selection__arrow")).click();
        driver.findElement(By.className("select2-search__field")).sendKeys("United States\n");
        driver.findElement(By.name("firstname")).sendKeys(FirstName);
        driver.findElement(By.name("lastname")).sendKeys(LastName);
        driver.findElement(By.name("address1")).sendKeys("titova 8-22");
        driver.findElement(By.name("postcode")).sendKeys("55555");
        driver.findElement(By.name("city")).sendKeys("Moscow");
        driver.findElement(By.name("email")).sendKeys(emailName+"@mail.com");
        driver.findElement(By.name("phone")).sendKeys("555-484");
        driver.findElement(By.name("password")).sendKeys(emailName);
        driver.findElement(By.name("confirmed_password")).sendKeys(emailName);

        driver.findElement(By.name("create_account")).click();
        wait = new WebDriverWait(driver,10);

        //Выбираем значение в появившемся выпадающем списке
        WebElement selectElem = driver.findElement(By.name("zone_code"));
        Select select = new Select(selectElem);
        selectElem.click();
        select.selectByVisibleText("Arizona");

        //Снова вводим пароль
        driver.findElement(By.name("password")).sendKeys(emailName);
        driver.findElement(By.name("confirmed_password")).sendKeys(emailName);

        driver.findElement(By.name("create_account")).click();
        wait = new WebDriverWait(driver,10);


        // Проверим что залогинись
        testString = driver.findElement(By.cssSelector("[id=box-account] .title")).getText();

        Assert.assertTrue(testString.compareTo("Account")==0);

        // Разлогиниваемся
        driver.findElement(By.cssSelector("[href*=logout]")).click();

        wait = new WebDriverWait(driver,10);

        // Проверяем что мы разлогинились
        testString = driver.findElement(By.cssSelector("[id=box-account-login] .title")).getText();

        Assert.assertTrue(testString.compareTo("Login")==0);

        // Логинимся заново под созданным пользователем
        driver.findElement(By.name("email")).sendKeys(emailName+"@mail.com");
        driver.findElement(By.name("password")).sendKeys(emailName);
        driver.findElement(By.name("login")).click();

        wait = new WebDriverWait(driver,10);

        // Проверяем что мы залогинились
        testString = driver.findElement(By.cssSelector("[id=box-account] .title")).getText();
        Assert.assertTrue(testString.compareTo("Account")==0);

        // Разлогиниваемся
        driver.findElement(By.cssSelector("[href*=logout]")).click();

        // Проверяем что мы разлогинились
        testString = driver.findElement(By.cssSelector("[id=box-account-login] .title")).getText();
        Assert.assertTrue(testString.compareTo("Login")==0);

        System.out.println("--------------------Сценарий успешно завершён--------------------");

    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}