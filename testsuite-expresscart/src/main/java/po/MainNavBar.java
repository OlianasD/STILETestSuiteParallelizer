package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainNavBar extends TopNavBar {
	
	@FindBy(xpath = "//*[@id=\"navbarMenu\"]/ul/li[1]/a")
	protected WebElement homeBtn;
	
	public MainNavBar(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public Home goHome() {
		homeBtn.click();
		return new Home(driver);
	}

}
