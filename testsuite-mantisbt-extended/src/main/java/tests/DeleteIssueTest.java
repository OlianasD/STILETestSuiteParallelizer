package tests;

import static org.testng.AssertJUnit.assertTrue;
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

public class DeleteIssueTest {

	private WebDriver driver;
	private String[][] data = new String[][] {
		{"40"},
		{"39"},
		{"38"},
		{"37"},
		{"36"},
		{"35"},
		{"34"},
		{"33"},
		{"32"},
		{"31"},
		{"30"},
		{"29"},
		{"28"},
		{"27"},
		{"26"},
		{"25"},
		{"24"},
		{"23"},
		{"22"},
		{"21"},
		{"20"},
		{"19"},
		{"18"},
		{"17"},
		{"16"},
		{"15"},
		{"14"},
		{"13"},
		{"12"},
		{"11"},
		{"10"},
		{"9"},
		{"8"},
		{"7"},
		{"6"},
		{"5"},
		{"4"},
		{"3"},
		{"2"},
		{"1"},

	};
    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port+"/mantisbt");
    }

	@Test
	@Parameters({"port", "host"})
	public void testMantisBTDeleteIssue(String port, String host) throws Exception {
		for(int i=0; i<data.length; i++) {
			driver.get("http://"+host+":"+port+"/mantisbt");
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("View Issues")).click();
			//driver.findElement(By.name("bug_arr[]")).click();
			driver.findElement(By.xpath("(//input[@type='checkbox' and @value='"+data[i][0]+"'])")).click();
			new Select(driver.findElement(By.name("action"))).selectByVisibleText("Delete");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.cssSelector("input.button")).click();
			if(i<39) {
				assertTrue(driver.findElement(By.cssSelector("BODY")).getText()
						.matches("^[\\s\\S]*Viewing Issues \\(1 - "+(data.length-(i+1))+" / "+(data.length-(i+1))+"\\)[\\s\\S]*$")); 
				}
			else {
				assertTrue(driver.findElement(By.cssSelector("BODY")).getText()
						.matches("^[\\s\\S]*Viewing Issues \\(0 - "+(data.length-(i+1))+" / "+(data.length-(i+1))+"\\)[\\s\\S]*$")); 
			}
			driver.findElement(By.linkText("Logout")).click();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
