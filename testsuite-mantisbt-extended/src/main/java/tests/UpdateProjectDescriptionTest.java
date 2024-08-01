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

public class UpdateProjectDescriptionTest {

	private WebDriver driver;
	private String[][] data = new String[][] {
		{"Project001", "description001"},
		{"Project002", "description002"},
		{"Project003", "description003"},
		{"Project004", "description004"},
		{"Project005", "description005"},
		{"Project006", "description006"},
		{"Project007", "description007"},
		{"Project008", "description008"},
		{"Project009", "description009"},
		{"Project00910", "description00910"},
		{"Project00911", "description00911"},
		{"Project00912", "description00912"},
		{"Project00913", "description00913"},
		{"Project00914", "description00914"},
		{"Project00915", "description00915"},
		{"Project00916", "description00916"},
		{"Project00917", "description00917"},
		{"Project00918", "description00918"},
		{"Project00919", "description00919"},
		{"Project00920", "description00920"},
		{"Project00921", "description00921"},
		{"Project00922", "description00922"},
		{"Project00923", "description00923"},
		{"Project00924", "description00924"},
		{"Project00925", "description00925"},
		{"Project00926", "description00926"},
		{"Project00927", "description00927"},
		{"Project00928", "description00928"},
		{"Project00929", "description00929"},
		{"Project00930", "description00930"},
		{"Project00931", "description00931"},
		{"Project00932", "description00932"},
		{"Project00933", "description00933"},
		{"Project00934", "description00934"},
		{"Project00935", "description00935"},
		{"Project00936", "description00936"},
		{"Project00937", "description00937"},
		{"Project00938", "description00938"},
		{"Project00939", "description00939"},
		{"Project00940", "description00940"},

	};

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

	@Test
	@Parameters({"port", "host"})
	public void testMantisBTUpdateProjectDescription(String port, String host) throws Exception {
		for(int i=0; i<data.length; i++) {
			driver.get("http://"+host+":"+port+"/mantisbt");
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Manage")).click();
			driver.findElement(By.linkText("Manage Projects")).click();
			driver.findElement(By.linkText(data[i][0])).click();
			driver.findElement(By.name("description")).clear();
			driver.findElement(By.name("description")).sendKeys(data[i][1]);
			driver.findElement(By.cssSelector("input.button")).click();
			assertEquals(data[i][1], driver.findElement(By.xpath("html/body/table[3]/tbody/tr["+(i+3)+"]/td[5]")).getText());
			driver.findElement(By.linkText("Logout")).click();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
