package tests;





import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.BaseNavBar;
import po.EditProfilePage;
import po.LoggedHome;
import po.ManageArticlesPage;
import po.ManageCategoriesPage;
import po.ManageUsersPage;
import po.ProfilePageInfo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class ChangePasswordTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaChangePassword() throws Exception {
		for(int i = 0; i<19; i++) {
			String username = "tuser0"+i;
			String oldPassword = "tpassword";
			String password = "newpassowrd01";
			
			EditProfilePage profile = new BaseNavBar(driver)
					.authorLogin()
					.setUsername(username)
					.setPassword(oldPassword)
					.login()
					.editProfile()
					.setPassword(password)
					.confirmPassword(password)
					.submit();
			
			assertEquals("Profile saved.", profile.getAlertMessage());
			profile.standardUserLogOut();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
