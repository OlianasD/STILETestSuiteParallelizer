package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SiteAdminHome extends PageObject {
	
	
	@FindBy(xpath = "//*[@id=\"cpanel-modules\"]/div/div/div[1]/div/div[2]/nav/ul/li[1]/ul/li[1]/a")
	//@FindBy(linkText = "Users")
	protected WebElement usersLink;
	
	@FindBy(linkText = "Articles")
	protected WebElement articles;
	
	@FindBy(linkText = "Article Categories")
	protected WebElement categories;
	
	@FindBy(linkText = "Menus")
	protected WebElement menus;
	
	@FindBy(xpath = "//*[@id=\"menu12\"]/li[5]/a")
	protected WebElement sideUsers;
	
	@FindBy(xpath = "//*[@id=\"menu12\"]/li[2]/a")
	protected WebElement sideContent;
	
	public SiteAdminHome(WebDriver driver) {
		super(driver);
	}
	
	public ManageUsersPage users() {
		usersLink.click();
		//driver.findElement(By.linkText("Users")).click();
		return new ManageUsersPage(driver);
	}
	
	public ManageArticlesPage articles() {
		articles.click();
		return new ManageArticlesPage(driver);
	}
	
	public ManageCategoriesPage categories() {
		categories.click();
		return new ManageCategoriesPage(driver);
	}
	
	public MenuPage menus() {
		menus.click();
		return new MenuPage(driver);
	}
	
	public UsersSidebar sideUsers() {
		sideUsers.click();
		return new UsersSidebar(driver);
	}
	
	public ContentSidebar sideContent() {
		sideContent.click();
		return new ContentSidebar(driver);
	}
	
	
	
}
