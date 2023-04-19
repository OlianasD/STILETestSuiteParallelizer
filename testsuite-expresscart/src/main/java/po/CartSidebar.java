package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartSidebar extends PageObject {
	
	@FindBy(xpath = "//*[@id=\"cart\"]/div[1]/div/button")
	protected WebElement closeCartBtn;
	
	public CartSidebar(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public String getIthItem(int i) {
		return driver.findElement(By.xpath("//*[@id=\"cart\"]/div[1]/div/div[1]/div["+i+"]/div/div/div[2]/div/div[1]/h6/a")).getText();
	}
	
	public Home close() {
		closeCartBtn.click();
		return new Home(driver);
	}

}
