package tests;

import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import utils.DriverProvider;
import utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class SearchCourseTest {

	private WebDriver driver;

	@BeforeMethod
    @Parameters("port")
	public void setUp(String port) throws Exception {
		driver = DriverProvider.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:"
                + port + "/claroline11110/claroline/index.php");
	}

	@Test
	public void testClarolineSearchCourse() throws Exception {
		driver.findElement(By.id("login")).clear();
		driver.findElement(By.id("login")).sendKeys("admin");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id='loginBox']/form/fieldset/button")).click();
		driver.findElement(By.linkText("Platform administration")).click();
		driver.findElement(By.id("search_course")).clear();
		driver.findElement(By.id("search_course")).sendKeys("Course001");
		driver.findElement(By.xpath("html/body/div[1]/div[2]/ul/li[2]/ul/li[1]/form/input[2]")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id='claroBody']/table[2]/tbody/tr/td[2]")).getText().contains("Course001"));
		assertTrue(driver.findElement(By.xpath("//*[@id='L0']")).getText().contains("001"));
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}
}
