package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.JavascriptExecutor;

public class ManageUsersPage extends SiteAdminPageObject {
	
	@FindBy(className = "button-new")
	protected WebElement addUserBtn;
	
	@FindBy(xpath = "//*[@id=\"toolbar-delete\"]/button")
	protected WebElement deleteBtn;
	
	public ManageUsersPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public AddUserPage addUser() {
		addUserBtn.click();
		return new AddUserPage(driver);
	}
	
	public String getSecondUserRealName() {
		return driver.findElement(By.xpath("//*[@id=\"userList\"]/tbody/tr[2]/th/div[1]/a")).getText();
	}
	
	public String getSecondUserName() {
		return driver.findElement(By.xpath("//*[@id=\"userList\"]/tbody/tr[2]/td[2]")).getText();
	}
	
	public String getSecondUserEmail() {
		return driver.findElement(By.xpath("//*[@id=\"userList\"]/tbody/tr[2]/td[6]")).getText();
	}
	
	public ManageUsersPage selectSecondUser() {
		driver.findElement(By.id("cb1")).click();
		return this;
	}
	
	public ManageUsersPage deleteSelectedUser() {
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-delete\"]/button")).click();
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
		return new ManageUsersPage(driver);
	}
	
	public String getAlertMessage() {
		return driver.findElement(By.className("alert-message")).getText();
	}
	
	public boolean containsUser(String user) {
		return driver.findElement(By.id("userList")).getText().contains(user);
	}
	
	public EditUserPage editUser(String name) {
		WebElement user = driver.findElement(By.linkText(name));
		new JavascriptExecutor(driver).scrollTo(user);
		user.click();
		return new EditUserPage(driver);
	}
	
	public boolean containsGroup(int uid, String group) {
		return driver.findElement(By.xpath("//*[@id=\"userList\"]/tbody/tr["+uid+"]/td[5]")).getText().contains(group);
	}
	
	public String getIthUserRealName(int i) {
		return driver.findElement(By.xpath("//*[@id=\"userList\"]/tbody/tr["+i+"]/th/div[1]/a")).getText();
	}
	
	public String getIthUserName(int i) {
		return driver.findElement(By.xpath("//*[@id=\"userList\"]/tbody/tr["+i+"]/td[2]")).getText();
	}
	
	public String getIthUserEmail(int i) {
		return driver.findElement(By.xpath("//*[@id=\"userList\"]/tbody/tr["+i+"]/td[6]")).getText();
	}
	

}
