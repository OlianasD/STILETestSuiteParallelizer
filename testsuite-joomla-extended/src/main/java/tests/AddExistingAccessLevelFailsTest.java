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

public class AddExistingAccessLevelFailsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddExistingAccessLevelFails() throws Exception {
		for(int i = 0; i < 10; i++) {
			String name = "Test Access Level 00"+i;
			
			CreateAccessLevelPage level = loginAsAdmin(driver)
				.siteAdmin()
				.setUsername("administrator")
				.setPassword("dodicicaratteri")
				.login()
				.accessLevels()
				.createAccessLevel()
				.setTitle(name)
				.saveError();
			
			assertEquals("Save failed with the following error: Level with the name \""+name+"\" already exists.", level.getAlertMessage());
			AccessLevelsPage levels = level.cancel();
			levels.logout();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
