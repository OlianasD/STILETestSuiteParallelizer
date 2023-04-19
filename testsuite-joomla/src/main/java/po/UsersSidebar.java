package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsersSidebar extends SiteAdminHome {
	
	@FindBy(xpath = "//*[@id=\"collapse4\"]/li[2]/a")
	protected WebElement groups;
	
	public UsersSidebar(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public ManageGroupsPage groups() {
		groups.click();
		return new ManageGroupsPage(driver);
	}
	
	

}
