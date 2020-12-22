package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.AssertJUnit;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class AddLongNameBuildingNegativeTest {

	private WebDriver driver;

	@BeforeMethod
    @Parameters("port")
	public void setUp(String port) throws Exception {
		driver = DriverProvider.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:"
                + port + "/mrbs/web");
	}

	@Test
	public void testAddLongNameBuildingNegative() throws Exception {
		driver.findElement(By.xpath(".//*[@id='logon_box']/form/div/input[3]")).click();
		driver.findElement(By.name("NewUserName")).clear();
		driver.findElement(By.name("NewUserName")).sendKeys("administrator");
		driver.findElement(By.name("NewUserPassword")).clear();
		driver.findElement(By.name("NewUserPassword")).sendKeys("secret");
		driver.findElement(By.cssSelector("input.submit")).click();
		driver.findElement(By.linkText("Rooms")).click();
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys("LongMoreThanThirtyCharactersBuildingName");
		driver.findElement(By.xpath("//input[@value='Add Area']")).click();
		AssertJUnit.assertFalse(driver.findElement(By.xpath(".//*[@id='area_select']")).getText()
				.matches("^[\\s\\S]*LongMoreThanThirtyCharactersBuildingName[\\s\\S]*$"));
		AssertJUnit.assertTrue(driver.findElement(By.xpath(".//*[@id='area_select']")).getText()
				.matches("^[\\s\\S]*LongMoreThanThirtyCharactersBu[\\s\\S]*$"));
		driver.findElement(By.xpath(".//*[@id='areaChangeForm']/fieldset/input[6]")).click();
		driver.findElement(By.cssSelector("#logon_box > form > div > input[type=\"submit\"]")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}
}
