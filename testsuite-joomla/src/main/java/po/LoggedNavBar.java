package po;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoggedNavBar extends BaseNavBar {
	
	@FindBy(linkText = "Site Administrator")
	protected WebElement siteAdmin;
	
	@FindBy(linkText = "Create a Post")
	protected WebElement createPost;
	
	@FindBy(linkText = "Log out")
	protected WebElement logout;
	
	public LoggedNavBar(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public SiteAdminLogin siteAdmin() {
		siteAdmin.click();
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver = driver.switchTo().window(tabs2.get(1));
		return new SiteAdminLogin(driver);
	}
	
	public CreatePostPage createPost() {
		createPost.click();
		return new CreatePostPage(driver);
	}
	
	public void adminLogout() {
		logout.click();
		driver.findElement(By.className("btn-primary")).click();
	}
	
	public void standardUserLogOut() {
		authorLogin.click();
		driver.findElement(By.className("btn-primary")).click();
	}

}
