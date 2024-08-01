package tests;



import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.ArticlePage;
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

public class CheckNewArticleTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaCheckNewArticle() throws Exception {
		ArticlePage art = loginAsAdmin(driver)
				.home()
				.goToFirstArticle();
		
		for(int i=19; i>=0; i--) {

			String title = "Test Article 0"+i;
			String author = "Super User";
			String body = "This is the body of the first article for testing the platform";
			
			
			assertEquals(title, art.getTitle());
			assertEquals(author, art.getAuthor());
			assertEquals(body, art.getBody());
			
			art = art.goToNext();
			
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
