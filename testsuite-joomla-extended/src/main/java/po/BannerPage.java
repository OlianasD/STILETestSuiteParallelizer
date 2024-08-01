package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BannerPage extends SiteAdminPageObject {
	
	@FindBy(xpath = "//*[@id=\"toolbar-new\"]/button")
	protected WebElement createBanner;
	
	@FindBy(id = "cb0")
	protected WebElement firstBannerCheckbox;

	public BannerPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateBannerPage createBanner() {
		createBanner.click();
		return new CreateBannerPage(driver);
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
	
	public CreateBannerPage goToBanner(String banner) {
		driver.findElement(By.linkText(banner)).click();
		return new CreateBannerPage(driver);
	}
	
	public BannerPage selectFirstBanner() {
		firstBannerCheckbox.click();
		return this;
	}
	
	public BannerPage deleteSelectedBanner() {
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-trash\"]/button")).click();
		return this;
	}

}
