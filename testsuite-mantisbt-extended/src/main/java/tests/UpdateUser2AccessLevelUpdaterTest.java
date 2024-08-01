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

public class UpdateUser2AccessLevelUpdaterTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

	@Test
	@Parameters({"port", "host"})
	public void testMantisBTUpdateUser1AccessLevelReporter(String port, String host) throws Exception {
		//for(int i=0; i<data.length; i++) {
			driver.get("http://"+host+":"+port+"/mantisbt");
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Manage")).click();
			driver.findElement(By.linkText("Manage Users")).click();
			driver.findElement(By.linkText("user002")).click();
			new Select(driver.findElement(By.name("access_level"))).selectByVisibleText("updater");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Proceed")).click();
			assertEquals("updater", new Select(driver.findElement(By.name("access_level"))).getFirstSelectedOption().getText());
			driver.findElement(By.linkText("Logout")).click();
		//}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
