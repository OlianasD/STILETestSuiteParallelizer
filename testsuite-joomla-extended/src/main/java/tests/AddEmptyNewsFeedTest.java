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


public class AddEmptyNewsFeedTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddEmptyNewsFeed() throws Exception {
			
		CreateNewsFeedPage feeds = loginAsAdmin(driver)
				.siteAdmin()
				.setUsername("administrator")
				.setPassword("dodicicaratteri")
				.login()
				.newsFeeds()
				.createNewsFeed()
				.saveError();
				
				
		
		assertEquals("The form cannot be submitted as it's missing required data.\nPlease correct the marked fields and try again.", feeds.getAlertMessage());
			

	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
