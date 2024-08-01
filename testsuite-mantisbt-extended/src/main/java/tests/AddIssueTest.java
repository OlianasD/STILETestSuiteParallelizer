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

public class AddIssueTest {

	private WebDriver driver;
	private String[][] data = new String[][] {
		{"Category001", "Project001", "Summary001", "description001"},
		{"Category002", "Project002", "Summary002", "description002"},
		{"Category003", "Project003", "Summary003", "description003"},
		{"Category004", "Project004", "Summary004", "description004"},
		{"Category005", "Project005", "Summary005", "description005"},
		{"Category006", "Project006", "Summary006", "description006"},
		{"Category007", "Project007", "Summary007", "description007"},
		{"Category008", "Project008", "Summary008", "description008"},
		{"Category009", "Project009", "Summary009", "description009"},
		{"Category00910", "Project00910", "Summary00910", "description00910"},
		{"Category00911", "Project00911", "Summary00911", "description00911"},
		{"Category00912", "Project00912", "Summary00912", "description00912"},
		{"Category00913", "Project00913", "Summary00913", "description00913"},
		{"Category00914", "Project00914", "Summary00914", "description00914"},
		{"Category00915", "Project00915", "Summary00915", "description00915"},
		{"Category00916", "Project00916", "Summary00916", "description00916"},
		{"Category00917", "Project00917", "Summary00917", "description00917"},
		{"Category00918", "Project00918", "Summary00918", "description00918"},
		{"Category00919", "Project00919", "Summary00919", "description00919"},
		{"Category00920", "Project00920", "Summary00920", "description00920"},
		{"Category00921", "Project00921", "Summary00921", "description00921"},
		{"Category00922", "Project00922", "Summary00922", "description00922"},
		{"Category00923", "Project00923", "Summary00923", "description00923"},
		{"Category00924", "Project00924", "Summary00924", "description00924"},
		{"Category00925", "Project00925", "Summary00925", "description00925"},
		{"Category00926", "Project00926", "Summary00926", "description00926"},
		{"Category00927", "Project00927", "Summary00927", "description00927"},
		{"Category00928", "Project00928", "Summary00928", "description00928"},
		{"Category00929", "Project00929", "Summary00929", "description00929"},
		{"Category00930", "Project00930", "Summary00930", "description00930"},
		{"Category00931", "Project00931", "Summary00931", "description00931"},
		{"Category00932", "Project00932", "Summary00932", "description00932"},
		{"Category00933", "Project00933", "Summary00933", "description00933"},
		{"Category00934", "Project00934", "Summary00934", "description00934"},
		{"Category00935", "Project00935", "Summary00935", "description00935"},
		{"Category00936", "Project00936", "Summary00936", "description00936"},
		{"Category00937", "Project00937", "Summary00937", "description00937"},
		{"Category00938", "Project00938", "Summary00938", "description00938"},
		{"Category00939", "Project00939", "Summary00939", "description00939"},
		{"Category00940", "Project00940", "Summary00940", "description00940"},
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
	public void testMantisBTAddIssue(String port, String host) throws Exception {
		for(int i=0; i<data.length; i++) {
			driver.get("http://"+host+":"+port+"/mantisbt");
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("administrator");
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.linkText("Report Issue")).click();
			//new Select(driver.findElement(By.name("project_id"))).selectByVisibleText(data[i][1]);
			new Select(driver.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[2]/td[2]/select"))).selectByVisibleText(data[i][1]);
			driver.findElement(By.xpath("(//input[@type='submit' and @value='Select Project'])")).click();
			new Select(driver.findElement(By.name("category_id"))).selectByVisibleText(data[i][0]);
			new Select(driver.findElement(By.name("reproducibility"))).selectByVisibleText("random");
			new Select(driver.findElement(By.name("severity"))).selectByVisibleText("crash");
			new Select(driver.findElement(By.name("priority"))).selectByVisibleText("immediate");
			driver.findElement(By.name("summary")).clear();
			driver.findElement(By.name("summary")).sendKeys(data[i][2]);
			driver.findElement(By.name("description")).clear();
			driver.findElement(By.name("description")).sendKeys(data[i][3]);
			driver.findElement(By.cssSelector("input.button")).click();
			driver.findElement(By.xpath("(//a[contains(text(),'View Issues')])[2]")).click();
			assertEquals(data[i][0], driver.findElement(By.xpath(".//*[@id='buglist']/tbody/tr[4]/td[6]")).getText());
			assertEquals("crash", driver.findElement(By.xpath(".//*[@id='buglist']/tbody/tr[4]/td[7]")).getText());
			assertEquals(data[i][2], driver.findElement(By.xpath(".//*[@id='buglist']/tbody/tr[4]/td[10]")).getText());
			driver.findElement(By.linkText("Logout")).click();
		}
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
