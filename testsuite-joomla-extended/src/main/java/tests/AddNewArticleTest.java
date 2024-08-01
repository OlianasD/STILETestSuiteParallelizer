package tests;



import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.LoggedHome;
import po.ManageUsersPage;
import po.ProfilePageInfo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class AddNewArticleTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddNewArticle() throws Exception {
		for(int i=0; i<20; i++) {
			String title = "Test Article 0"+i;
			String body = "This is the body of the first article for testing the platform";
			
			LoggedHome home = loginAsAdmin(driver)
					.createPost()
					.setTitle(title)
					.setBody(body)
					.save();
			
			assertEquals(title, home.getFirstArticleTitle());
			assertEquals(body, home.getFirstArticleBody());
			home.adminLogout();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
