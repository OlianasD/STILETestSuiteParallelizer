package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddUserPage extends PageObject {
	
	@FindBy(id = "jform_name")
	protected WebElement name;
	
	@FindBy(id = "jform_username")
	protected WebElement loginName;
	
	@FindBy(id = "jform_password")
	protected WebElement password;
	
	@FindBy(id = "jform_password2")
	protected WebElement confirmPassword;
	
	@FindBy(id = "jform_email")
	protected WebElement email;
	
	@FindBy(className = "button-save")
	protected WebElement saveAndClose;
	
	public AddUserPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public AddUserPage setName(String nm) {
		name.clear();
		name.sendKeys(nm);
		return this;
	}
	
	public AddUserPage setLoginName(String user) {
		loginName.clear();
		loginName.sendKeys(user);
		return this;
	}
	
	public AddUserPage setPassword(String psw) {
		password.clear();
		password.sendKeys(psw);
		return this;
	}
	
	public AddUserPage confirmPassword(String psw) {
		confirmPassword.clear();
		confirmPassword.sendKeys(psw);
		return this;
	}
	
	public AddUserPage setEmail(String mail) {
		email.clear();
		email.sendKeys(mail);
		return this;
	}
	
	public ManageUsersPage saveAndClose() {
		saveAndClose.click();
		return new ManageUsersPage(driver);
	}

}
