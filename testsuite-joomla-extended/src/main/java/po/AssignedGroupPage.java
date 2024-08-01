package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AssignedGroupPage extends EditUserPage {
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveAndCloseBtn;
	
	@FindBy(xpath = "//*[@id=\"toolbar-cancel\"]/button")
	protected WebElement cancel;
	
	public AssignedGroupPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	//group id = 10
	public AssignedGroupPage checkGroup(int id) {
		driver.findElement(By.id("1group_"+id)).click();
		return this;
	}
	
	public boolean isGroupChecked(int id) {
		return driver.findElement(By.id("1group_"+id)).isSelected();
	}
	
	public ManageUsersPage saveAndClose() {
		saveAndCloseBtn.click();
		return new ManageUsersPage(driver);
	}
	
	public ManageUsersPage cancel() {
		cancel.click();
		return new ManageUsersPage(driver);
	}
	

}
