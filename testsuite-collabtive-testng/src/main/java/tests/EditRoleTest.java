package tests;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class EditRoleTest {

	private WebDriver driver;

	@BeforeMethod
    @Parameters("port")
	public void setUp(String port) throws Exception {
		driver = DriverProvider.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		driver.get("http://localhost:" + port + "/collabtive/");
	}

	@Test
	public void testCollabtiveEditRole() throws Exception {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("pass")).clear();
		driver.findElement(By.id("pass")).sendKeys("admin");
		driver.findElement(By.cssSelector("button.loginbutn")).click();
		driver.findElement(By.xpath(".//*[@id='mainmenue']/li[3]/a")).click();
		driver.findElement(By.xpath(".//*[@id='contentwrapper']/div[1]/ul/li[2]/a")).click();
		driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[1]/td[2]/div/a"))
				.click();
		driver.findElement(By.id("rolename")).clear();
		driver.findElement(By.id("rolename")).sendKeys("NewRole");
		driver.findElement(By.xpath(
				"html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[2]/td[2]/div[2]/form/fieldset/div[3]/div[3]/input"))
				.click();
		driver.findElement(By.xpath(
				"html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[2]/td[2]/div[2]/form/fieldset/div[3]/div[5]/input"))
				.click();
		driver.findElement(By.xpath(
				"html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[2]/td[2]/div[2]/form/fieldset/div[5]/button[1]"))
				.click();
		assertEquals("NewRole",
				driver.findElement(
						By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[1]/td[2]/div/a"))
						.getText());
		driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[1]/td[2]/div/a"))
				.click();
		assertFalse(driver.findElement(By.xpath(
				"html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[2]/td[2]/div[2]/form/fieldset/div[3]/div[5]/input"))
				.isSelected());
		assertFalse(driver.findElement(By.xpath(
				"html/body/div[1]/div[2]/div[2]/div/div/div[6]/table/tbody[1]/tr[2]/td[2]/div[2]/form/fieldset/div[3]/div[5]/input"))
				.isSelected());
		driver.findElement(By.xpath(".//*[@id='mainmenue']/li[4]/a")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
