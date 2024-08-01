package tests;


import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.AccessLevelsPage;
import po.CreateAccessLevelPage;
import po.ManageUsersPage;
import po.ProfilePageInfo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class AddEmptyAccessLevelFailsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddEmptyAccessLevel() throws Exception {
			
			CreateAccessLevelPage level = loginAsAdmin(driver)
				.siteAdmin()
				.setUsername("administrator")
				.setPassword("dodicicaratteri")
				.login()
				.accessLevels()
				.createAccessLevel()
				.saveError();
			
			assertEquals("The form cannot be submitted as it's missing required data.\nPlease correct the marked fields and try again.", level.getAlertMessage());
			AccessLevelsPage levels = level.cancel();
			levels.logout();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
