package tests;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
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

public class AddLongNameRoomNegativeTest {

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
	public void testAddLongNameRoomNegative() throws Exception {
		driver.findElement(By.xpath(".//*[@id='logon_box']/form/div/input[3]")).click();
		driver.findElement(By.name("NewUserName")).clear();
		driver.findElement(By.name("NewUserName")).sendKeys("administrator");
		driver.findElement(By.name("NewUserPassword")).clear();
		driver.findElement(By.name("NewUserPassword")).sendKeys("secret");
		driver.findElement(By.cssSelector("input.submit")).click();
		driver.findElement(By.linkText("Rooms")).click();
		new Select(driver.findElement(By.xpath(".//*[@id='area_select']"))).selectByIndex(2);
		driver.findElement(By.xpath("(//input[@name='name'])[2]")).clear();
		driver.findElement(By.xpath("(//input[@name='name'])[2]")).sendKeys("LongMoreThanTwentyFiveCharactersRoomName");
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("");
		driver.findElement(By.name("capacity")).clear();
		driver.findElement(By.name("capacity")).sendKeys("");
		driver.findElement(By.xpath("//input[@value='Add Room']")).click();
		assertFalse(driver
				.findElement(By.xpath(".//*[@id='rooms_table_wrapper']/div[6]/div[2]/div[2]/table/tbody/tr[1]/td/div"))
				.getText().matches("^[\\s\\S]*LongMoreThanTwentyFiveCharactersRoomName[\\s\\S]*$"));
		assertTrue(driver
				.findElement(By.xpath(".//*[@id='rooms_table_wrapper']/div[6]/div[2]/div[2]/table/tbody/tr[1]/td/div"))
				.getText().matches("^[\\s\\S]*LongMoreThanTwentyFiveCha[\\s\\S]*$"));
		driver.findElement(
				By.xpath(".//*[@id='rooms_table_wrapper']/div[6]/div[1]/div[2]/table/tbody/tr[1]/td/div/a/img"))
				.click();
		driver.findElement(By.id("del_yes")).click();
		driver.findElement(By.cssSelector("#logon_box > form > div > input[type=\"submit\"]")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}
}
