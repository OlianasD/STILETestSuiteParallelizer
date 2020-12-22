package tests;

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

public class DeleteMultipleSubprojectsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters("port")
	public void setUp(String port) {
        driver = DriverProvider.getInstance().getDriver();
        //implicit wait time was 30, but made this test last 60 sec because of the assertFalse(IsElementPresent) assertions
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("http://localhost:"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTDeleteMultipleSubprojects() throws Exception {
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("administrator");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("root");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Manage")).click();
		driver.findElement(By.linkText("Manage Projects")).click();
		driver.findElement(By.linkText("» sub1")).click();
		driver.findElement(By.cssSelector("form > input.button")).click();
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("» sub2")).click();
		driver.findElement(By.cssSelector("form > input.button")).click();
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Project1")).click();
		driver.findElement(By.cssSelector("form > input.button")).click();
		driver.findElement(By.cssSelector("input.button")).click();
		assertFalse(isElementPresent(By.xpath("html/body/table[3]/tbody/tr[4]/td[1]/a")));
		assertFalse(isElementPresent(By.xpath("html/body/table[3]/tbody/tr[5]/td[1]/a")));
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
