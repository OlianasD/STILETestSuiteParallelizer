package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateFieldGroupPage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement title;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveAndCloseBtn;
	
	public CreateFieldGroupPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateFieldGroupPage setTitle(String ttl) {
		title.sendKeys(ttl);
		return this;
	}
	
	public ManageFieldGroupsPage saveAndClose() {
		saveAndCloseBtn.click();
		return new ManageFieldGroupsPage(driver);
	}

}
