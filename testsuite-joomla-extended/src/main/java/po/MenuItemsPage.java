package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.JavascriptExecutor;

public class MenuItemsPage extends SiteAdminPageObject {
	
	@FindBy(className = "button-new")
	protected WebElement createMenuItem;
	
	@FindBy(id = "cb11")
	protected WebElement lastMenuCheckbox;
	
	public MenuItemsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateMenuItemPage createMenuItem() {
		createMenuItem.click();
		return new CreateMenuItemPage(driver);
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
	
	public boolean containsMenuItem(String title) {
		return driver.findElement(By.id("menuitemList")).getText().contains(title);
	}
	
	public MenuItemsPage selectLastMenuItem() {
		new JavascriptExecutor(driver).scrollTo(lastMenuCheckbox);
		lastMenuCheckbox.click();
		return this;
	}
	
	public MenuItemsPage deleteSelectedMenuItem() {
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-trash\"]/button")).click();
		return this;
	}

}
