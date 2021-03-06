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

public class LinkMultipleSubprojectsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters("port")
	public void setUp(String port) {
        driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://localhost:"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTLinkMultipleSubprojects() throws Exception {
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("administrator");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("root");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Manage")).click();
		driver.findElement(By.linkText("Manage Projects")).click();
		driver.findElement(By.linkText("Project1")).click();
		new Select(driver.findElement(By.name("subproject_id"))).selectByVisibleText("sub1");
		driver.findElement(By.xpath("html/body/div[5]/table/tbody/tr[3]/td/form/input[3]")).click();
		driver.findElement(By.linkText("Proceed")).click();
		new Select(driver.findElement(By.name("subproject_id"))).selectByVisibleText("sub2");
		driver.findElement(By.xpath("html/body/div[5]/table/tbody/tr[5]/td/form/input[3]")).click();
		driver.findElement(By.linkText("Proceed")).click();
		assertEquals("sub1", driver.findElement(By.xpath("html/body/div[5]/table/tbody/tr[3]/td[1]/a")).getText());
		assertEquals("sub2", driver.findElement(By.xpath("html/body/div[5]/table/tbody/tr[4]/td[1]/a")).getText());
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
