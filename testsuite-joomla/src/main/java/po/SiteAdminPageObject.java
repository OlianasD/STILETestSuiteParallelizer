package po;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SiteAdminPageObject extends PageObject {
	
	@FindBy(xpath = "//*[@id=\"header\"]/div[2]/div[2]/div[2]/div/button/div[2]")
	protected WebElement dropDownToggle;
	
	public SiteAdminPageObject(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void logout() {
		dropDownToggle.click();
		driver.findElement(By.linkText("Log out")).click();
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver = driver.switchTo().window(tabs2.get(0));
	    LoggedNavBar bar = new LoggedNavBar(driver);
	    bar.adminLogout();
	}

}
