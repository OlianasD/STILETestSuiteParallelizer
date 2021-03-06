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

public class EnrolPasswordCourseWrongPasswordUserTest {

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
	public void testClarolineEnrolPasswordCourseWrongPasswordUser() throws Exception {
		driver.findElement(By.id("login")).clear();
		driver.findElement(By.id("login")).sendKeys("user001");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("password001");
		driver.findElement(By.xpath("//*[@id='loginBox']/form/fieldset/button")).click();
		driver.findElement(By.linkText("Enrol on a new course")).click();
		driver.findElement(By.id("coursesearchbox_keyword")).clear();
		driver.findElement(By.id("coursesearchbox_keyword")).sendKeys("Course002");
		driver.findElement(By.xpath("//*[@id='claroBody']/form/button")).click();
//		driver.findElement(By.xpath("//*[@id='claroBody']/dl/dt[2]/a[1]/img")).click();
		driver.findElement(By.xpath("//*[@id='claroBody']/dl[1]/dt[1]/a[1]/img")).click();
		driver.findElement(By.name("registrationKey")).clear();
		driver.findElement(By.name("registrationKey")).sendKeys("passowrd");
		driver.findElement(By.xpath("//*[@id='claroBody']/div[2]/div[2]/form/p/input")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id='claroBody']/div[2]/div[1]")).getText()
				.contains("Invalid enrolment key given"));
		driver.findElement(By.linkText("Logout")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}
}
