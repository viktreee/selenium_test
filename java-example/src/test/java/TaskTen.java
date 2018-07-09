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

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class TaskTen {

    private WebDriver driver;
    private WebDriverWait wait;
    int numberOfCountries, numberOfZones, numberOfGeozones;
    int[] zones;
    WebElement countryRow, zoneRow, geoZoneRow;
    List<WebElement> countryRows, zoneRows, geoZoneRows;
    String[] countryName, zoneName;
    int a, az;

    public static int testAlphabet (String[] testArr, int arrSize) {
        int isAlphab = 1;
        for (int i = 1; i < arrSize; i++) {
            int k;
            k = testArr[i-1].compareToIgnoreCase(testArr[i]);
            if(k >= 0) isAlphab = -1;
        }
        return isAlphab;
    }

    @Before
    public void start() {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    }

    @Test
    public void CheckCountries() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        countryRows = driver.findElements(By.cssSelector("[name=countries_form] .row"));

        numberOfCountries = countryRows.size();
        countryName = new String[numberOfCountries];
        zones = new int[numberOfCountries];

        for (int i = 0; i < numberOfCountries; i++) {
            countryRow = countryRows.get(i);
            countryName[i] = countryRow.findElement(By.cssSelector("a")).getText();
            zones[i] = Integer.parseInt(countryRow.findElement(By.cssSelector("td:nth-child(6)")).getText());
        }

        a = testAlphabet(countryName,numberOfCountries);

        Assert.assertTrue(a == 1);

        for (int i = 0; i < numberOfCountries; i++) {
            if (zones[i]>0) {
                countryRows = driver.findElements(By.cssSelector("[name=countries_form] .row"));
                countryRow = countryRows.get(i);
                countryRow.findElement(By.cssSelector("a")).click();
                wait = new WebDriverWait(driver,10);

                zoneRows = driver.findElements(By.cssSelector("[id=table-zones] tr"));
                numberOfZones = zoneRows.size() - 2;
                zoneName = new String[numberOfZones];

                for (int j = 1; j <= numberOfZones; j++) {
                    zoneRow = zoneRows.get(j);
                    zoneName[j-1] = zoneRow.findElement(By.cssSelector("td:nth-child(3)")).getText();
                }
                az = testAlphabet(zoneName,numberOfZones);
                Assert.assertTrue(az == 1);

                driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
            }
        }

        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

        geoZoneRows = driver.findElements(By.cssSelector("[name=geo_zones_form] .row"));
        numberOfGeozones = geoZoneRows.size();

        for (int i = 0; i < numberOfGeozones; i++) {
            geoZoneRows = driver.findElements(By.cssSelector("[name=geo_zones_form] .row"));
            geoZoneRow = geoZoneRows.get(i);
            geoZoneRow.findElement(By.cssSelector("a")).click();
            wait = new WebDriverWait(driver,10);

            zoneRows = driver.findElements(By.cssSelector("[id=table-zones] tr"));
            numberOfZones = zoneRows.size() - 2;
            zoneName = new String[numberOfZones];

            for (int j = 1; j <= numberOfZones; j++) {
                zoneRow = zoneRows.get(j);
                zoneName[j-1] = zoneRow.findElement(
                        By.cssSelector("[id=table-zones] tr td:nth-child(3) [selected=selected]")).
                        getAttribute("textContent");
            }
            az = testAlphabet(zoneName,numberOfZones);
            Assert.assertTrue(az == 1);
            driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        }
    }

    @After
    public void stop() {
        driver.get("http://localhost/litecart/admin/logout.php");
        driver.quit();
        driver = null;
    }
}