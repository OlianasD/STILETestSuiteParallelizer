package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateMenuPage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement title;
	
	@FindBy(id = "jform_menutype")
	protected WebElement uniqueName;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveBtn;
	
	@FindBy(id = "jform_menudescription")
	protected WebElement description;

	public CreateMenuPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateMenuPage setTitle(String ttl) {
		title.sendKeys(ttl);
		return this;
	}
	
	public CreateMenuPage setUniqueName(String name) {
		uniqueName.sendKeys(name);
		return this;
	}
	
	public CreateMenuPage setDescription(String descr) {
		description.sendKeys(descr);
		return this;
	}
	
	public MenuPage save() {
		saveBtn.click();
		return new MenuPage(driver);
	}
	
	public CreateMenuPage saveError() {
		saveBtn.click();
		return new CreateMenuPage(driver);
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
	
	
	
	

}
