package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateClientPage extends PageObject {
	
	@FindBy(id = "jform_name")
	protected WebElement name;
	
	@FindBy(id = "jform_contact")
	protected WebElement contactName;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"toolbar-cancel\"]/button")
	protected WebElement cancel;

	public CreateClientPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateClientPage setName(String nm) {
		name.sendKeys(nm);
		return this;
	}
	
	public CreateClientPage setContactName(String ct) {
		contactName.sendKeys(ct);
		return this;
	}
	
	public ClientPage save() {
		saveBtn.click();
		return new ClientPage(driver);
	}
	
	public CreateClientPage saveError() {
		saveBtn.click();
		return new CreateClientPage(driver);
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
	
	public ClientPage cancel() {
		cancel.click();
		return new ClientPage(driver);
	}
	
	

}
