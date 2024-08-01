package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContentSidebar extends SiteAdminHome {
	
	@FindBy(xpath = "//*[@id=\"collapse1\"]/li[5]/a")
	protected WebElement fields;
	
	@FindBy(xpath = "//*[@id=\"collapse1\"]/li[6]/a/span")
	protected WebElement fieldGroups;
	
	public ContentSidebar(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public ManageFieldsPage fields() {
		fields.click();
		return new ManageFieldsPage(driver);
	}
	
	public ManageFieldGroupsPage fieldGroups() {
		fieldGroups.click();
		return new ManageFieldGroupsPage(driver);
	}

}
