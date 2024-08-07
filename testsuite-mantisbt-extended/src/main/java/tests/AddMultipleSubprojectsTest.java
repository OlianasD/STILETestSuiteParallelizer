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

public class AddMultipleSubprojectsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTAddMultipleSubprojects() throws Exception {
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("administrator");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("root");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Manage")).click();
		driver.findElement(By.linkText("Manage Projects")).click();
		driver.findElement(By.cssSelector("td.form-title > form > input.button-small")).click();
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys("Project1");
		new Select(driver.findElement(By.name("status"))).selectByVisibleText("release");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Proceed")).click();
		driver.findElement(By.linkText("Project1")).click();
		driver.findElement(By.cssSelector("td.form-title > form > input.button-small")).click();
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys("sub1");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Proceed")).click();
		driver.findElement(By.linkText("Project1")).click();
		driver.findElement(By.cssSelector("td.form-title > form > input.button-small")).click();
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys("sub2");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Proceed")).click();
		driver.findElement(By.linkText("Project1")).click();
		assertEquals("sub1", driver.findElement(By.xpath("html/body/div[5]/table/tbody/tr[3]/td[1]/a")).getText());
		assertEquals("sub2", driver.findElement(By.xpath("html/body/div[5]/table/tbody/tr[4]/td[1]/a")).getText());
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
