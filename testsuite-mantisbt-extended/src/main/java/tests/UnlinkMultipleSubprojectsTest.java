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

public class UnlinkMultipleSubprojectsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTUnlinkMultipleSubprojects() throws Exception {
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("administrator");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("root");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Manage")).click();
		driver.findElement(By.linkText("Manage Projects")).click();
		driver.findElement(By.linkText("Project1")).click();
		driver.findElement(By.xpath("html/body/div[5]/table/tbody/tr[3]/td[7]/span[2]/a")).click();
		driver.findElement(By.linkText("Proceed")).click();
		driver.findElement(By.xpath("html/body/div[5]/table/tbody/tr[3]/td[7]/span[2]/a")).click();
		driver.findElement(By.linkText("Proceed")).click();
		driver.navigate().refresh();
		assertFalse(isElementPresent(By.xpath("html/body/div[6]/table/tbody/tr[3]/td[1]/a")));
		assertFalse(isElementPresent(By.xpath("html/body/div[6]/table/tbody/tr[4]/td[1]/a")));
		driver.findElement(By.linkText("Manage Projects")).click();
		assertEquals("sub1", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[4]/td[1]/a")).getText());
		assertEquals("sub2", driver.findElement(By.xpath("html/body/table[3]/tbody/tr[5]/td[1]/a")).getText());
		driver.findElement(By.linkText("Logout")).click();
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
