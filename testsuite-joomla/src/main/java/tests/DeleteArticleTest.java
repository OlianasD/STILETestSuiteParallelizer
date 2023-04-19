package tests;





import static org.junit.Assert.assertFalse;
import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.LoggedHome;
import po.ManageArticlesPage;
import po.ManageUsersPage;
import po.ProfilePageInfo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class DeleteArticleTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaDeleteArticle() throws Exception {
		for(int i = 1; i<=20; i++) {
			String title = "Test Article 0"+(20-i);
			//String expectedAlert = "Article trashed.";
			
			ManageArticlesPage articles = loginAsAdmin(driver)
					.siteAdmin()
					.setUsername("administrator")
					.setPassword("dodicicaratteri")
					.login()
					.articles()
					.showArchived()
					.deleteFirstArticle();
			
			//assertEquals(expectedAlert, articles.getAlertMessage());
			assertFalse(articles.containsArticle(title));
			articles.logout();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
