package tests;


import static org.junit.Assert.assertFalse;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.DriverProvider;
import utils.JavascriptExecutor;
import utils.Properties;
import utils.XpathGenerator;

public class DeleteDiscountCodePercentTest {

	private WebDriver driver;
	private XpathGenerator xpgen;

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
    	driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
    	//driver = DriverProvider.getInstance().getDriver();
    	xpgen = new XpathGenerator();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
    }

	@Test
	@Parameters({"port", "host"})
	public void testExpressCartAddDiscountCodePercent(String port, String host) throws Exception {
		driver.get("http://"+host+":"+port+"/admin");
		driver.findElement(By.id("email")).sendKeys("owner@test.com");
		driver.findElement(By.id("password")).sendKeys("test");
		driver.findElement(By.id("loginForm")).click();
		driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[2]/li[4]/a")).click();
		for(int i=0; i<29; i++) {
			String code;
			if(i<10)
				code = "discperc00"+i;
			else
				code = "discperc00A"+i;
			WebElement codeElem = driver.findElement(By.xpath("//*[contains(text(), '"+code+"')]"));
			String codeXpath = xpgen.generateXpath(codeElem, "");
			codeXpath = codeXpath.replace("div[1]/span[1]", "div[4]/button");
			//System.out.println("Using xpath: "+codeXpath);
			driver.findElement(By.xpath(codeXpath)).click();
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[2]/li[4]/a")).click();
			assertFalse(driver.findElement(By.xpath("//*[@id=\"container\"]/div/main/div/ul")).getText().contains("Code:  "+code));
					
		}	
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
