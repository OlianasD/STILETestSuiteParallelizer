package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CustomerLoginPage extends PageObject {
	
	@FindBy(id = "email")
	protected WebElement email;
	
	@FindBy(id = "password")
	protected WebElement password;
	
	@FindBy(id = "customerloginForm")
	protected WebElement loginBtn;

	public CustomerLoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CustomerLoginPage setEmail(String mail) {
		email.sendKeys(mail);
		return this;
	}
	
	public CustomerLoginPage setPassword(String psw) {
		password.sendKeys(psw);
		return this;
	}
	
	public CustomerInfoPage login() {
		loginBtn.click();
		return new CustomerInfoPage(driver);
	}
	
	

}
