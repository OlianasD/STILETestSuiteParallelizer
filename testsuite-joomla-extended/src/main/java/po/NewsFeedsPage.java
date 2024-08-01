package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsFeedsPage extends SiteAdminPageObject {
	
	@FindBy(xpath = "//*[@id=\"toolbar-new\"]/button")
	protected WebElement createNewsFeed;
	
	@FindBy(id = "cb0")
	protected WebElement firstNewsFeedCheckbox;

	public NewsFeedsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateNewsFeedPage createNewsFeed() {
		createNewsFeed.click();
		return new CreateNewsFeedPage(driver);
	}
	
	public String getAlertMessage() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver.findElement(By.className("alert-message")).getText();
	}
	
	public NewsFeedsPage selectNewsFeed() {
		firstNewsFeedCheckbox.click();
		return this;
	}
	
	public NewsFeedsPage deleteSelectedNewsFeed() {
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-trash\"]/button")).click();
		return this;
	}
	
	public CreateNewsFeedPage goToNewsFeed(String feed) {
		driver.findElement(By.linkText(feed)).click();
		return new CreateNewsFeedPage(driver);
	}

}
