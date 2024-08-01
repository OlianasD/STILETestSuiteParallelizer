package tests;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import utils.DriverProvider;
import utils.Properties;

public class UpdateIssueStatusFeedbackTest {

	private WebDriver driver;
	private String[][] data = new String[][] {
		{"Category001", "4"},
		{"Category002", "5"},
		{"Category003", "6"},
		{"Category004", "7"},
		{"Category005", "8"},
		{"Category006", "9"},
		{"Category007", "10"},
		{"Category008", "11"},
		{"Category009", "12"},
		{"Category00910", "13"},
		{"Category00911", "14"},
		{"Category00912", "15"},
		{"Category00913", "16"},
		{"Category00914", "17"},
		{"Category00915", "18"},
		{"Category00916", "19"},
		{"Category00917", "20"},
		{"Category00918", "21"},
		{"Category00919", "22"},
		{"Category00920", "23"},
		{"Category00921", "24"},
		{"Category00922", "25"},
		{"Category00923", "26"},
		{"Category00924", "27"},
		{"Category00925", "28"},
		{"Category00926", "29"},
		{"Category00927", "30"},
		{"Category00928", "31"},
		{"Category00929", "32"},
		{"Category00930", "33"},
		{"Category00931", "34"},
		{"Category00932", "35"},
		{"Category00933", "36"},
		{"Category00934", "37"},
		{"Category00935", "38"},
		{"Category00936", "39"},
		{"Category00937", "40"},
		{"Category00938", "41"},
		{"Category00939", "42"},
		{"Category00940", "43"},

	};
	
    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

	@Test
	@Parameters({"port", "host"})
	public void testMantisBTUpdateIssueStatusFeedback(String port, String host) throws Exception {
		for(int i=0; i<data.length; i++) {
			driver.get("http://"+host+":"+port+"/mantisbt");
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("View Issues")).click();
			driver.findElement(By.xpath("//td[contains(text(), '"+data[i][0]+"')]/..//td[2]/a/img")).click();
			new Select(driver.findElement(By.name("status"))).selectByVisibleText("feedback");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("View Issues")).click();
			assertEquals("feedback (administrator)",
					driver.findElement(By.xpath(".//*[@id='buglist']/tbody/tr[4]/td[8]")).getText());
			driver.findElement(By.linkText("Logout")).click();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
