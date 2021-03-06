package tests;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import utils.DriverProvider;
import utils.Properties;

public class AddUserTest {

    private WebDriver driver;

    @BeforeMethod
    @Parameters("port")
	public void setUp(String port) {
        driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://localhost:"+port+"/mantisbt");
    }

    @Test
    public void testMantisBTAddUser() {
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("administrator");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("root");
        driver.findElement(By.cssSelector("input.button")).click();
        driver.findElement(By.linkText("Manage")).click();
        driver.findElement(By.linkText("Manage Users")).click();
        driver.findElement(By.cssSelector("td.form-title > form > input.button-small")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("username001");
        driver.findElement(By.name("realname")).clear();
        driver.findElement(By.name("realname")).sendKeys("username");
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("username@username.it");
        driver.findElement(By.name("realname")).clear();
        driver.findElement(By.name("realname")).sendKeys("username001");
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("username001@username.it");
        new Select(driver.findElement(By.name("access_level"))).selectByVisibleText("updater");
        driver.findElement(By.cssSelector("input.button")).click();
        driver.findElement(By.xpath("html/body/div[2]/span[3]/a")).click();
        driver.findElement(By.linkText("Manage Users")).click();
        assertEquals("username001", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[4]/td[1]/a")).getText());
        assertEquals("username001", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[4]/td[2]")).getText());
        assertEquals("username001@username.it", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[4]/td[3]")).getText());
        assertEquals("updater", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[4]/td[4]")).getText());
        driver.findElement(By.linkText("Logout")).click();
    }

    @AfterMethod
	public void tearDown() {
        driver.quit();
    }
}
