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

public class AddUserWrongTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTAddUserWrong() throws Exception {
		for(int i=0; i<40; i++) {
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Manage")).click();
			driver.findElement(By.linkText("Manage Users")).click();
			driver.findElement(By.xpath("//input[@value='Create New Account']")).click();
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("username001");
			driver.findElement(By.name("realname")).clear();
			driver.findElement(By.name("realname")).sendKeys("username001");
			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys("username001@username,it");
			new Select(driver.findElement(By.name("access_level"))).selectByVisibleText("updater");
			driver.findElement(By.cssSelector("input.button")).click();
			assertEquals("That username is already being used. Please go back and select another one.",
					driver.findElement(By.xpath("html/body/div[2]/table/tbody/tr[2]/td/p")).getText());
			driver.findElement(By.linkText("Logout")).click();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
