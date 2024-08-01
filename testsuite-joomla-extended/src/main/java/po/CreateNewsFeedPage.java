package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateNewsFeedPage extends PageObject {
	
	@FindBy(id = "jform_name")
	protected WebElement name;
	
	@FindBy(id = "jform_link")
	protected WebElement link;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"toolbar-cancel\"]/button")
	protected WebElement cancel;

	public CreateNewsFeedPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateNewsFeedPage setTitle(String nm) {
		name.sendKeys(nm);
		return this;
	}
	
	public CreateNewsFeedPage setLink(String lnk) {
		link.sendKeys(lnk);
		return this;
	}
	
	public NewsFeedsPage save() {
		saveBtn.click();
		return new NewsFeedsPage(driver);
	}
	
	public CreateNewsFeedPage saveError() {
		saveBtn.click();
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
	
	public NewsFeedsPage cancel() {
		cancel.click();
		return new NewsFeedsPage(driver);
	}
	

}
