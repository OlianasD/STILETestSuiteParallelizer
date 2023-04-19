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

public class OpenMenuTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
    	driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
    	//driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
       // driver.manage().window().maximize();
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testExpressCartOpenMenu() throws Exception {
		driver.findElement(By.linkText("Test Menu")).click();
		assertEquals("NewProduct000", driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div[2]/div/div/div/a/h3")).getText());
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
