package tests;


import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import po.Home;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.DriverProvider;
import utils.Properties;

public class SeeReviewTest {

	private WebDriver driver;
	private WebDriverWait wait;

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
	public void testExpressCartSeeReview(String port, String host) throws Exception {
		//int i =0;
		int page = 1;
		for(int i=0; i<29; i++) {
			if(i>0 && i%6==0) {
				page++;
			}
			driver.findElement(By.linkText("Home")).click();
			new Home(driver).goToPage(page);
			driver.findElement(By.linkText("NewProduct00"+(39-i))).click();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div[1]/div[2]/div/div[4]/a")).click();
			Thread.sleep(500);
			assertEquals("Rating: 5", driver.findElement(By.xpath("//*[@id=\"collapseReviews\"]/li/p[2]")).getText());
			assertEquals("Title: Review00"+i, driver.findElement(By.xpath("//*[@id=\"collapseReviews\"]/li/p[3]")).getText());
			assertEquals("Description: Description00"+i, driver.findElement(By.xpath("//*[@id=\"collapseReviews\"]/li/p[4]")).getText());
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
