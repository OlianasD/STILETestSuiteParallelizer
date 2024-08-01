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
	
	@FindBy(xpath = "//*[@id=\"collapse2\"]/li[1]/a")
	protected WebElement manageMenus;
	
	@FindBy(xpath = "//*[@id=\"menu12\"]/li[5]/a")
	protected WebElement sideUsers;
	
	@FindBy(xpath = "//*[@id=\"menu12\"]/li[2]/a")
	protected WebElement sideContent;
	
	@FindBy(xpath = "//*[@id=\"menu12\"]/li[4]/a")
	protected WebElement components;
	
	public SiteAdminHome(WebDriver driver) {
		super(driver);
	}
	
	public ManageUsersPage users() {
		usersLink.click();
		//driver.findElement(By.linkText("Users")).click();
		return new ManageUsersPage(driver);
	}
	
	public AccessLevelsPage accessLevels() {
		usersLink.click();
		driver.findElement(By.linkText("Access Levels")).click();
		return new AccessLevelsPage(driver);
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
	
	public MenuPage manageMenus() {
		menus.click();
		manageMenus.click();
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
	
	public BannerPage banners() {
		components.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.findElement(By.linkText("Banners")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/nav/ul/li[4]/ul/li[1]/ul/li[1]/a")).click();
		return new BannerPage(driver);
	}
	
	public TrackingPage tracking() {
		components.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.findElement(By.linkText("Banners")).click();
		driver.findElement(By.linkText("Tracks")).click();
		return new TrackingPage(driver);
	}
	
	public ContactsPage contacts() {
		components.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.findElement(By.linkText("Contacts")).click();
		driver.findElement(By.xpath("//*[@id=\"menu-7\"]/li[1]/a")).click();
		return new ContactsPage(driver);
	}
	
	public NewsFeedsPage newsFeeds() {
		components.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.findElement(By.linkText("News Feeds")).click();
		driver.findElement(By.xpath("//*[@id=\"menu-13\"]/li[1]/a")).click();
		return new NewsFeedsPage(driver);
	}
	
	public CreateSiteModulePage newSiteModule() {
		sideContent.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@id=\"collapse1\"]/li[9]/span/a")).click();
		return new CreateSiteModulePage(driver);
	}
	
	public SiteModulesPage siteModules() {
		sideContent.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@id=\"collapse1\"]/li[9]/a/span")).click();
		return new SiteModulesPage(driver);
	}
	
	public ClientPage clients() {
		components.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.findElement(By.linkText("Banners")).click();
		driver.findElement(By.xpath("//*[@id=\"menu-2\"]/li[3]/a")).click();
		return new ClientPage(driver);
	}
	
	public TagsPage tags() {
		components.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.findElement(By.linkText("Tags")).click();
		return new TagsPage(driver);
	}
	
}
