package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuPage extends PageObject {
	
	@FindBy(xpath = "//*[@id=\"collapse2\"]/li[3]/a/span")
	public WebElement menuItems;
	
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

}
