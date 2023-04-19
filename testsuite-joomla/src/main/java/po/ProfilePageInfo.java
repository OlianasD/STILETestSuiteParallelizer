package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePageInfo extends LoggedNavBar {
	
	@FindBy(xpath = "//*[@id=\"users-profile-core\"]/dl/dd[1]")
	protected WebElement name;
	
	@FindBy(xpath = "//*[@id=\"content\"]/div[3]/ul/li/a")
	protected WebElement editBtn;
	
	public ProfilePageInfo(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return name.getText();
	}
	
	public EditProfilePage editProfile() {
		editBtn.click();
		return new EditProfilePage(driver);
	}
	
	

}
