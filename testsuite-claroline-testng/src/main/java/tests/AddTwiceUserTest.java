package tests;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class AddTwiceUserTest {

	private WebDriver driver;

	@BeforeMethod
    @Parameters("port")
	public void setUp(String port) throws Exception {
		driver = DriverProvider.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:"
                + port + "/claroline11110/claroline/index.php");
	}

	@Test
	public void testClarolineAddTwiceUser() throws Exception {
		driver.findElement(By.id("login")).clear();
		driver.findElement(By.id("login")).sendKeys("admin");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='loginBox']/form/fieldset/button")).click();
		driver.findElement(By.linkText("Platform administration")).click();
		driver.findElement(By.linkText("Create user")).click();
		driver.findElement(By.id("lastname")).clear();
		driver.findElement(By.id("lastname")).sendKeys("Name001");
		driver.findElement(By.id("firstname")).clear();
		driver.findElement(By.id("firstname")).sendKeys("Firstname001");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("user001");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password001");
		driver.findElement(By.id("password_conf")).clear();
		driver.findElement(By.id("password_conf")).sendKeys("password001");
		driver.findElement(By.id("student")).click();
		driver.findElement(By.id("applyChange")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id='claroBody']/div[2]/div[1]")).getText()
				.contains("This username is already taken"));
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}
}
