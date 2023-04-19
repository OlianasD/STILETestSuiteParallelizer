package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateGroupPage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement groupTitle;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveAndCloseBtn;
	
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
	
	

}
