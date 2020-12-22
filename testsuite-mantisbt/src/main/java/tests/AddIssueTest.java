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

    @BeforeMethod
    @Parameters("port")
	public void setUp(String port) {
        driver = DriverProvider.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://localhost:"+port+"/mantisbt");
    }

	@Test
	public void testMantisBTAddIssue() throws Exception {
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("administrator");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("root");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.linkText("Report Issue")).click();
		new Select(driver.findElement(By.name("category_id"))).selectByVisibleText("Category001");
		new Select(driver.findElement(By.name("reproducibility"))).selectByVisibleText("random");
		new Select(driver.findElement(By.name("severity"))).selectByVisibleText("crash");
		new Select(driver.findElement(By.name("priority"))).selectByVisibleText("immediate");
		driver.findElement(By.name("summary")).clear();
		driver.findElement(By.name("summary")).sendKeys("Summary001");
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("description001");
		driver.findElement(By.cssSelector("input.button")).click();
		driver.findElement(By.xpath("(//a[contains(text(),'View Issues')])[2]")).click();
		assertEquals("Category001", driver.findElement(By.xpath(".//*[@id='buglist']/tbody/tr[4]/td[6]")).getText());
		assertEquals("crash", driver.findElement(By.xpath(".//*[@id='buglist']/tbody/tr[4]/td[7]")).getText());
		assertEquals("Summary001", driver.findElement(By.xpath(".//*[@id='buglist']/tbody/tr[4]/td[10]")).getText());
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

}
