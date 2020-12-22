package tests;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.BasePageObject;
import utils.DriverProvider;
import utils.Properties;

public class PasswordManagerEditTagTest {

	private WebDriver driver;
	private BasePageObject basePageObject;

	@BeforeMethod
    @Parameters("port")
	public void setUp(String port) throws Exception {
		driver = DriverProvider.getInstance().getDriver();
		driver.get("http://localhost:"
                + port + "/ppma/index.php");
		basePageObject = new BasePageObject(driver);
	}

	@Test
	public void testPasswordManagerEditTag() throws Exception {
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
		driver.findElement(By.linkText("Tags")).click();
		driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[3]/a[2]")).click();
		driver.findElement(By.id("Tag_name")).clear();
		driver.findElement(By.id("Tag_name")).sendKeys("Msn");
		driver.findElement(By.name("yt0")).click();
//		Thread.sleep(1000);
		basePageObject.waitForElementBeingPresentOnPage(By.xpath("html/body/div[1]/div/div/div[4]/table/tbody/tr/td[1]"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[4]/table/tbody/tr/td[1]")).getText().contains("Msn"));
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
