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
import org.openqa.selenium.support.ui.Select;

import utils.DriverProvider;
import utils.Properties;

public class AddDiscountCodePercentTest {

	private WebDriver driver;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
    	driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
    	//driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
    }

	@Test
	@Parameters({"port", "host"})
	public void testExpressCartAddDiscountCodePercent(String port, String host) throws Exception {
		//int i = 0;
		for(int i=0; i<29; i++) {
			String code;
			if(i<10)
				code = "discperc00"+i;
			else
				code = "discperc00A"+i;
			driver.get("http://"+host+":"+port+"/admin");
			driver.findElement(By.id("email")).sendKeys("owner@test.com");
			driver.findElement(By.id("password")).sendKeys("test");
			driver.findElement(By.id("loginForm")).click();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[2]/li[4]/a")).click();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/main/div/h2/div/a")).click();
			driver.findElement(By.id("discountCode")).sendKeys(code);
			new Select(driver.findElement(By.id("discountType"))).selectByVisibleText("Percent");
			driver.findElement(By.id("discountValue")).sendKeys("50");
			driver.findElement(By.id("discountStart")).sendKeys("12/02/2023 00:00");
			driver.findElement(By.xpath("/html/body/div[10]/div/div[3]/button[1]")).click();
			driver.findElement(By.id("discountEnd")).sendKeys("12/02/2024 00:00");
			driver.findElement(By.xpath("/html/body/div[11]/div/div[3]/button[1]")).click();
			driver.findElement(By.xpath("//*[@id=\"discountNewForm\"]/div[1]/div/div/button")).click();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[2]/li[4]/a")).click();
			assertTrue(driver.findElement(By.xpath("//*[@id=\"container\"]/div/main/div/ul")).getText().contains("Code:  "+code));
			/*assertEquals("Code:  discperc00"+i, driver.findElement(By.xpath("//*[@id=\"container\"]/div/main/div/ul/li["+(i+34)+"]/div/div[1]/span")).getText());
			assertEquals("Type:  percent", driver.findElement(By.xpath("//*[@id=\"container\"]/div/main/div/ul/li["+(i+34)+"]/div/div[2]/span")).getText());
			assertEquals("Status: Running", driver.findElement(By.xpath("//*[@id=\"container\"]/div/main/div/ul/li["+(i+34)+"]/div/div[3]/span")).getText());*/
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[3]/li/a")).click();			
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
