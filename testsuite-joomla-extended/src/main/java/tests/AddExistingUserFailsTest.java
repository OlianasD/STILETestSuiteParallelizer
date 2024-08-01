package tests;


import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.AddUserPage;
import po.ManageUsersPage;
import po.ProfilePageInfo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class AddExistingUserFailsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddExistingUserFails() throws Exception {
		for(int i = 0; i < 19; i++) {
			String name;
			if(i<10)
			 name="Test User0"+i;
			else
				name="Test UserA"+i;
			String username = "tuser0"+i;
			String password = "tpassword";
			String email = "testmail"+i+"@example.com";
			
			AddUserPage user = loginAsAdmin(driver)
				.siteAdmin()
				.setUsername("administrator")
				.setPassword("dodicicaratteri")
				.login()
				.users()
				.addUser()
				.setName(name)
				.setLoginName(username)
				.setPassword(password)
				.confirmPassword(password)
				.setEmail(email)
				.saveError();
			
			assertEquals("Save failed with the following error: Username in use.", user.getAlertMessage());

			ManageUsersPage users = user.cancel();
			users.logout();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
