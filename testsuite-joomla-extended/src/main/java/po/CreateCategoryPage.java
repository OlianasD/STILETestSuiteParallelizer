package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateCategoryPage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement title;
	
	@FindBy(className = "button-save")
	protected WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"jform_title-lbl\"]/span[2]")
	protected WebElement emptyTitleAlert;
	
	@FindBy(xpath = "//*[@id=\"toolbar-cancel\"]/button")
	protected WebElement cancel;
	
	public CreateCategoryPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateCategoryPage setTitle(String ttl) {
		title.clear();
		title.sendKeys(ttl);
		return this;
	}
	
	public ManageCategoriesPage save() {
		saveBtn.click();
		return new ManageCategoriesPage(driver);
	}
	
	public CreateCategoryPage saveError() {
		saveBtn.click();
		return new CreateCategoryPage(driver);
	}
	
	public String getEmptyTitleAlert() {
		return emptyTitleAlert.getText();
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
	
	public ManageCategoriesPage cancel() {
		cancel.click();
		return new ManageCategoriesPage(driver);
	}

}
