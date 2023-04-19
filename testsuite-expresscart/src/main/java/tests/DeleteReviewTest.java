package tests;


import static org.junit.Assert.assertFalse;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.Home;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class DeleteReviewTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
    	driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
    	//driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
    }

	@Test
	@Parameters({"port", "host"})
	public void testExpressCartDeleteReview(String port, String host) throws Exception {
		for(int i=0; i<29; i++) {
			driver.get("http://"+host+":"+port+"/admin");
			driver.findElement(By.id("email")).sendKeys("owner@test.com");
			driver.findElement(By.id("password")).sendKeys("test");
			driver.findElement(By.id("loginForm")).click();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[8]/a")).click();
			driver.findElement(By.xpath("/html/body/div[2]/div/main/div[3]/ul/li[2]/div/div[4]/a")).click();
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[8]/a")).click();
			assertFalse(driver.findElement(By.xpath("/html/body/div[2]/div/main/div[3]/ul/li[2]/div/div[1]/div")).getText().contains("Review00"+(28-i)));
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[3]/li/a")).click();			
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
