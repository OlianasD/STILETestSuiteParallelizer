package tests;

import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.BannerPage;
import po.ContactsPage;
import po.MenuPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import utils.DriverProvider;


public class LinkUserToContactTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaLinkUserToContact() throws Exception {
		for(int i = 0; i < 19; i++) {
			String contact = "Test Contact 00"+i;
			String user;
			if(i<10)
				user="Test User0"+i;
			else
				user="Test UserA"+i;
			ContactsPage contacts = loginAsAdmin(driver)
					.siteAdmin()
					.setUsername("administrator")
					.setPassword("dodicicaratteri")
					.login()
					.contacts()
					.goToContact(contact)
					.linkUser(user)
					.save();
	
					
					
			
			assertEquals("Contact saved.", contacts.getAlertMessage());
			contacts.logout();	
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
