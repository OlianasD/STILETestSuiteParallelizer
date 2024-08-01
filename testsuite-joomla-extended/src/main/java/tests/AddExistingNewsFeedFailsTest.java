package tests;

import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.BannerPage;
import po.ContactsPage;
import po.CreateNewsFeedPage;
import po.MenuPage;
import po.NewsFeedsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import utils.DriverProvider;


public class AddExistingNewsFeedFailsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddExistingNewsFeedFails() throws Exception {
		for(int i = 0; i<19; i++) {
			String name = "Test Feed 00"+i;
			CreateNewsFeedPage feed = loginAsAdmin(driver)
					.siteAdmin()
					.setUsername("administrator")
					.setPassword("dodicicaratteri")
					.login()
					.newsFeeds()
					.createNewsFeed()
					.setTitle(name)
					.setLink("https://www.google.com")
					.saveError();
					
			
			assertEquals("Save failed with the following error: Another News feed from this category has the same alias.", feed.getAlertMessage());
			NewsFeedsPage feeds = feed.cancel();
			feeds.logout();
		}

	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
