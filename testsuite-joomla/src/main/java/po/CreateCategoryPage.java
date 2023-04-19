package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateCategoryPage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement title;
	
	@FindBy(className = "button-save")
	protected WebElement saveBtn;
	
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

}
