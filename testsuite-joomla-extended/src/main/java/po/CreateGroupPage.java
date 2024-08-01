package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateGroupPage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement groupTitle;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveAndCloseBtn;
	
	@FindBy(xpath = "//*[@id=\"jform_title-lbl\"]/span[2]")
	protected WebElement emptyTitleAlert;
	
	@FindBy(xpath = "//*[@id=\"toolbar-cancel\"]/button")
	protected WebElement cancel;
	
	public CreateGroupPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateGroupPage setTitle(String title) {
		groupTitle.sendKeys(title);
		return this;
	}
	
	public ManageGroupsPage saveAndClose() {
		saveAndCloseBtn.click();
		return new ManageGroupsPage(driver);
	}
	
	public CreateGroupPage saveError() {
		saveAndCloseBtn.click();
		return new CreateGroupPage(driver);
	}
	
	public String getEmptyTitleAlert() {
		return emptyTitleAlert.getText();
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
	
	public ManageGroupsPage cancel() {
		cancel.click();
		return new ManageGroupsPage(driver);
	}
	
	

}
