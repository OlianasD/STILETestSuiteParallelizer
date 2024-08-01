package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuPage extends PageObject {
	
	@FindBy(xpath = "//*[@id=\"collapse2\"]/li[3]/a/span")
	public WebElement menuItems;
	
	@FindBy(xpath = "//*[@id=\"toolbar-new\"]/button")
	public WebElement createMenu;
	
	@FindBy(xpath = "//*[@id=\"menuList\"]/tbody/tr[4]/td[6]/button")
	protected WebElement linkModuleLastMenu;
	
	@FindBy(xpath = "//*[@id=\"menuList\"]/tbody/tr[4]/td[6]/div[1]/button")
	protected WebElement modulesLastMenu;
	
	@FindBy(id = "cb3")
	protected WebElement lastMenuCheckbox;
	
	@FindBy(xpath = "//*[@id=\"toolbar-delete\"]/button")
	protected WebElement deleteBtn;
	
	public MenuPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public MenuItemsPage menuItems() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menuItems.click();
		return new MenuItemsPage(driver);
	}
	
	public CreateMenuPage createMenu() {
		createMenu.click();
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
	
	public AddModulePage linkModuleLastMenu() {
		linkModuleLastMenu.click();
		return new AddModulePage(driver);
	}
	
	public boolean doesLastMenuHaveModule() {
		return modulesLastMenu.getText().equals("Modules");
	}
	
	public CreateMenuPage goToLastMenu() {
		driver.findElement(By.xpath("//*[@id=\"menuList\"]/tbody/tr[4]/th/div/a")).click();
		return new CreateMenuPage(driver);
	}
	
	public MenuPage selectLastMenu() {
		lastMenuCheckbox.click();
		return this;
	}
	
	public MenuPage deleteSelectedMenu() {
		deleteBtn.click();
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
		return this;
	}

}
