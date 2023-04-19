package tests;


import static org.junit.Assert.assertTrue;
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

public class DeleteProductTagTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
    	driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
    	//driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
        driver.get("http://"+host+":"+port+"/admin");
    }

	@Test
	@Parameters({"port", "host"})
	public void testExpressCartDeleteProductTag(String port, String host) throws Exception {
		driver.findElement(By.id("email")).sendKeys("owner@test.com");
		driver.findElement(By.id("password")).sendKeys("test");
		driver.findElement(By.id("loginForm")).click();
		for(int i=0; i<40; i++) {
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[3]/a[1]")).click();
			if(i>=10 && i<20 ) driver.findElement(By.linkText("2")).click();
			if(i>=20 && i<30 ) driver.findElement(By.linkText("3")).click();
			if(i>=30 && i<40 ) driver.findElement(By.linkText("4")).click();
			driver.findElement(By.linkText("NewProduct00"+(39-i))).click();
			driver.findElement(By.xpath("//*[@id=\"productEditForm\"]/div/div[9]/div/div/div/a")).click();
			assertTrue(driver.findElement(By.id("productTags-tokenfield")).getAttribute("value").equals(""));
			driver.findElement(By.id("productUpdate")).click();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[3]/a[1]")).click();		
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
