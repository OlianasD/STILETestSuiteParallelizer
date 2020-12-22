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

public class AddMultipleUsersTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters("port")
	public void setUp(String port) {
        driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://localhost:"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTAddMultipleUsers() throws Exception {
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("administrator");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("root");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Manage")).click();
		driver.findElement(By.linkText("Manage Users")).click();
		driver.findElement(By.cssSelector("td.form-title > form > input.button-small")).click();
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("user001");
		driver.findElement(By.name("realname")).clear();
		driver.findElement(By.name("realname")).sendKeys("user001");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user001@user.it");
		new Select(driver.findElement(By.name("access_level"))).selectByVisibleText("viewer");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Proceed")).click();
		driver.findElement(By.linkText("Manage Users")).click();
		driver.findElement(By.xpath("//input[@value='Create New Account']")).click();
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("user002");
		driver.findElement(By.name("realname")).clear();
		driver.findElement(By.name("realname")).sendKeys("user002");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user002@user.it");
		new Select(driver.findElement(By.name("access_level"))).selectByVisibleText("reporter");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Proceed")).click();
		driver.findElement(By.linkText("Manage Users")).click();
		driver.findElement(By.xpath("//input[@value='Create New Account']")).click();
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("user003");
		driver.findElement(By.name("realname")).clear();
		driver.findElement(By.name("realname")).sendKeys("user003");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user003@user.it");
		new Select(driver.findElement(By.name("access_level"))).selectByVisibleText("updater");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Proceed")).click();
		driver.findElement(By.linkText("Manage Users")).click();
		assertEquals("user001", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[4]/td[1]/a")).getText());
		assertEquals("viewer", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[4]/td[4]")).getText());
		assertEquals("user002", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[5]/td[1]/a")).getText());
		assertEquals("reporter", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[5]/td[4]")).getText());
		assertEquals("user003", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[6]/td[1]/a")).getText());
		assertEquals("updater", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[6]/td[4]")).getText());
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
