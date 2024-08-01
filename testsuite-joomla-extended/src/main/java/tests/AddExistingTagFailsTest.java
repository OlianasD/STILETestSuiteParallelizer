package tests;

import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.BannerPage;
import po.ContactsPage;
import po.CreateTagPage;
import po.MenuPage;
import po.NewsFeedsPage;
import po.TagsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import utils.DriverProvider;


public class AddExistingTagFailsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddExistingTagFails() throws Exception {
		for(int i =0; i<20; i++) {
			String name = "Test Tag 00"+i;
			CreateTagPage tag = loginAsAdmin(driver)
					.siteAdmin()
					.setUsername("administrator")
					.setPassword("dodicicaratteri")
					.login()
					.tags()
					.createTag()
					.setTitle(name)
					.saveError();
					
			assertEquals("Save failed with the following error: Another Tag has the same alias.", tag.getAlertMessage());
			TagsPage tags = tag.cancel();
			tags.logout();
		}

	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
