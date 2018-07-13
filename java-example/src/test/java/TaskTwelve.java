import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class TaskTwelve {

    private WebDriver driver;
    private WebDriverWait wait;
    String enName, rusName, enProdName, rusProdName, validFrom, validTo, prefix;

    @Before
    public void start() {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    }

    @Test
    public void NewProduct() throws InterruptedException {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));

        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(calendar.HOUR_OF_DAY);
        int m = calendar.get(calendar.MINUTE);
        int s = calendar.get(calendar.SECOND);

        enName = "more duck";
        rusName = "ещё одна утка";
        prefix = Integer.toString(h) + Integer.toString(m) + Integer.toString(s);
        enProdName = enName + " " + prefix;
        rusProdName = rusName + " " + prefix;


        int y = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int d = calendar.get(calendar.DAY_OF_MONTH);

        validFrom = Integer.toString(y);
        validTo = Integer.toString(y+2);

        if(month < 10) {
            validFrom = validFrom+"-0"+Integer.toString(month);
            validTo = validTo+"-0"+Integer.toString(month);
        } else {
            validFrom = validFrom+"-"+Integer.toString(month);
            validTo = validTo+"-"+Integer.toString(month);
        }

        if(d < 10) {
            validFrom = validFrom+"-0"+Integer.toString(d);
            validTo = validTo+"-0"+Integer.toString(d);
        } else {
            validFrom = validFrom+"-"+Integer.toString(d);
            validTo = validTo+"-"+Integer.toString(d);
        }


        driver.findElement(By.cssSelector("[href*=catalog]")).click();
        // открыть каталог

        driver.findElement(By.linkText("Add New Product")).click();
        // открываем форму регистрации нового продукта

        wait = new WebDriverWait(driver,10);

        // устанавливаем статус Enabled
        driver.findElement(By.name("status")).click();

        // вводим название товара en
        driver.findElement(By.name("name[en]")).clear();
        driver.findElement(By.name("name[en]")).sendKeys(enProdName);

        // вводим название товара rus
        driver.findElement(By.name("name[ru]")).clear();
        driver.findElement(By.name("name[ru]")).sendKeys(rusProdName);

        // вводим код товара
        driver.findElement(By.name("code")).sendKeys(prefix + Keys.TAB);

        // устанавливаем категорию Rubber Ducks
        driver.findElement(By.xpath("(//input[@name='categories[]'])[2]")).click();

        // Устанавливаем группу Unisex
        driver.findElement(By.xpath("(//input[@name='product_groups[]'])[3]")).click();

        // устанавливаем количество 1
        driver.findElement(By.name("quantity")).clear();
        driver.findElement(By.name("quantity")).sendKeys("11");

        // устанавливаем дату начала годности
        driver.findElement(By.name("date_valid_from")).sendKeys(validFrom);

        // устанавливаем дату конца годности
        driver.findElement(By.name("date_valid_to")).sendKeys(validTo);

        // Прикладываем картинку
        File file = new File("src/test/resources/ytochka.jpg");
        driver.findElement(By.name("new_images[]")).sendKeys(file.getAbsolutePath());
        Thread.sleep(1000);

        // переходим на вкладку Information
        driver.findElement(By.linkText("Information")).click();
        wait = new WebDriverWait(driver,10);

        // выбираем корпорацию
        new Select(driver.findElement(By.name("manufacturer_id"))).selectByVisibleText("ACME Corp.");

        // Ввводим ключевое слово
        driver.findElement(By.name("keywords")).sendKeys("Уточко");

        // задаем краткое описание
        driver.findElement(By.name("short_description[en]")).sendKeys("Йа Уточко");
        driver.findElement(By.name("short_description[ru]")).sendKeys("Йа Уточко");

        // задаем описание
        driver.findElement(By.name("description[en]")).sendKeys(enProdName + " вдруг из маминой из спальни ололо пышь пышь реальне!");
        driver.findElement(By.name("description[ru]")).sendKeys(rusProdName + " вдруг из маминой из спальни ололо пышь пышь реальне!");

        // задаем заголовок
        driver.findElement(By.name("head_title[en]")).sendKeys(enProdName);
        driver.findElement(By.name("head_title[ru]")).sendKeys(rusProdName);

        // задаем метаописание
        driver.findElement(By.name("meta_description[en]")).sendKeys("метаописание..лолшто?");
        driver.findElement(By.name("meta_description[ru]")).sendKeys("метаописание..лолшто?");


        // переходим на вкладку Prices
        driver.findElement(By.linkText("Prices")).click();
        wait = new WebDriverWait(driver,10);

        // задаем цену
        driver.findElement(By.name("purchase_price")).clear();
        driver.findElement(By.name("purchase_price")).sendKeys("15");

        // выбираем валюту
        new Select(driver.findElement(By.name("purchase_price_currency_code"))).selectByVisibleText("Euros");

        // задаем цену в долларах
        driver.findElement(By.name("gross_prices[USD]")).clear();
        driver.findElement(By.name("gross_prices[USD]")).sendKeys("20");

        // задаем цену в EURO
        driver.findElement(By.name("gross_prices[EUR]")).clear();
        driver.findElement(By.name("gross_prices[EUR]")).sendKeys("25");

        // сохраняем продукт
        driver.findElement(By.name("save")).click();
        wait = new WebDriverWait(driver,10);

        Thread.sleep(2000);


        // Проверяем наличие такого элемента на странице
        driver.findElement(By.linkText(enProdName));
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}