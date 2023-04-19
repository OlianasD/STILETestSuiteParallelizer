package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BaseNavBar {
	
	public WebDriver driver;
	
	@FindBy(linkText = "Home")
	protected WebElement home;
	
	@FindBy(linkText = "Author Login")
	protected WebElement authorLogin;
	
	public BaseNavBar(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public AuthorLoginPage authorLogin() {
		authorLogin.click();
		return new AuthorLoginPage(driver);
	}
	
	public LoggedHome home() {
		home.click();
		return new LoggedHome(driver);
	}
	
}
