package tests;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

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

public class AddCategoryTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddCategory() throws Exception {
		for(int i = 0; i < 18; i++) {
			String title = "Test Category 00"+i;
				
				ManageCategoriesPage cats = loginAsAdmin(driver)
					.siteAdmin()
					.setUsername("administrator")
					.setPassword("dodicicaratteri")
					.login()
					.categories()
					.addCategory()
					.setTitle(title)
					.save();
				
				assertTrue(cats.containsCategory(title));
				cats.logout();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
