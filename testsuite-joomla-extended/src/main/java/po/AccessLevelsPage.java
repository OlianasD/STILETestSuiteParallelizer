package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccessLevelsPage extends SiteAdminPageObject {
	
	@FindBy(xpath = "//*[@id=\"toolbar-new\"]/button")
	protected WebElement createAccessLevelBtn;
	
	public AccessLevelsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateAccessLevelPage createAccessLevel() {
		createAccessLevelBtn.click();
		return new CreateAccessLevelPage(driver);
	}
	
	public String getAlertMessage() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver.findElement(By.className("alert-message")).getText();
	}
	
	public CreateAccessLevelPage goToAccessLevel(String lvl) {
		driver.findElement(By.linkText(lvl)).click();
		return new CreateAccessLevelPage(driver);
	}
	
	public AccessLevelsPage selectSecondLevel() {
		driver.findElement(By.id("cb1")).click();
		return this;
	}
	
	public AccessLevelsPage deleteSelectedLevel() {
		driver.findElement(By.xpath("//*[@id=\"toolbar-delete\"]/button")).click();
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
		return this;
	}

}
