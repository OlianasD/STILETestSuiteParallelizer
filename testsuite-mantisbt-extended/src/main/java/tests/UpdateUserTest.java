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

public class UpdateUserTest {

	private WebDriver driver;
	private String[][] data = new String[][] {
		{"username001", "ChangedUsername001"},
		{"username002", "ChangedUsername002"},
		{"username003", "ChangedUsername003"},
		{"username004", "ChangedUsername004"},
		{"username005", "ChangedUsername005"},
		{"username006", "ChangedUsername006"},
		{"username007", "ChangedUsername007"},
		{"username008", "ChangedUsername008"},
		{"username009", "ChangedUsername009"},
		{"username00A10", "ChangedUsername00A10"},
		{"username00A11", "ChangedUsername00A11"},
		{"username00A12", "ChangedUsername00A12"},
		{"username00A13", "ChangedUsername00A13"},
		{"username00A14", "ChangedUsername00A14"},
		{"username00A15", "ChangedUsername00A15"},
		{"username00A16", "ChangedUsername00A16"},
		{"username00A17", "ChangedUsername00A17"},
		{"username00A18", "ChangedUsername00A18"},
		{"username00A19", "ChangedUsername00A19"},
		{"username00A20", "ChangedUsername00A20"},
		{"username00A21", "ChangedUsername00A21"},
		{"username00A22", "ChangedUsername00A22"},
		{"username00A23", "ChangedUsername00A23"},
		{"username00A24", "ChangedUsername00A24"},
		{"username00A25", "ChangedUsername00A25"},
		{"username00A26", "ChangedUsername00A26"},
		{"username00A27", "ChangedUsername00A27"},
		{"username00A28", "ChangedUsername00A28"},
		{"username00A29", "ChangedUsername00A29"},
		{"username00A30", "ChangedUsername00A30"},
		{"username00A31", "ChangedUsername00A31"},
		{"username00A32", "ChangedUsername00A32"},
		{"username00A33", "ChangedUsername00A33"},
		{"username00A34", "ChangedUsername00A34"},
		{"username00A35", "ChangedUsername00A35"},
		{"username00A36", "ChangedUsername00A36"},
		{"username00A37", "ChangedUsername00A37"},
		{"username00A38", "ChangedUsername00A38"},
		{"username00A39", "ChangedUsername00A39"},
		{"username00A40", "ChangedUsername00A40"},


	};

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

	@Test
	@Parameters({"port", "host"})
	public void testMantisBTUpdateUser(String port, String host) throws Exception {
		for(int i=0; i<data.length; i++) {
			driver.get("http://"+host+":"+port+"/mantisbt");
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Manage")).click();
			driver.findElement(By.linkText("Manage Users")).click();
			driver.findElement(By.linkText(data[i][0])).click();
			driver.findElement(By.name("realname")).clear();
			driver.findElement(By.name("realname")).sendKeys(data[i][1]);
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Proceed")).click();
			assertEquals(data[i][1], driver.findElement(By.name("realname")).getAttribute("value"));
			driver.findElement(By.linkText("Logout")).click();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
