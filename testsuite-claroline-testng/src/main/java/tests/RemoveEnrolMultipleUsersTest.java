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

public class RemoveEnrolMultipleUsersTest {

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
	public void testClarolineRemoveEnrolMultipleUsers() throws Exception {
		driver.findElement(By.id("login")).clear();
		driver.findElement(By.id("login")).sendKeys("admin");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='loginBox']/form/fieldset/button")).click();
		driver.findElement(By.linkText("001 - Course001")).click();
		driver.findElement(By.id("CLUSR")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id='courseRightContent']/div[1]/span")).getText().contains("5"));
		driver.findElement(By.xpath("//*[@id='courseRightContent']/table/tbody/tr[3]/td[10]/a/img")).click();
		driver.switchTo().alert().accept();
		driver.findElement(By.xpath("//*[@id='courseRightContent']/table/tbody/tr[3]/td[10]/a/img")).click();
		driver.switchTo().alert().accept();
		driver.findElement(By.xpath("//*[@id='courseRightContent']/table/tbody/tr[3]/td[10]/a/img")).click();
		driver.switchTo().alert().accept();
		assertTrue(driver.findElement(By.xpath("//*[@id='courseRightContent']/div[1]/span")).getText().contains("2"));
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}
}
