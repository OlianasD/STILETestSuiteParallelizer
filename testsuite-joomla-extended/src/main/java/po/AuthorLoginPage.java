package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthorLoginPage extends BaseNavBar {
	
	@FindBy(id="username")
	protected WebElement username;
	
	@FindBy(id="password")
	protected WebElement password;
	
	@FindBy(className = "btn-primary")
	protected WebElement loginBtn;
	
	@FindBy(className = "alert-message")
	protected WebElement alertMsg;
	
	@FindBy(xpath = "//*[@id=\"username-lbl\"]/span[2]")
	protected WebElement emptyLoginAlert;
	
	public AuthorLoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public AuthorLoginPage setUsername(String usr) {
		username.clear();
		username.sendKeys(usr);
		return this;
	}
	
	public AuthorLoginPage setPassword(String psw) {
		password.clear();
		password.sendKeys(psw);
		return this;
	}
	
	public ProfilePageInfo login() {
		loginBtn.click();
		return new ProfilePageInfo(driver);
	}
	
	public AuthorLoginPage badLogin() {
		loginBtn.click();
		return new AuthorLoginPage(driver);
	}
	
	public String getAlertMessage() {
		return alertMsg.getText();
	}
	
	public String getEmptyLoginAlert() {
		return emptyLoginAlert.getText();
	}

}
