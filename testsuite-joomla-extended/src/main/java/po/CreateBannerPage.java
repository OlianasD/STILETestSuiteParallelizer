package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CreateBannerPage extends PageObject {
	
	@FindBy(id = "jform_name")
	protected WebElement name;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveBtn;
	
	@FindBy(id = "jform_clickurl")
	protected WebElement clickUrl;
	
	@FindBy(xpath = "//*[@id=\"myTab\"]/div/button[2]")
	protected WebElement details;

	public CreateBannerPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateBannerPage setName(String nm) {
		name.sendKeys(nm);
		return this;
	}
	
	public CreateBannerPage setType(String type) {
		new Select(driver.findElement(By.id("jform_type"))).selectByVisibleText(type);
		return this;
	}
	
	public CreateBannerPage setCode(String code) {
		driver.findElement(By.id("jform_custombannercode")).sendKeys(code);
		return this;
	}
	
	public BannerPage save() {
		saveBtn.click();
		return new BannerPage(driver);
	}
	
	public CreateBannerPage setClickUrl(String url) {
		clickUrl.sendKeys(url);
		return this;
	}
	
	public CreateBannerPage details() {
		details.click();
		return this;
	}
	
	public CreateBannerPage selectClient(String client) {
		new Select(driver.findElement(By.id("jform_cid"))).selectByVisibleText(client);
		return this;
	}
	
	public CreateBannerPage trackImpressions() {
		new Select(driver.findElement(By.id("jform_track_impressions"))).selectByVisibleText("Yes");
		return this;
	}
	
	

}
