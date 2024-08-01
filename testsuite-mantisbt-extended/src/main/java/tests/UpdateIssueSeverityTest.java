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

public class UpdateIssueSeverityTest {

	private WebDriver driver;
	private String[][] data = new String[][] {
		{"4"},
		{"5"},
		{"6"},
		{"7"},
		{"8"},
		{"9"},
		{"10"},
		{"11"},
		{"12"},
		{"13"},
		{"14"},
		{"15"},
		{"16"},
		{"17"},
		{"18"},
		{"19"},
		{"20"},
		{"21"},
		{"22"},
		{"23"},
		{"24"},
		{"25"},
		{"26"},
		{"27"},
		{"28"},
		{"29"},
		{"30"},
		{"31"},
		{"32"},
		{"33"},
		{"34"},
		{"35"},
		{"36"},
		{"37"},
		{"38"},
		{"39"},
		{"40"},
		{"41"},
		{"42"},
		{"43"},
	};

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

	@Test
	@Parameters({"port", "host"})
	public void testMantisBTUpdateIssueSeverity(String port, String host) throws Exception {
		for(int i=0; i<data.length; i++) {
	 		driver.get("http://"+host+":"+port+"/mantisbt");
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("View Issues")).click();
			//driver.findElement(By.cssSelector("img[alt=\"Edit\"]")).click();
			driver.findElement(By.xpath("/html/body/form/table/tbody/tr["+data[i][0]+"]/td[2]/a/img")).click();
			new Select(driver.findElement(By.name("severity"))).selectByVisibleText("major");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("View Issues")).click();
			assertEquals("major", driver.findElement(By.xpath(".//*[@id='buglist']/tbody/tr["+data[i][0]+"]/td[7]")).getText());
			driver.findElement(By.linkText("Logout")).click();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
