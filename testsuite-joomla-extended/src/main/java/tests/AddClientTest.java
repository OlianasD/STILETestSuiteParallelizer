package tests;

import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.BannerPage;
import po.ClientPage;
import po.MenuPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import utils.DriverProvider;


public class AddClientTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddClient() throws Exception {
		for(int i =0; i<19; i++) {
			String name = "Google0"+i;
			String contactName = "Google contact0"+i;
			ClientPage clients = loginAsAdmin(driver)
					.siteAdmin()
					.setUsername("administrator")
					.setPassword("dodicicaratteri")
					.login()
					.clients()
					.createClient()
					.setName(name)
					.setContactName(contactName)
					.save();
			
			assertEquals("Client saved.", clients.getAlertMessage());
			clients.logout();
		}
			

	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
