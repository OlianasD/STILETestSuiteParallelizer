package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditProfilePage extends LoggedNavBar {
	
	@FindBy(id = "jform_name")
	protected WebElement name;
	
	@FindBy(id = "jform_password1")
	protected WebElement password;
	
	@FindBy(id = "jform_password2")
	protected WebElement confirmPassword;
	
	@FindBy(className = "btn-primary")
	protected WebElement submitBtn;
	
	
	public EditProfilePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return name.getAttribute("value");
	}
	
	public EditProfilePage setPassword(String psw) {
		password.sendKeys(psw);
		return this;
	}
	
	public EditProfilePage confirmPassword(String psw) {
		confirmPassword.sendKeys(psw);
		return this;
	}
	
	public EditProfilePage submit() {
		submitBtn.click();
		return this;
	}
	
	public String getAlertMessage() {
		return driver.findElement(By.className("alert-message")).getText();
	}
	
	
}
