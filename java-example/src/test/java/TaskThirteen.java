import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;


public class TaskThirteen {

    private WebDriver driver;
    private WebDriverWait wait;
    List<WebElement> prodList;
    WebElement productUnit, Cart, prodTable;
    int i, j, k, k1, p;
    String[] prodName;

    @Before
    public void start() {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    @Test
    public void Basket() throws InterruptedException {
        prodName = new String[3];

        for (i=0; i<3; i++) {
            driver.get("http://localhost/litecart/en/");
            wait.until(titleContains("Online Store"));

            prodList = driver.findElements(By.cssSelector("li.product"));

            p=1; j=0;
            while(p>0) {
                k=1; k1=1;
                productUnit = prodList.get(j);    // выбираем конкретный продукт
                prodName[i]=productUnit.findElement(By.cssSelector("div.name")).getText();
                // получаем имя продукта

                if(i==1) { // для 2-го товара
                    // проверяем, что выбранный товар не совпадает с предыдущим
                    k=prodName[i].compareToIgnoreCase(prodName[i-1]);
                }

                if (i==2) { // для 3-го товара
                    // проверяем, что выбранный товар не совпадает с предыдущими
                    k=prodName[i].compareToIgnoreCase(prodName[i-1]);
                    k1=prodName[i].compareToIgnoreCase(prodName[i-2]);
                }

                if((k==0)||(k1==0)) { j++; } // переходим на следующий продукт в списке
                else { p=0; }  // подходящий продукт найден - прерываем цикл
            }

            productUnit.click(); //щелкаем по странице продукта
            wait.until(titleContains(prodName[i]));

            Cart = wait.until(presenceOfElementLocated(By.id("cart"))); // нашли корзину
            k=prodName[i].compareToIgnoreCase("Yellow Duck");
            // Проверяем, что выбранный товар не Yellow Duck - требует доп. обработки
            if (k==0) {  // Обработка Yellow Duck - выбираем размер
                new Select(driver.findElement(By.name("options[Size]"))).selectByVisibleText("Medium +$2.50");
            }

            driver.findElement(By.name("add_cart_product")).click();
            // добавляем продукт в корзину

            wait.until(textToBePresentInElement(
                    Cart.findElement(By.cssSelector("span.quantity")),
                    Integer.toString(i+1)));
            // ждем изменения количества
        }

        driver.get("http://localhost/litecart/en/"); //открыть главную страницу магазина

        driver.findElement(By.id("cart")).click(); // открываем корзину
        wait.until(titleContains("Checkout")); // ожидаем открытия страницы корзины

        for(int n=1;n<=3;n++) {
            prodTable = wait.until(presenceOfElementLocated(By.id("order_confirmation-wrapper")));
            // находим таблицу товаров в корзине

            prodList = driver.findElements(By.cssSelector("li.shortcut"));
            if(prodList.size()>0) {  prodList.get(0).click();
            /*
              Поскольку изначально картинки продуктов на экране сменяются, мы просто определяем
              список маленьких изображений продуктов и щелкаем по нему.
              При этом изображение продукта и все связанные с ним служебные кнопки фиксируются.
            */
            }

            driver.findElement(By.name("remove_cart_item")).click();
            // кликнуть по кнопке удаления товара Remove
            wait.until(stalenessOf(prodTable));  // ожидаем обновления таблицы со списком товаров
        }

        driver.get("http://localhost/litecart/en/");
        wait.until(titleContains("Online Store"));

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}