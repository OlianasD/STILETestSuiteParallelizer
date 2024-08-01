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

public class ChangePasswordTooShortTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaChangePasswordTooShort() throws Exception {
		String username = "tuser01";
		String password = "newpassword01";
		
		EditProfilePage profile = new BaseNavBar(driver)
				.authorLogin()
				.setUsername(username)
				.setPassword(password)
				.login()
				.editProfile()
				.setPassword("a")
				.confirmPassword("a");
		
		assertEquals("This value is not valid", profile.getPasswordAlertMessage());
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
