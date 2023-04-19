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

public class AddMenuTest {

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
	public void testExpressCartAddMenu(String port, String host) throws Exception {
		driver.findElement(By.id("email")).sendKeys("owner@test.com");
		driver.findElement(By.id("password")).sendKeys("test");
		driver.findElement(By.id("loginForm")).click();
		driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[2]/li[2]/a")).click();
		driver.findElement(By.id("newNavMenu")).sendKeys("Test Menu");
		driver.findElement(By.id("newNavLink")).sendKeys("/category/tag000");
		driver.findElement(By.id("settings-menu-new")).click();
		Thread.sleep(6000);
		assertTrue(driver.findElement(By.xpath("/html/body/div[2]/div/main/div/div[2]/div[3]/div[4]/button")).isDisplayed());
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
