package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends MainNavBar {
	
	@FindBy(className = "product-add-to-cart")
	protected WebElement addToCartBtn;
	
	public ProductPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public ProductPage addToCart() {
		addToCartBtn.click();
		return new ProductPage(driver);
	}

}
