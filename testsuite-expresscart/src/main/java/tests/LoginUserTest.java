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

public class LoginUserTest {

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
	public void testExpressCartLoginUser(String port, String host) throws Exception {
		for(int i=0; i<40; i++) {
			driver.get("http://"+host+":"+port+"/admin");
			driver.findElement(By.id("email")).sendKeys("test00"+i+"@test.com");
			driver.findElement(By.id("password")).sendKeys("password");
			driver.findElement(By.id("loginForm")).click();
			String text = driver.findElement(By.xpath("//*[@id=\"container\"]/div/main/div[1]/h2")).getText();
			assertEquals(text, "Dashboard");
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[3]/li/a")).click();			
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
