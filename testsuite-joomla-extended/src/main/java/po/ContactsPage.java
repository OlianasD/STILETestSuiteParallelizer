package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.JavascriptExecutor;

public class ContactsPage extends SiteAdminPageObject {
	
	@FindBy(xpath = "//*[@id=\"toolbar-new\"]/button")
	protected WebElement createContact;
	
	@FindBy(id = "cb0")
	protected WebElement firstContactCheckbox;

	public ContactsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateContactPage createContact() {
		createContact.click();
		return new CreateContactPage(driver);
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
	
	public ContactsPage selectContact() {
		firstContactCheckbox.click();
		return this;
	}
	
	public ContactsPage deleteSelectedContact() {
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-trash\"]/button")).click();
		return this;
	}
	
	public CreateContactPage goToContact(String contact) {
		WebElement ct = driver.findElement(By.linkText(contact));
		new JavascriptExecutor(driver).scrollTo(ct);
		ct.click();
		return new CreateContactPage(driver);
	}

}
