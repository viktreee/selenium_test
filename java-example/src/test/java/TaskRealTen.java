import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import javafx.scene.paint.Color;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class TaskRealTen {

    private WebDriver driver;
    private WebDriverWait wait;
    WebElement product;
    String[] regularPrice1, regularPrice2, promotionalPrice1, promotionalPrice2;
    String nameOfItem1, nameOfItem2;
    float size1, size2;


    @Before
    public void start() {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    }

    private boolean isGrey(Color color) {
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();
        return (r == g & g == b);
    }

    private boolean isRed(Color color) {
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();
        return (g == 0 & b == 0 & r != 0);
    }

    @Test
    public void CorrectProductPage() {


        driver.get("http://localhost/litecart/en/");
        wait = new WebDriverWait(driver,10);

        //Берем первый продукт в блоке campaigns
        product = driver.findElement(By.cssSelector("[id=box-campaigns] li.product"));
        //Сохраняем название продукта
        nameOfItem1 = product.findElement(By.cssSelector(".name")).getText();
        System.out.println("Название продукта на основной странице = " + nameOfItem1);


        regularPrice1 = new String[5];
        promotionalPrice1 = new String[5];

        //Параметры основной цены

        //Текст основной цены
        regularPrice1[0] = product.findElement(By.cssSelector(".regular-price")).getText();

        //Цвет шрифта основной цены
        regularPrice1[1] = product.findElement(By.cssSelector(".regular-price")).getCssValue("color");
        System.out.println("Цвет шрифта обычной цены = " + regularPrice1[1]);
        Color testColor1 = Color.valueOf(driver.findElement(By.cssSelector(".regular-price")).getCssValue("color"));
        Assert.assertTrue("Ошибка: цвет шрифта обычной цены не серый!",isGrey(testColor1));

        //Жирность шрифта основной цены
        regularPrice1[2] = product.findElement(By.cssSelector(".regular-price")).getCssValue("font-weight");

        //Оформление шрифта основной цены
        regularPrice1[3] = product.findElement(By.cssSelector(".regular-price")).getCssValue("text-decoration");
        System.out.println("Оформление шрифта обычной цены = " + regularPrice1[3]);
        Assert.assertTrue("Ошибка: обычная цена не перечёркнута!",regularPrice1[3].compareTo("line-through") == 0);

        //Выясняем размер шрифта основной цены
        regularPrice1[4] = product.findElement(By.cssSelector(".regular-price")).getCssValue("font-size");
        size1 = Float.parseFloat(regularPrice1[4].replaceAll("px",""));

        //Параметры акционной цены

        //Текст акционной цены
        promotionalPrice1[0]= product.findElement(By.cssSelector(".campaign-price")).getText();
        //Цвет акционной цены
        promotionalPrice1[1]= product.findElement(By.cssSelector(".campaign-price")).getCssValue("color");
        System.out.println("Цвет шрифта акционной цены = " + promotionalPrice1[1]);
        Color testColor2 = Color.valueOf(driver.findElement(By.cssSelector(".regular-price")).getCssValue("color"));
        Assert.assertTrue("Ошибка: цвет шрифта акционной цены не красный!",isRed(testColor2));

        //Жирность шрифта акционной цены
        promotionalPrice1[2]= product.findElement(By.cssSelector(".campaign-price")).getCssValue("font-weight");
        System.out.println("Жирность акционного шрифта = " + promotionalPrice1[2]);
        Assert.assertTrue("Ошибка: шрифт акционной цены не жирный!", Integer.valueOf (promotionalPrice1[2]) > 599);

        //Оформление шрифта акционной цены
        promotionalPrice1[3]= product.findElement(By.cssSelector(".campaign-price")).getCssValue("text-decoration");
        //Размер шрифта акционной цены
        promotionalPrice1[4]= product.findElement(By.cssSelector(".campaign-price")).getCssValue("font-size");
        size2 = Float.parseFloat(promotionalPrice1[4].replaceAll("px",""));

        //Проверка того, что акционная цена крупнее, чем обычная
        Assert.assertTrue("Ошибка: размер шрифта основной цены больше размера шрифта акционной цены!",size1<size2);

        //Идём на страницу продукта
        product.click();
        wait = new WebDriverWait(driver,10);

        product = driver.findElement(By.cssSelector("[id=box-product]"));
        nameOfItem2 = product.findElement(By.cssSelector("[itemprop=name]")).getText();
        System.out.println("Название продукта на странице продукта = " + nameOfItem2);

        //Сравниваем названия продуктов
        Assert.assertTrue("Ошибка: название товара на основной странице не совпадает с названием на странице товара!",nameOfItem1.compareTo(nameOfItem2)== 0);

        regularPrice2 = new String[5];
        promotionalPrice2 = new String[5];

        //Параметры обычной цены

        //Текст обычной цены
        regularPrice2[0]= product.findElement(By.cssSelector(".regular-price")).getText();
        //Цвет шрифта обычной цены
        regularPrice2[1]= product.findElement(By.cssSelector(".regular-price")).getCssValue("color");
        System.out.println("Цвет обычной цены = " + regularPrice2[1]);
        Color testColor3 = Color.valueOf(driver.findElement(By.cssSelector(".regular-price")).getCssValue("color"));
        Assert.assertTrue("Ошибка: цвет шрифта обычной цены не серый!",isGrey(testColor3));

        //Жирность шрифта обычной цены
        regularPrice2[2]= product.findElement(By.cssSelector(".regular-price")).getCssValue("font-weight");
        //Оформление шрифта обычной цены
        regularPrice2[3]= product.findElement(By.cssSelector(".regular-price")).getCssValue("text-decoration");
        System.out.println("Оформление шрифта = " + regularPrice2[3]);
        Assert.assertTrue("Ошибка, обычная цена не перечеркнута!", regularPrice2[3].compareTo("line-through") == 0);
        //Размер шрифта обычной цены
        regularPrice2[4]= product.findElement(By.cssSelector(".regular-price")).getCssValue("font-size");
        size1 = Float.parseFloat(regularPrice2[4].replaceAll("px",""));

        //Параметры акционной цены

        //Текст акционной цены
        promotionalPrice2[0]= product.findElement(By.cssSelector(".campaign-price")).getText();
        //Цвет шрифта акционной цены
        promotionalPrice2[1]= product.findElement(By.cssSelector(".campaign-price")).getCssValue("color");
        System.out.println("Цвет шрифта акционной цены = " + promotionalPrice2[1]);
        Color testColor4 = Color.valueOf(driver.findElement(By.cssSelector(".regular-price")).getCssValue("color"));
        Assert.assertTrue("Ошибка: цвет шрифта акционной цены не красный!",isRed(testColor4));

        //Жирность шрифта акционной цены
        promotionalPrice2[2]= product.findElement(By.cssSelector(".campaign-price")).getCssValue("font-weight");
        System.out.println("Жирность акционного шрифта = " + promotionalPrice2[2]);
        Assert.assertTrue( "Ошибка: шрифт акционной цены не жирный!",Integer.valueOf(promotionalPrice2[2]) > 599);
        //Оформление шрифта акционной цены
        promotionalPrice2[3]= product.findElement(By.cssSelector(".campaign-price")).getCssValue("text-decoration");
        //Размер шрифта акционной цены
        promotionalPrice2[4]= product.findElement(By.cssSelector(".campaign-price")).getCssValue("font-size");
        size2 = Float.parseFloat(promotionalPrice2[4].replaceAll("px",""));

        //Проверка того, что размер шрифта акционной цены крупнее, чем у обычной
        Assert.assertTrue("Ошибка: размер шрифта акционной цены меньше, чем у обычной!",size1<size2);

        for(int i = 0; i<1; i++) {
            Assert.assertTrue("Ошибка: простая цена товара на основной странице отличается от цены на странице товара!",regularPrice1[i].compareTo(regularPrice2[i])== 0);
            Assert.assertTrue("Ошибка: акционная цена товара на основной странице отличается от цены на странице товара!",promotionalPrice1[i].compareTo(promotionalPrice2[i])== 0);
        }
        System.out.println("------------------Тест успешно пройден------------------");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}