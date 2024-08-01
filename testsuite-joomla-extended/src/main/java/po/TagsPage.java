package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TagsPage extends SiteAdminPageObject {
	
	@FindBy(xpath = "//*[@id=\"toolbar-new\"]/button")
	protected WebElement createTag;
	
	@FindBy(id = "cb0")
	protected WebElement firstTagCheckbox;

	public TagsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateTagPage createTag() {
		createTag.click();
		return new CreateTagPage(driver);
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
	
	public TagsPage selectTag() {
		firstTagCheckbox.click();
		return this;
	}
	
	public TagsPage deleteSelectedTag() {
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-trash\"]/button")).click();
		return this;
	}
	
	public CreateTagPage goToTag(String feed) {
		driver.findElement(By.linkText(feed)).click();
		return new CreateTagPage(driver);
	}

}
