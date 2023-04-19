package tests;


import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.CartSidebar;
import po.Home;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class SearchDeletedProductTagFailsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
    	driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
    	//driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testExpressCartSearchDeletedProductTagFails() throws Exception {
		for(int i=0; i<40; i++) {
			driver.findElement(By.id("frm_search")).sendKeys("tag00"+i);
			driver.findElement(By.id("btn_search")).click();
			Thread.sleep(1000);
			assertEquals("No products found", driver.findElement(By.className("text-danger")).getText());
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
