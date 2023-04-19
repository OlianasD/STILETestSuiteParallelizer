package tests;


import static org.junit.Assert.assertTrue;
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

public class DeletedDiscountCodeFailsPercentTest {

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
	@Parameters({"port", "host"})
	public void testExpressCartDeletedDiscountCodeFailsPercent(String port, String host) throws Exception {
		new Home(driver).account().setEmail("test@test.com").setPassword("test").login();
		driver.findElement(By.linkText("Home")).click();
		int page = 1;
		int item_id = 1;
		for(int i=0; i<29; i++) {
			String code;
			if(i<10)
				code = "discperc00"+i;
			else
				code = "discperc00A"+i;
			Home home = new Home(driver);
			//Thread.sleep(1000);
			home
				.goToProduct(item_id)
				.addToCart();
			driver.findElement(By.xpath("//*[@id=\"navbarText\"]/ul/li/a")).click();
			Thread.sleep(500);
			driver.findElement(By.xpath("//*[@id=\"cart\"]/div[2]/div/a")).click();
			driver.findElement(By.id("checkoutInformation")).click();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div/div[1]/a[2]")).click();
			driver.findElement(By.id("discountCode")).sendKeys(code);
			driver.findElement(By.id("addDiscountCode")).click();
			Thread.sleep(1000);
			assertEquals("Discount code is invalid or expired", driver.findElement(By.className("alert-danger")).getText());
			driver.findElement(By.xpath("//*[@id=\"navbarText\"]/ul/li/a")).click();
			Thread.sleep(500);
			driver.findElement(By.id("empty-cart")).click();
			driver.findElement(By.id("buttonConfirm")).click();
			Thread.sleep(500);
			driver.get("http://"+host+":"+port);
			item_id++;
			if(i>0 && item_id  == 7) {
				page++;
				item_id = 1;
			}
			home = new Home(driver).goToPage(page);
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
