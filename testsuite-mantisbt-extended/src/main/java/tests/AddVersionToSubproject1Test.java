package tests;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class AddVersionToSubproject1Test {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTaddVersionToSubproject1() throws Exception {
		for(int i=0; i<10; i++) {
			int gap = i == 0 ? 2 : 3;
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Manage")).click();
			driver.findElement(By.linkText("Manage Projects")).click();
			driver.findElement(By.linkText("Project1")).click();
			driver.findElement(By.linkText("sub1")).click();
			driver.findElement(By.xpath("/html/body/div[6]/a[2]/table/tbody/tr["+(gap+i)+"]/td/form/input[3]")).sendKeys(i+".0");
			driver.findElement(By.xpath("/html/body/div[6]/a[2]/table/tbody/tr["+(gap+i)+"]/td/form/input[4]")).click();
			driver.findElement(By.linkText("Proceed")).click();
			assertEquals(i+".0", driver.findElement(By.xpath("/html/body/div[6]/a[2]/table/tbody/tr[3]/td[1]")).getText());
			driver.findElement(By.linkText("Logout")).click();
			Thread.sleep(1000);
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
