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

public class AddUserTest {

    private WebDriver driver;
    private String[][] data = new String[][] {
    	{"username001", "username1", "username001@username.it", "4"},
    	{"username002", "username2", "username002@username.it", "5"},
    	{"username003", "username3", "username003@username.it", "6"},
    	{"username004", "username4", "username004@username.it", "7"},
    	{"username005", "username5", "username005@username.it", "8"},
    	{"username006", "username6", "username006@username.it", "9"},
    	{"username007", "username7", "username007@username.it", "10"},
    	{"username008", "username8", "username008@username.it", "11"},
    	{"username009", "username9", "username009@username.it", "12"},
    	{"username00A10", "usernameA10", "username00A10@username.it", "13"},
    	{"username00A11", "usernameA11", "username00A11@username.it", "14"},
    	{"username00A12", "usernameA12", "username00A12@username.it", "15"},
    	{"username00A13", "usernameA13", "username00A13@username.it", "16"},
    	{"username00A14", "usernameA14", "username00A14@username.it", "17"},
    	{"username00A15", "usernameA15", "username00A15@username.it", "18"},
    	{"username00A16", "usernameA16", "username00A16@username.it", "19"},
    	{"username00A17", "usernameA17", "username00A17@username.it", "20"},
    	{"username00A18", "usernameA18", "username00A18@username.it", "21"},
    	{"username00A19", "usernameA19", "username00A19@username.it", "22"},
    	{"username00A20", "usernameA20", "username00A20@username.it", "23"},
    	{"username00A21", "usernameA21", "username00A21@username.it", "24"},
    	{"username00A22", "usernameA22", "username00A22@username.it", "25"},
    	{"username00A23", "usernameA23", "username00A23@username.it", "26"},
    	{"username00A24", "usernameA24", "username00A24@username.it", "27"},
    	{"username00A25", "usernameA25", "username00A25@username.it", "28"},
    	{"username00A26", "usernameA26", "username00A26@username.it", "29"},
    	{"username00A27", "usernameA27", "username00A27@username.it", "30"},
    	{"username00A28", "usernameA28", "username00A28@username.it", "31"},
    	{"username00A29", "usernameA29", "username00A29@username.it", "32"},
    	{"username00A30", "usernameA30", "username00A30@username.it", "33"},
    	{"username00A31", "usernameA31", "username00A31@username.it", "34"},
    	{"username00A32", "usernameA32", "username00A32@username.it", "35"},
    	{"username00A33", "usernameA33", "username00A33@username.it", "36"},
    	{"username00A34", "usernameA34", "username00A34@username.it", "37"},
    	{"username00A35", "usernameA35", "username00A35@username.it", "38"},
    	{"username00A36", "usernameA36", "username00A36@username.it", "39"},
    	{"username00A37", "usernameA37", "username00A37@username.it", "40"},
    	{"username00A38", "usernameA38", "username00A38@username.it", "41"},
    	{"username00A39", "usernameA39", "username00A39@username.it", "42"},
    	{"username00A40", "usernameA40", "username00A40@username.it", "43"},


    };

    @BeforeMethod
    @Parameters({"port", "browserPort", "host"})
	public void setUp(String port, String browserPort, String host) {
        driver = DriverProvider.getInstance().getRemoteWebDriver(browserPort);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    @Parameters({"port", "host"})
    public void testMantisBTAddUser(String port, String host) {
    	for(int i=0; i<data.length; i++) {
	    	driver.get("http://"+host+":"+port+"/mantisbt");
	        driver.findElement(By.name("username")).clear();
	        driver.findElement(By.name("username")).sendKeys("administrator");
	        driver.findElement(By.name("password")).clear();
	        driver.findElement(By.name("password")).sendKeys("root");
	        driver.findElement(By.cssSelector("input.button")).click();
	        driver.findElement(By.linkText("Manage")).click();
	        driver.findElement(By.linkText("Manage Users")).click();
	        driver.findElement(By.cssSelector("td.form-title > form > input.button-small")).click();
	        driver.findElement(By.name("username")).clear();
	        driver.findElement(By.name("username")).sendKeys(data[i][0]);
	        driver.findElement(By.name("realname")).clear();
	        driver.findElement(By.name("realname")).sendKeys(data[i][1]);
	        driver.findElement(By.name("email")).clear();
	        driver.findElement(By.name("email")).sendKeys(data[i][2]);
	        driver.findElement(By.name("realname")).clear();
	        driver.findElement(By.name("realname")).sendKeys(data[i][1]);
	        driver.findElement(By.name("email")).clear();
	        driver.findElement(By.name("email")).sendKeys(data[i][2]);
	        new Select(driver.findElement(By.name("access_level"))).selectByVisibleText("updater");
	        driver.findElement(By.cssSelector("input.button")).click();
	        driver.findElement(By.xpath("html/body/div[2]/span[3]/a")).click();
	        driver.findElement(By.linkText("Manage Users")).click();
	        assertEquals(data[i][0], driver.findElement(By.xpath("html/body/table[3]/tbody/tr["+data[i][3]+"]/td[1]/a")).getText());
	        assertEquals(data[i][1], driver.findElement(By.xpath("html/body/table[3]/tbody/tr["+data[i][3]+"]/td[2]")).getText());
	        assertEquals(data[i][2], driver.findElement(By.xpath("html/body/table[3]/tbody/tr["+data[i][3]+"]/td[3]")).getText());
	        assertEquals("updater", driver.findElement(By.xpath("html/body/table[3]/tbody/tr["+data[i][3]+"]/td[4]")).getText());
	        driver.findElement(By.linkText("Logout")).click();
    	}
    }

    @AfterMethod
	public void tearDown() {
        driver.quit();
    }
}
