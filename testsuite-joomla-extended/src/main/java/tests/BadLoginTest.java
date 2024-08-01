package tests;


import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.AuthorLoginPage;
import po.BaseNavBar;
import po.ProfilePageInfo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class BadLoginTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaLoginAsAdmin() throws Exception {
		AuthorLoginPage login = new BaseNavBar(driver)
				.authorLogin()
				.setUsername("administrator")
				.setPassword("wrongpassword")
				.badLogin();
		
		assertEquals("Username and password do not match or you do not have an account yet.", login.getAlertMessage());
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
