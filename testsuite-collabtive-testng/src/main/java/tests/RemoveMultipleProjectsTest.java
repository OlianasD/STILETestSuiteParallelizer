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

import utils.DriverProvider;
import utils.Properties;

public class RemoveMultipleProjectsTest {

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
	public void testCollabtiveRemoveMultipleProjects() throws Exception {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("pass")).clear();
		driver.findElement(By.id("pass")).sendKeys("admin");
		driver.findElement(By.cssSelector("button.loginbutn")).click();
		driver.findElement(By.xpath(".//*[@id='mainmenue']/li[3]/a")).click();
		driver.findElement(
				By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[3]/div[2]/table/tbody[2]/tr[1]/td[5]/a[2]"))
				.click();
		
		Thread.sleep(200);
		assertTrue(driver.switchTo().alert().getText()
				.matches("^Really delete this item[\\s\\S]\nDeleting cannot be undone\\.$"));
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		driver.findElement(
				By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[3]/div[2]/table/tbody[3]/tr[1]/td[5]/a[2]"))
				.click();
		Thread.sleep(200);
		assertTrue(driver.switchTo().alert().getText()
				.matches("^Really delete this item[\\s\\S]\nDeleting cannot be undone\\.$"));
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		driver.findElement(
				By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[3]/div[2]/table/tbody[4]/tr[1]/td[5]/a[2]"))
				.click();
		Thread.sleep(200);
		assertTrue(driver.switchTo().alert().getText()
				.matches("^Really delete this item[\\s\\S]\nDeleting cannot be undone\\.$"));
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		driver.navigate().refresh();
		assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*myProject1[\\s\\S]*$"));
		assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*myProject2[\\s\\S]*$"));
		assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*myProject3[\\s\\S]*$"));
		driver.findElement(By.xpath(".//*[@id='mainmenue']/li[4]/a")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
