package tests;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class DeleteUserTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters("port")
	public void setUp(String port) {
        driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://localhost:"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTDeleteUser() throws Exception {
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("administrator");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("root");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Manage")).click();
		driver.findElement(By.linkText("Manage Users")).click();
		driver.findElement(By.linkText("username001")).click();
		driver.findElement(By.xpath("//input[@value='Delete User']")).click();
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Proceed")).click();
		assertEquals("Manage Accounts [1]",
				driver.findElement(By.xpath("html/body/table[3]/tbody/tr[1]/td[1]")).getText());
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
