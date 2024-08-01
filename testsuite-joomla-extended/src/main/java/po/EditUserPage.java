package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditUserPage extends PageObject {
	
	@FindBy(xpath = "//*[@id=\"myTab\"]/div/button[2]")
	protected WebElement groups;
	
	public EditUserPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public AssignedGroupPage assignedGroups() {
		groups.click();
		return new AssignedGroupPage(driver);
	}
	
	
}
