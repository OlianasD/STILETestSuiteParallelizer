package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddModulePage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement title;
	
	@FindBy(xpath = "//*[@id=\"moduleAddModal\"]/div/div/div[3]/button[2]")
	protected WebElement saveBtn;

	public AddModulePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public AddModulePage setTitle(String ttl) {
		title.sendKeys(ttl);
		return this;
	}
	
	

}
