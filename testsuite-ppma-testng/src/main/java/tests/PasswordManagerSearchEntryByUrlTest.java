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

public class PasswordManagerSearchEntryByUrlTest {

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
	public void testPasswordManagerSearchEntryByUrl() throws Exception {
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingClickable(By.id("Entry_url"));
		basePageObject.sendKeys(By.id("Entry_url"), "www.google.it/mail");
//		driver.findElement(By.id("Entry_url")).clear();
//		driver.findElement(By.id("Entry_url")).sendKeys("www.google.it/mail");
		basePageObject.waitForElementBeingVisibleOnPage(By.name("yt0"));
		basePageObject.click(By.name("yt0"));
//		driver.findElement(By.name("yt0")).click();
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[1]")).getText().contains("Google"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[2]")).getText().contains("myaccount@google.it"));
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div/div/div[3]/table/tbody/tr/td[3]")).getText().contains("Email, Google"));
		driver.findElement(By.linkText("Profile")).click();
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
