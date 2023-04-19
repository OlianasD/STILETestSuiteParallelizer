package tests;


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

public class AddProductTest {

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
	public void testExpressCartAddProduct(String port, String host) throws Exception {
		for(int i=0; i<40; i++) {
			driver.get("http://"+host+":"+port+"/admin");
			driver.findElement(By.id("email")).sendKeys("owner@test.com");
			driver.findElement(By.id("password")).sendKeys("test");
			driver.findElement(By.id("loginForm")).click();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[3]/a[2]")).click();
			driver.findElement(By.id("productTitle")).sendKeys("NewProduct00"+i);
			driver.findElement(By.id("productPrice")).sendKeys(new Double(15.95).toString());
			driver.findElement(By.xpath("//*[@id=\"editor-wrapper\"]/div/div[3]/div[2]")).sendKeys("Description for product 00"+i);
			driver.findElement(By.id("frm_edit_product_save")).click();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[3]/a[1]")).click();
			assertEquals(driver.findElement(By.xpath("//*[@id=\"container\"]/div/main/div[3]/ul/li[2]/div/a")).getText(), "NewProduct00"+i);
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[3]/li/a")).click();			
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
