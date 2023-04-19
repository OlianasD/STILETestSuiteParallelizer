package tests;


import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.CartSidebar;
import po.Home;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class AddNewProdToCartTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
    	driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
    	//driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
        driver.get("http://"+host+":"+port);
    }

	@Test
	public void testExpressCartAddNewProdToCart() throws Exception {
		Home home = new Home(driver);
		int page = 1;
		int item_id = 1;
		for(int i=0; i<29; i++) {
			//System.err.println(i);
			home = home.goToProduct(item_id).addToCart().goHome();
			//Thread.sleep(1000);
			assertEquals(i+1, home.getCartCount());
			CartSidebar cart = home.openCart();
			Thread.sleep(500);
			assertEquals("NewProduct00"+(39-i), cart.getIthItem(i+1));
			home = cart.close();
			Thread.sleep(500);
			item_id++;
			if(i>0 && item_id  == 7) {
				page++;
				item_id = 1;
			}
			home = home.goToPage(page);
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
