package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TopNavBar extends PageObject {
	
	@FindBy(xpath = "//*[@id=\"navbarText\"]/ul/li[1]/a")
	protected WebElement accountBtn;
	
	@FindBy(xpath = "//*[@id=\"navbarText\"]/ul/li[2]/a")
	protected WebElement cartBtn;
	
	@FindBy(id = "cart-count")
	protected WebElement cartCount;

	public TopNavBar(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public int getCartCount() {
		return Integer.parseInt(cartCount.getText());
	}
	
	public CartSidebar openCart() {
		cartBtn.click();
		return new CartSidebar(driver);
	}
	
	public CustomerLoginPage account() {
		accountBtn.click();
		return new CustomerLoginPage(driver);
	}

}
