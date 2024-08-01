package po;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class SiteAdminLogin extends PageObject {
	
	@FindBy(id = "mod-login-username")
	protected WebElement username;
	
	@FindBy(id = "mod-login-password")
	protected WebElement password;
	
	@FindBy(id = "btn-login-submit")
	protected WebElement loginBtn;
	
	@FindBy(className = "alert-message")
	protected WebElement alertMsg;
	
	@FindBy(xpath = "//*[@id=\"form-login\"]/fieldset/div[1]/label/span")
	protected WebElement emptyLoginAlert;
	
	@FindBy(xpath = "//*[@id=\"form-login\"]/fieldset/div[2]/label/span")
	protected WebElement emptyPasswordAlert;
	
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
	
	public SiteAdminLogin badLogin() {
		loginBtn.click();
		return new SiteAdminLogin(driver);
	}
	
	public String getAlertMessage() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alertMsg.getText();
	}
	
	public String getEmptyLoginAlert() {
		return emptyLoginAlert.getText();
	}
	
	public String getEmptyPasswordAlert() {
		return emptyPasswordAlert.getText();
	}
	
	
	
}
