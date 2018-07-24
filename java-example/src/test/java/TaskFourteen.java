import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;


public class TaskFourteen {

    private WebDriver driver;
    private WebDriverWait wait;
    int countryQuantity, linksQuantity; // количество стран в списке
    int randomIndex;
    WebElement countryRow;  // строка по стране
    List<WebElement> countryRows, listLinks;  // список стран, список внешних ссылок
    String originalWindow, newWindow;
    Set<String> existingWindows;

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles=driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size()>0 ? handles.iterator().next():null;
            }
        };
    }

    @Before
    public void start() {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 20);

    }

    @Test
    public void CheckNewTabs() throws InterruptedException {

        //открыть страницу
        driver.get("http://localhost/litecart/admin/");
        driver.manage().window().maximize();
        //найти поле для ввода логина и ввести "admin"
        driver.findElement(By.name("username")).sendKeys("admin");
        //найти поле для ввода пароля и ввести "admin"
        driver.findElement(By.name("password")).sendKeys("admin");
        //найти кнопку логина и нажать на нее
        driver.findElement(By.name("login")).click();
        //подождать пока не загрузится страница с заголовком "My Store"
        wait.until(titleIs("My Store"));


        //открыть страницу со списком стран
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        // ждем загрузки страницы
        wait.until(titleContains("Countries"));

        //определение списка строк в таблице стран
        countryRows = driver.findElements(By.cssSelector("[name=countries_form] .row"));

        // сохраняем количество строк, т.е. стран в списке
        countryQuantity=countryRows.size();

        final Random random = new Random();

        // выбираем номер случайно страны из списка
        randomIndex = random.nextInt(countryQuantity-1);

        // выбираем случайную страну
        countryRow=countryRows.get(randomIndex);
        // открываем страницу выбранной страны
        countryRow.findElement(By.cssSelector("a")).click();
        // ждем загрузки страницы
        wait.until(titleContains("Edit Country"));

        // получаем список внешних ссылок
        listLinks = driver.findElements(By.cssSelector("form .fa-external-link"));

        // определяем количество ссылок
        linksQuantity = listLinks.size();

        for (int i=0; i<linksQuantity; i++) {

            // сохранили идентификатор текущего окна
            originalWindow=driver.getWindowHandle();
            // сохранили идентификаторы уже открытых окон
            existingWindows=driver.getWindowHandles();

            // кликаем по ссылке из найденного списка
            listLinks.get(i).click();
            // получаем идентификатор нового окна
            newWindow=wait.until(anyWindowOtherThan(existingWindows));

            // переключаемся в новое окно
            driver.switchTo().window(newWindow);
            // закрываем окно
            driver.close();
            // вернулись в исходное окно
            driver.switchTo().window(originalWindow);
        }
    }

    @After
    public void stop() {
        driver.get("http://localhost/litecart/admin/logout.php");
        driver.quit();
        driver = null;
    }


}