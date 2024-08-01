package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateFieldGroupPage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement title;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveAndCloseBtn;
	
	@FindBy(xpath = "//*[@id=\"jform_title-lbl\"]/span[2]")
	protected WebElement emptyTitleAlert;
	
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
	
	public CreateFieldGroupPage saveError() {
		saveAndCloseBtn.click();
		return new CreateFieldGroupPage(driver);
	}
	
	public String getEmptyTitleAlert() {
		return emptyTitleAlert.getText();
	}

}
