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

public class LoginNegativeTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters("port")
	public void setUp(String port) {
        driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://localhost:"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTLoginNegative() throws Exception {
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("");
		driver.findElement(By.cssSelector("input.button")).click();
		assertEquals("Your account may be disabled or blocked or the username/password you entered is incorrect.",
				driver.findElement(By.xpath("html/body/div[2]/font")).getText());
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
