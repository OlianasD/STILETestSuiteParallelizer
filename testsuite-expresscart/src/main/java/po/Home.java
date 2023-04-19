package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.JavascriptExecutor;

public class Home extends MainNavBar {
	
	@FindBy(xpath = "//*[@id=\"pager\"]/ul/li[4]/a")
	protected WebElement nextPageLink;
	
	public Home(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public Home addToCart(int i) {
		
		driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div/div["+i+"]/div/p/a")).click();
		return new Home(driver);
	}
	
	public ProductPage goToProduct(int i) {
		driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div/div["+i+"]/div/div/a/h3")).click();
		return new ProductPage(driver);
	}
	
	public Home nextPage() {
		nextPageLink.click();
		return new Home(driver);
	}
	
	public Home goToPage(Integer page) {
		WebElement pgLink = driver.findElement(By.linkText(page.toString()));
		new JavascriptExecutor(driver).scrollTo(pgLink);
		pgLink.click();
		return new Home(driver);
	}
}
