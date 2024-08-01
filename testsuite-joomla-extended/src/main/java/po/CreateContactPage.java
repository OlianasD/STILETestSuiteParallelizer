package po;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateContactPage extends PageObject {
	
	@FindBy(id = "jform_name")
	protected WebElement name;
	
	@FindBy(xpath = "//*[@id=\"details\"]/div/div[1]/div/div[1]/div[1]/div[2]/joomla-field-user/div[1]/button")
	protected WebElement linkUserBtn;
	
	@FindBy(id = "jform_email_to")
	protected WebElement email;
	
	@FindBy(id = "jform_telephone")
	protected WebElement phone;
	
	@FindBy(id = "jform_mobile")
	protected WebElement mobile;
	
	@FindBy(className = "button-save")
	protected WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"toolbar-cancel\"]/button")
	protected WebElement cancel;

	
	public CreateContactPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateContactPage setName(String nm) {
		name.clear();
		name.sendKeys(nm);
		return this;
	}
	
	public ContactsPage save() {
		saveBtn.click();
		return new ContactsPage(driver);
	}
	
	public CreateContactPage saveError() {
		saveBtn.click();
		return new CreateContactPage(driver);
	}
	
	public String getAlertMessage() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver.findElement(By.className("alert-message")).getText();
	}
	
	public CreateContactPage linkUser(String usr) {
		linkUserBtn.click();
		driver.switchTo().frame(driver.findElement(By.className("iframe")));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("filter_search")).sendKeys(usr);
		driver.findElement(By.id("filter_search")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//*[@id=\"adminForm\"]/table/tbody/tr/th/a")).click();
		driver.switchTo().defaultContent();
		return this;
	}
	
	public String getLinkedUser() {
		return driver.findElement(By.id("jform_user_id")).getAttribute("value");
	}
	
	public CreateContactPage setEmail(String mail) {
		email.sendKeys(mail);
		return this;
	}
	
	public CreateContactPage setPhone(String num) {
		phone.sendKeys(num);
		return this;
	}
	
	public CreateContactPage setMobile(String num) {
		mobile.sendKeys(num);
		return this;
	}
	
	public ContactsPage cancel() {
		cancel.click();
		return new ContactsPage(driver);
	}


}
