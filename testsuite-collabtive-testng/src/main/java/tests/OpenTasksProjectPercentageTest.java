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

public class OpenTasksProjectPercentageTest {

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
	public void testCollabtiveOpenTasksProjectPercentage() throws Exception {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("pass")).clear();
		driver.findElement(By.id("pass")).sendKeys("admin");
		driver.findElement(By.cssSelector("button.loginbutn")).click();
		driver.findElement(By.xpath(".//*[@id='mainmenue']/li[3]/a")).click();
		driver.findElement(By.linkText("Project001")).click();
		driver.findElement(By.xpath(".//*[@id='contentwrapper']/div[1]/ul/li[3]/a")).click();
		driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[5]/div[3]/div/a[2]")).click();
		driver.findElement(
				By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[5]/div[2]/div/div/table/tbody[1]/tr[1]/td[1]/a"))
				.click();
		driver.findElement(By.xpath(".//*[@id='contentwrapper']/div[1]/ul/li[1]/a")).click();
		assertEquals("50%",
				driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div[1]/div[2]/div")).getText());
		driver.findElement(By.xpath(".//*[@id='contentwrapper']/div[1]/ul/li[3]/a")).click();
		driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[5]/div[3]/div/a[2]")).click();
		driver.findElement(
				By.xpath("html/body/div[1]/div[2]/div[2]/div/div/div[5]/div[2]/div/div/table/tbody[1]/tr[1]/td[1]/a"))
				.click();
		driver.findElement(By.xpath(".//*[@id='contentwrapper']/div[1]/ul/li[1]/a")).click();
		assertEquals("0%",
				driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]/div/div[1]/div[2]/div")).getText());
		driver.findElement(By.xpath(".//*[@id='mainmenue']/li[4]/a")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
