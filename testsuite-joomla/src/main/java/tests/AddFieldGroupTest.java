package tests;


import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.ManageFieldGroupsPage;
import po.ManageFieldsPage;
import po.ManageGroupsPage;
import po.ManageUsersPage;
import po.ProfilePageInfo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class AddFieldGroupTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddFieldGroup() throws Exception {
		for(int i=0; i<20; i++) {
			String group = "Test Group 00"+i;
			ManageFieldGroupsPage groups = loginAsAdmin(driver)
				.siteAdmin()
				.setUsername("administrator")
				.setPassword("dodicicaratteri")
				.login()
				.sideContent()
				.fieldGroups()
				.createGroup()
				.setTitle(group)
				.saveAndClose();
			
			assertTrue(groups.isGroupPresent(group));
			groups.logout();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
