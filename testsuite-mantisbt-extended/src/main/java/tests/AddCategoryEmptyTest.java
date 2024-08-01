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

public class AddCategoryEmptyTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTAddCategoryEmpty() throws Exception {
		for(int i=0; i<40; i++) {
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Manage")).click();
			driver.findElement(By.linkText("Manage Projects")).click();
			driver.findElement(By.linkText("Project001")).click();
			driver.findElement(By.cssSelector("td.left > form > input.button")).click();
			assertEquals("A necessary field \"Category\" was empty. Please recheck your inputs.",
					driver.findElement(By.xpath("html/body/div[2]/table/tbody/tr[2]/td/p")).getText());
			driver.findElement(By.linkText("Logout")).click();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
