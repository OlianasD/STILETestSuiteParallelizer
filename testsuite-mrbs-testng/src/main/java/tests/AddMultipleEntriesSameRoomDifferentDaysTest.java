package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.AssertJUnit;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import utils.DriverProvider;
import utils.Properties;

public class AddMultipleEntriesSameRoomDifferentDaysTest {

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
	public void testAddMultipleEntriesSameRoomDifferentDays() throws Exception {
		driver.findElement(By.xpath(".//*[@id='logon_box']/form/div/input[3]")).click();
		driver.findElement(By.name("NewUserName")).clear();
		driver.findElement(By.name("NewUserName")).sendKeys("administrator");
		driver.findElement(By.name("NewUserPassword")).clear();
		driver.findElement(By.name("NewUserPassword")).sendKeys("secret");
		driver.findElement(By.cssSelector("input.submit")).click();
		driver.findElement(By.linkText("MyBuilding")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'10')])[3]")).click();
		driver.findElement(By.xpath("//*[@id='day_main']/tbody/tr[1]/td[2]/div/a")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("MyEvent1");
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("Description for MyEvent1");
		new Select(driver.findElement(By.id("area"))).selectByVisibleText("MyBuilding");
		driver.findElement(By.name("save_button")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'11')])[2]")).click();
		driver.findElement(By.xpath(".//*[@id='day_main']/tbody/tr[11]/td[2]/div/a")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("MyEvent2");
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("Description for MyEvent2");
		new Select(driver.findElement(By.id("area"))).selectByVisibleText("MyBuilding");
		driver.findElement(By.name("save_button")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'12')])[2]")).click();
		driver.findElement(By.xpath(".//*[@id='day_main']/tbody/tr[15]/td[2]/div/a")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("MyEvent3");
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("Description for MyEvent3");
		new Select(driver.findElement(By.id("area"))).selectByVisibleText("MyBuilding");
		driver.findElement(By.name("save_button")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'10')])[2]")).click();
		AssertJUnit.assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*MyEvent1[\\s\\S]*$"));
		driver.findElement(By.xpath("(//a[contains(text(),'11')])[2]")).click();
		AssertJUnit.assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*MyEvent2[\\s\\S]*$"));
		driver.findElement(By.xpath("(//a[contains(text(),'12')])[2]")).click();
		AssertJUnit.assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*MyEvent3[\\s\\S]*$"));
		driver.findElement(By.cssSelector("#logon_box > form > div > input[type=\"submit\"]")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}
}
