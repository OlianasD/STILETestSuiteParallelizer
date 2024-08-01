package tests;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverProvider;
import utils.Properties;

public class UpdateIssueSummaryTest {

	private WebDriver driver;
	private String[][] data = new String[][] {
		{"Category001", "Summary001changed"},
		{"Category002", "Summary002changed"},
		{"Category003", "Summary003changed"},
		{"Category004", "Summary004changed"},
		{"Category005", "Summary005changed"},
		{"Category006", "Summary006changed"},
		{"Category007", "Summary007changed"},
		{"Category008", "Summary008changed"},
		{"Category009", "Summary009changed"},
		{"Category00910", "Summary00910changed"},
		{"Category00911", "Summary00911changed"},
		{"Category00912", "Summary00912changed"},
		{"Category00913", "Summary00913changed"},
		{"Category00914", "Summary00914changed"},
		{"Category00915", "Summary00915changed"},
		{"Category00916", "Summary00916changed"},
		{"Category00917", "Summary00917changed"},
		{"Category00918", "Summary00918changed"},
		{"Category00919", "Summary00919changed"},
		{"Category00920", "Summary00920changed"},
		{"Category00921", "Summary00921changed"},
		{"Category00922", "Summary00922changed"},
		{"Category00923", "Summary00923changed"},
		{"Category00924", "Summary00924changed"},
		{"Category00925", "Summary00925changed"},
		{"Category00926", "Summary00926changed"},
		{"Category00927", "Summary00927changed"},
		{"Category00928", "Summary00928changed"},
		{"Category00929", "Summary00929changed"},
		{"Category00930", "Summary00930changed"},
		{"Category00931", "Summary00931changed"},
		{"Category00932", "Summary00932changed"},
		{"Category00933", "Summary00933changed"},
		{"Category00934", "Summary00934changed"},
		{"Category00935", "Summary00935changed"},
		{"Category00936", "Summary00936changed"},
		{"Category00937", "Summary00937changed"},
		{"Category00938", "Summary00938changed"},
		{"Category00939", "Summary00939changed"},
		{"Category00940", "Summary00940changed"},


	};

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

	@Test
	@Parameters({"port", "host"})
	public void testMantisBTUpdateIssueSummary(String port, String host) throws Exception {
		for(int i=0; i<data.length; i++) {
			driver.get("http://"+host+":"+port+"/mantisbt");
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("View Issues")).click();
			driver.findElement(By.xpath("//td[contains(text(), '"+data[i][0]+"')]/..//td[2]/a/img")).click();
			driver.findElement(By.name("summary")).clear();
			driver.findElement(By.name("summary")).sendKeys(data[i][1]);
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("View Issues")).click();
			assertEquals(data[i][1], driver.findElement(By.xpath(".//*[@id='buglist']/tbody/tr[4]/td[10]")).getText());
			driver.findElement(By.linkText("Logout")).click();
			Thread.sleep(1000);
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
