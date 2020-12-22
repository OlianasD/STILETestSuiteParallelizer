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

public class AddressBookAddAddressBookTest {

	private WebDriver driver;

	@BeforeMethod
    @Parameters("port")
	public void setUp(String port) {
        driver = DriverProvider.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("http://localhost:"+port+"/addressbook/index.php");
	}

	@Test
	public void testAddressBookAddAddressBook() throws Exception {
		driver.findElement(By.name("user")).sendKeys("admin");
		driver.findElement(By.name("pass")).sendKeys("secret");
		driver.findElement(By.xpath(".//*[@id='content']/form/input[3]")).click();
		driver.findElement(By.linkText("add new")).click();
		driver.findElement(By.name("quickadd")).click();
		driver.findElement(By.name("firstname")).clear();
		driver.findElement(By.name("firstname")).sendKeys("firstname");
		driver.findElement(By.name("lastname")).clear();
		driver.findElement(By.name("lastname")).sendKeys("lastname");
		driver.findElement(By.name("address")).clear();
		driver.findElement(By.name("address")).sendKeys("address");
		driver.findElement(By.name("home")).clear();
		driver.findElement(By.name("home")).sendKeys("01056321");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("mail@mail.it");
		new Select(driver.findElement(By.name("bday"))).selectByVisibleText("19");
		new Select(driver.findElement(By.name("bmonth"))).selectByVisibleText("June");
		driver.findElement(By.name("byear")).clear();
		driver.findElement(By.name("byear")).sendKeys("1985");
		driver.findElement(By.name("submit")).click();
		assertTrue(driver.findElement(By.xpath(".//*[@id='content']/div")).getText()
				.contains("Information entered into address book"));
		driver.findElement(By.linkText("home page")).click();
		assertTrue(driver.findElement(By.xpath("html/body/div[1]/div[4]/label/strong")).getText()
				.contains("Number of results: 1"));
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();

	}
}
