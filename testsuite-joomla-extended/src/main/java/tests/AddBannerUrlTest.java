package tests;

import static org.testng.AssertJUnit.assertEquals;
import static utils.Login.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.BannerPage;
import po.MenuPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import utils.DriverProvider;


public class AddBannerUrlTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testJoomlaAddBannerUrl() throws Exception {
			
		BannerPage banners = loginAsAdmin(driver)
				.siteAdmin()
				.setUsername("administrator")
				.setPassword("dodicicaratteri")
				.login()
				.banners()
				.goToBanner("Test Banner 001")
				.setClickUrl("https://www.google.com")
				.save();
		
		assertEquals("Banner saved.", banners.getAlertMessage());
			

	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
