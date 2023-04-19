package po;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import utils.JavascriptExecutor;

public class ManageArticlesPage extends SiteAdminPageObject {
	
	@FindBy(xpath = "//*[@id=\"j-main-container\"]/div[1]/div[1]/div/div[2]/button[1]")
	protected WebElement filterBtn;
	
	public ManageArticlesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public ManageArticlesPage deleteFirstArticle() {
		driver.findElement(By.id("cb0")).click();
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-trash\"]/button")).click();
		return this;
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
	
	public boolean containsArticle(String title) {
		try {
			return driver.findElement(By.id("articleList")).getText().contains(title);
		} catch(NoSuchElementException e) {
			if(driver.findElement(By.xpath("//*[@id=\"j-main-container\"]/div[2]")).getText().contains("No Matching Results")) {
				return false;
			}
			else {
				throw e;
			}
			
		}
	}
	
	public EditArticleSiteAdminPage goToArticle(String article) {
		WebElement artLink = driver.findElement(By.linkText(article));
		new JavascriptExecutor(driver).scrollTo(artLink);
		artLink.click();
		return new EditArticleSiteAdminPage(driver);
	}
	
	public String getFirstArticleCategory() {
		return driver.findElement(By.xpath("//*[@id=\"articleList\"]/tbody/tr[1]/th/div/div[2]/a")).getText();
	}
	
	public String getIthArticleCategory(int i) {
		return driver.findElement(By.xpath("//*[@id=\"articleList\"]/tbody/tr["+i+"]/th/div/div[2]/a")).getText();
	}
	
	public ManageArticlesPage archiveSecondArticle() {
		driver.findElement(By.id("cb1")).click();
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-archive\"]/button")).click();
		return this;
	}
	
	public ManageArticlesPage archiveFirstArticle() {
		driver.findElement(By.id("cb0")).click();
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-archive\"]/button")).click();
		return this;
	}
	
	public ManageArticlesPage deleteIthArticle(int i) {
		driver.findElement(By.id("cb"+i)).click();
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-trash\"]/button")).click();
		return this;
	}
	
	public ManageArticlesPage showArchived() {
		filterBtn.click();
		new Select(driver.findElement(By.id("filter_published"))).selectByVisibleText("Archived");
		return new ManageArticlesPage(driver);
	}

}
