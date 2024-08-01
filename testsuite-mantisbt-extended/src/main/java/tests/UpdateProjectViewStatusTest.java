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

public class UpdateProjectViewStatusTest {

	private WebDriver driver;
	private String[][] data = new String[][] {
		{"Project001", "3"},
		{"Project002", "4"},
		{"Project003", "5"},
		{"Project004", "6"},
		{"Project005", "7"},
		{"Project006", "8"},
		{"Project007", "9"},
		{"Project008", "10"},
		{"Project009", "11"},
		{"Project00910", "12"},
		{"Project00911", "13"},
		{"Project00912", "14"},
		{"Project00913", "15"},
		{"Project00914", "16"},
		{"Project00915", "17"},
		{"Project00916", "18"},
		{"Project00917", "19"},
		{"Project00918", "20"},
		{"Project00919", "21"},
		{"Project00920", "22"},
		{"Project00921", "23"},
		{"Project00922", "24"},
		{"Project00923", "25"},
		{"Project00924", "26"},
		{"Project00925", "27"},
		{"Project00926", "28"},
		{"Project00927", "29"},
		{"Project00928", "30"},
		{"Project00929", "31"},
		{"Project00930", "32"},
		{"Project00931", "33"},
		{"Project00932", "34"},
		{"Project00933", "35"},
		{"Project00934", "36"},
		{"Project00935", "37"},
		{"Project00936", "38"},
		{"Project00937", "39"},
		{"Project00938", "40"},
		{"Project00939", "41"},
		{"Project00940", "42"},
	};

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTUpdateProjectViewStatus() throws Exception {
		for(int i=0; i<data.length; i++) {
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Manage")).click();
			driver.findElement(By.linkText("Manage Projects")).click();
			driver.findElement(By.linkText(data[i][0])).click();
			new Select(driver.findElement(By.name("view_state"))).selectByVisibleText("private");
			driver.findElement(By.cssSelector("input.button")).click();
			assertEquals("private", driver.findElement(By.xpath("html/body/table[3]/tbody/tr["+data[i][1]+"]/td[4]")).getText());
			driver.findElement(By.linkText("Logout")).click();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
