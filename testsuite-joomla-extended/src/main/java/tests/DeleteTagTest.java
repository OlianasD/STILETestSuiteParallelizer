package tests;

import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.BannerPage;
import po.ContactsPage;
import po.MenuPage;
import po.NewsFeedsPage;
import po.TagsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import utils.DriverProvider;


public class DeleteTagTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddTag() throws Exception {
		for(int i=0; i<20; i++) {
			TagsPage tags = loginAsAdmin(driver)
					.siteAdmin()
					.setUsername("administrator")
					.setPassword("dodicicaratteri")
					.login()
					.tags()
					.selectTag()
					.deleteSelectedTag();
					
					
			
			assertEquals("Tag trashed.", tags.getAlertMessage());
			tags.logout();
		}
			

	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
