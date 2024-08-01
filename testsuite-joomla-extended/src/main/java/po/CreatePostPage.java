package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreatePostPage extends LoggedNavBar {
	
	@FindBy(id = "jform_title")
	protected WebElement title;
	
	@FindBy(xpath = "//*[@id=\"adminForm\"]/div/button[2]")
	protected WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"jform_title-lbl\"]/span[2]")
	protected WebElement emptyTitleAlert;
	
	
	public CreatePostPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreatePostPage setTitle(String ttl) {
		title.clear();
		title.sendKeys(ttl);
		return this;
	}
	
	public CreatePostPage setBody(String post) {
		driver.switchTo().frame(driver.findElement(By.id("jform_articletext_ifr")));
		driver.findElement(By.id("tinymce")).sendKeys(post);
		driver.switchTo().defaultContent();
		return this;
	}
	
	public LoggedHome save() {
		saveBtn.click();
		return new LoggedHome(driver);
	}
	
	public CreatePostPage saveError() {
		saveBtn.click();
		return new CreatePostPage(driver);
	}
	
	public String getEmptyTitleAlert() {
		return emptyTitleAlert.getText();
	}

}
