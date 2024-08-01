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

public class AddCategoryTest {

	private WebDriver driver;
	private String[][] data = new String[][] {
		{"Category001", "Project001", "3"},
		{"Category002", "Project002", "4"},
		{"Category003", "Project003", "5"},
		{"Category004", "Project004", "6"},
		{"Category005", "Project005", "7"},
		{"Category006", "Project006", "8"},
		{"Category007", "Project007", "9"},
		{"Category008", "Project008", "10"},
		{"Category009", "Project009", "11"},
		{"Category00910", "Project00910", "12"},
		{"Category00911", "Project00911", "13"},
		{"Category00912", "Project00912", "14"},
		{"Category00913", "Project00913", "15"},
		{"Category00914", "Project00914", "16"},
		{"Category00915", "Project00915", "17"},
		{"Category00916", "Project00916", "18"},
		{"Category00917", "Project00917", "19"},
		{"Category00918", "Project00918", "20"},
		{"Category00919", "Project00919", "21"},
		{"Category00920", "Project00920", "22"},
		{"Category00921", "Project00921", "23"},
		{"Category00922", "Project00922", "24"},
		{"Category00923", "Project00923", "25"},
		{"Category00924", "Project00924", "26"},
		{"Category00925", "Project00925", "27"},
		{"Category00926", "Project00926", "28"},
		{"Category00927", "Project00927", "29"},
		{"Category00928", "Project00928", "30"},
		{"Category00929", "Project00929", "31"},
		{"Category00930", "Project00930", "32"},
		{"Category00931", "Project00931", "33"},
		{"Category00932", "Project00932", "34"},
		{"Category00933", "Project00933", "35"},
		{"Category00934", "Project00934", "36"},
		{"Category00935", "Project00935", "37"},
		{"Category00936", "Project00936", "38"},
		{"Category00937", "Project00937", "39"},
		{"Category00938", "Project00938", "40"},
		{"Category00939", "Project00939", "41"},
		{"Category00940", "Project00940", "42"},
	};

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://"+host+":"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTAddCategory() throws Exception {
		for(int i=0; i<data.length; i++) {
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Manage")).click();
			driver.findElement(By.linkText("Manage Projects")).click();
			driver.findElement(By.linkText(data[i][1])).click();
			driver.findElement(By.xpath("html/body/div[6]/a[1]/table/tbody/tr[4]/td/form/input[3]")).clear();
			driver.findElement(By.xpath("html/body/div[6]/a[1]/table/tbody/tr[4]/td/form/input[3]"))
					.sendKeys(data[i][0]);
			driver.findElement(By.cssSelector("td.left > form > input.button")).click();
			assertEquals(data[i][0],
					driver.findElement(By.xpath("html/body/div[6]/a[1]/table/tbody/tr[3]/td[1]")).getText());
			driver.findElement(By.linkText("Logout")).click();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
