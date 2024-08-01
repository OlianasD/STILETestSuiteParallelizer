package tests;

import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.BannerPage;
import po.ContactsPage;
import po.CreateContactPage;
import po.MenuPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import utils.DriverProvider;


public class AddExistingContactFailsTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddExistingContactFails() throws Exception {
		for(int i = 0; i<19; i++) {	
			String name = "Test Contact 00"+i;
			CreateContactPage contact = loginAsAdmin(driver)
					.siteAdmin()
					.setUsername("administrator")
					.setPassword("dodicicaratteri")
					.login()
					.contacts()
					.createContact()
					.setName(name)
					.saveError();
			
			assertEquals("Save failed with the following error: Another Contact in this category has the same alias.", contact.getAlertMessage());
			ContactsPage contacts = contact.cancel();
			contacts.logout();
		}
			

	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
