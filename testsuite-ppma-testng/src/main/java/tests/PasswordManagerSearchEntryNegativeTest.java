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

public class PasswordManagerSearchEntryNegativeTest {

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
	public void testPasswordManagerSearchEntryNegative() throws Exception {
		driver.findElement(By.id("LoginForm_username")).clear();
		driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
		driver.findElement(By.id("LoginForm_password")).clear();
		driver.findElement(By.id("LoginForm_password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='login-form']/div/div[2]/a")).click();
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingPresentOnPage(By.id("Entry_name"));
		basePageObject.sendKeys(By.id("Entry_name"), "NotAnEntry");
//		driver.findElement(By.id("Entry_name")).clear();
//		driver.findElement(By.id("Entry_name")).sendKeys("NotAnEntry");
		basePageObject.click(By.name("yt0"));
//		driver.findElement(By.name("yt0")).click();
		assertTrue(driver.findElement(By.className("empty")).getText().contains("No results found."));
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingPresentOnPage(By.id("Entry_name"));
		basePageObject.sendKeys(By.id("Entry_name"), "");
//		driver.findElement(By.id("Entry_name")).clear();
//		driver.findElement(By.id("Entry_name")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_username"), "");
//		driver.findElement(By.id("Entry_username")).clear();
//		driver.findElement(By.id("Entry_username")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_url"), "");
//		driver.findElement(By.id("Entry_url")).clear();
//		driver.findElement(By.id("Entry_url")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_tagList"), "");
//		driver.findElement(By.id("Entry_tagList")).clear();
//		driver.findElement(By.id("Entry_tagList")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_comment"), "");
//		driver.findElement(By.id("Entry_comment")).clear();
//		driver.findElement(By.id("Entry_comment")).sendKeys("");
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingInvisibleOnPage(By.id("Entry_username"));
//		Thread.sleep(1000);
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingPresentOnPage(By.id("Entry_username"));
		basePageObject.sendKeys(By.id("Entry_username"), "NotAnEntry@email.it");
//		driver.findElement(By.id("Entry_username")).clear();
//		driver.findElement(By.id("Entry_username")).sendKeys("NotAnEntry@email.it");
		basePageObject.click(By.name("yt0"));
//		driver.findElement(By.name("yt0")).click();
		assertTrue(driver.findElement(By.className("empty")).getText().contains("No results found."));
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingPresentOnPage(By.id("Entry_username"));
		basePageObject.sendKeys(By.id("Entry_name"), "");
//		driver.findElement(By.id("Entry_name")).clear();
//		driver.findElement(By.id("Entry_name")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_username"), "");
//		driver.findElement(By.id("Entry_username")).clear();
//		driver.findElement(By.id("Entry_username")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_url"), "");
//		driver.findElement(By.id("Entry_url")).clear();
//		driver.findElement(By.id("Entry_url")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_tagList"), "");
//		driver.findElement(By.id("Entry_tagList")).clear();
//		driver.findElement(By.id("Entry_tagList")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_comment"), "");
//		driver.findElement(By.id("Entry_comment")).clear();
//		driver.findElement(By.id("Entry_comment")).sendKeys("");
		driver.findElement(By.linkText("Advanced Search")).click();
//		Thread.sleep(1000);
		basePageObject.waitForElementBeingInvisibleOnPage(By.id("Entry_tagList"));
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingPresentOnPage(By.id("Entry_tagList"));
		basePageObject.sendKeys(By.id("Entry_tagList"), "NotAnEntry");
//		driver.findElement(By.id("Entry_tagList")).clear();
//		driver.findElement(By.id("Entry_tagList")).sendKeys("NotAnEntry");
		basePageObject.click(By.name("yt0"));
//		driver.findElement(By.name("yt0")).click();
		assertTrue(driver.findElement(By.className("empty")).getText().contains("No results found."));
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingPresentOnPage(By.id("Entry_name"));
		basePageObject.sendKeys(By.id("Entry_name"), "");
//		driver.findElement(By.id("Entry_name")).clear();
//		driver.findElement(By.id("Entry_name")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_username"), "");
//		driver.findElement(By.id("Entry_username")).clear();
//		driver.findElement(By.id("Entry_username")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_url"), "");
//		driver.findElement(By.id("Entry_url")).clear();
//		driver.findElement(By.id("Entry_url")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_tagList"), "");
//		driver.findElement(By.id("Entry_tagList")).clear();
//		driver.findElement(By.id("Entry_tagList")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_comment"), "");
//		driver.findElement(By.id("Entry_comment")).clear();
//		driver.findElement(By.id("Entry_comment")).sendKeys("");
		driver.findElement(By.linkText("Advanced Search")).click();
//		Thread.sleep(1000);
		basePageObject.waitForElementBeingInvisibleOnPage(By.id("Entry_url"));
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingPresentOnPage(By.id("Entry_url"));
		basePageObject.sendKeys(By.id("Entry_url"), "www.NotAnEntry.it");
//		driver.findElement(By.id("Entry_url")).clear();
//		driver.findElement(By.id("Entry_url")).sendKeys("www.NotAnEntry.it");
		basePageObject.click(By.name("yt0"));
//		driver.findElement(By.name("yt0")).click();
		assertTrue(driver.findElement(By.className("empty")).getText().contains("No results found."));
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingPresentOnPage(By.id("Entry_name"));
		basePageObject.sendKeys(By.id("Entry_name"), "");
//		driver.findElement(By.id("Entry_name")).clear();
//		driver.findElement(By.id("Entry_name")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_username"), "");
//		driver.findElement(By.id("Entry_username")).clear();
//		driver.findElement(By.id("Entry_username")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_url"), "");
//		driver.findElement(By.id("Entry_url")).clear();
//		driver.findElement(By.id("Entry_url")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_tagList"), "");
//		driver.findElement(By.id("Entry_tagList")).clear();
//		driver.findElement(By.id("Entry_tagList")).sendKeys("");
		basePageObject.sendKeys(By.id("Entry_comment"), "");
//		driver.findElement(By.id("Entry_comment")).clear();
//		driver.findElement(By.id("Entry_comment")).sendKeys("");
		driver.findElement(By.linkText("Advanced Search")).click();
//		Thread.sleep(1000);
		basePageObject.waitForElementBeingInvisibleOnPage(By.id("Entry_comment"));
		driver.findElement(By.linkText("Advanced Search")).click();
		basePageObject.waitForElementBeingPresentOnPage(By.id("Entry_comment"));
		basePageObject.sendKeys(By.id("Entry_comment"), "NotAnEntry");
//		driver.findElement(By.id("Entry_comment")).clear();
//		driver.findElement(By.id("Entry_comment")).sendKeys("NotAnEntry");
		basePageObject.click(By.name("yt0"));
//		driver.findElement(By.name("yt0")).click();
		assertTrue(driver.findElement(By.className("empty")).getText().contains("No results found."));
		driver.findElement(By.linkText("Profile")).click();
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	public WebDriver getDriver() {
		return driver;
	}


}
