package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateAccessLevelPage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement title;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"toolbar-cancel\"]/button")
	protected WebElement cancel;
	
	@FindBy(xpath = "//*[@id=\"myTab\"]/div/button[2]")
	protected WebElement groupsBtn;

	public CreateAccessLevelPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateAccessLevelPage setTitle(String ttl) {
		title.sendKeys(ttl);
		return this;
	}
	
	public AccessLevelsPage save() {
		saveBtn.click();
		return new AccessLevelsPage(driver);
	}
	
	public CreateAccessLevelPage saveError() {
		saveBtn.click();
		return new CreateAccessLevelPage(driver);
	}
	
	public AccessLevelsPage cancel() {
		cancel.click();
		return new AccessLevelsPage(driver);
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
	
	public CreateAccessLevelPage groups() {
		groupsBtn.click();
		return this;
	}
	
	public CreateAccessLevelPage selectGroup(int i) {
		driver.findElement(By.id("1group_"+i)).click();
		return this;
	}
	

}
