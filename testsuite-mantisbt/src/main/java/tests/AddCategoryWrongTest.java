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

public class AddCategoryWrongTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters("port")
	public void setUp(String port) {
        driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://localhost:"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTAddCategoryWrong() throws Exception {
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("administrator");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("root");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Manage")).click();
		driver.findElement(By.linkText("Manage Projects")).click();
		driver.findElement(By.linkText("Project001")).click();
		driver.findElement(By.xpath("html/body/div[6]/a[1]/table/tbody/tr[5]/td/form/input[3]")).clear();
		driver.findElement(By.xpath("html/body/div[6]/a[1]/table/tbody/tr[5]/td/form/input[3]"))
				.sendKeys("Category001");
		driver.findElement(By.cssSelector("td.left > form > input.button")).click();
		assertEquals("A category with that name already exists.",
				driver.findElement(By.xpath("html/body/div[2]/table/tbody/tr[2]/td/p")).getText());
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}