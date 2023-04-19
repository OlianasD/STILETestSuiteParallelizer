package po;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SiteAdminLogin extends PageObject {
	
	@FindBy(id = "mod-login-username")
	protected WebElement username;
	
	@FindBy(id = "mod-login-password")
	protected WebElement password;
	
	@FindBy(id = "btn-login-submit")
	protected WebElement loginBtn;
	
	public SiteAdminLogin(WebDriver driver) {
		super(driver);
	}
	
	public SiteAdminLogin setUsername(String usr) {
		username.clear();
		username.sendKeys(usr);
		return this;
	}
	
	public SiteAdminLogin setPassword(String psw) {
		password.clear();
		password.sendKeys(psw);
		return this;
	}
	
	public SiteAdminHome login() {
		loginBtn.click();
		return new SiteAdminHome(driver);
	}
	
	
	
}
