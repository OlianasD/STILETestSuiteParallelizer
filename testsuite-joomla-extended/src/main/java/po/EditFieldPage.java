package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class EditFieldPage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement title;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveAndCloseBtn;
	
	@FindBy(id = "jform_group_id")
	protected WebElement groupSelect;
	
	@FindBy(xpath = "//*[@id=\"jform_title-lbl\"]/span[2]")
	protected WebElement emptyTitleAlert;
	
	@FindBy(xpath = "//*[@id=\"toolbar-cancel\"]/button")
	protected WebElement cancel;
	
	public EditFieldPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public EditFieldPage setTitle(String ttl) {
		title.sendKeys(ttl);
		return this;
	}
	
	public EditFieldPage selectGroup(String group) {
		new Select(groupSelect).selectByVisibleText(group);
		return this;
	}
	
	public ManageFieldsPage saveAndClose() {
		saveAndCloseBtn.click();
		return new ManageFieldsPage(driver);
	}
	
	public EditFieldPage saveError() {
		saveAndCloseBtn.click();
		return new EditFieldPage(driver);
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
	
	public ManageFieldsPage cancel() {
		cancel.click();
		return new ManageFieldsPage(driver);
	}

}
