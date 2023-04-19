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

public class AddReviewTest {

	private WebDriver driver;
	private WebDriverWait wait;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
    	driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
    	//driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	//wait = new WebDriverWait(driver, 30);
        //driver.manage().window().maximize();
        driver.get("http://"+host+":"+port);
    }

	@Test
	@Parameters({"port", "host"})
	public void testExpressCartAddReview(String port, String host) throws Exception {
		//int i =0;
		int page = 1;
		driver.findElement(By.xpath("//*[@id=\"navbarText\"]/ul/li[1]/a")).click();
		driver.findElement(By.id("email")).sendKeys("test@test.com");
		driver.findElement(By.id("password")).sendKeys("test");
		driver.findElement(By.id("customerloginForm")).click();
		for(int i=0; i<29; i++) {
			if(i>0 && i%6==0) {
				page++;
			}
			driver.get("http://"+host+":"+port);
			//System.out.println("i="+i+"page="+page);
			driver.findElement(By.linkText("Home")).click();
			new Home(driver).goToPage(page);
			driver.findElement(By.linkText("NewProduct00"+(39-i))).click();
			driver.findElement(By.id("add-review")).click();
			Thread.sleep(500);
			driver.findElement(By.id("review-title")).sendKeys("Review00"+i);
			Thread.sleep(500);
			driver.findElement(By.id("review-description")).sendKeys("Description00"+i);
			Thread.sleep(500);
			driver.findElement(By.id("review-rating")).sendKeys("5");
			Thread.sleep(500);
			driver.findElement(By.id("addReview")).click();
			Thread.sleep(1000);
			//driver.findElement(By.xpath("/html/body/div[7]/div/div/div[3]/button[2]")).click();
			try {
				assertEquals("Review successfully submitted", driver.findElement(By.className("alert-success")).getText());
			} catch(Exception e) {
				Thread.sleep(6000);
				driver.navigate().refresh();
				assertTrue(driver.findElement(By.tagName("body")).getText().contains("Recent reviews"));
			}
			/*driver.navigate().refresh();
			assertTrue(driver.findElement(By.tagName("body")).getText().contains("Recent reviews"));*/
			/*
			 * Thread.sleep(1000);
			 *
			driver.findElement(By.xpath("/html/body/nav/div/ul/div[2]/button")).click();
			driver.findElement(By.xpath("//*[@id=\"navbarText\"]/ul/div[2]/div/li[2]/a")).click();*/
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
