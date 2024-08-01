package po;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateBannerModulePage extends PageObject {
	
	@FindBy(id = "jform_title")
	protected WebElement title;
	
	@FindBy(xpath = "//*[@id=\"save-group-children-save\"]/button")
	protected WebElement saveBtn;
	
	public CreateBannerModulePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateBannerModulePage setTitle(String ttl) {
		title.sendKeys(ttl);
		return this;
	}
	
	public CreateBannerModulePage setPosition(String pos) {
		driver.findElement(By.xpath("//*[@id=\"general\"]/div/div[2]/fieldset/div[2]/div[2]/joomla-field-fancy-select/div/div[1]")).click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@id=\"general\"]/div/div[2]/fieldset/div[2]/div[2]/joomla-field-fancy-select/div/div[2]/input")).sendKeys(pos);
		driver.findElement(By.xpath("//*[@id=\"general\"]/div/div[2]/fieldset/div[2]/div[2]/joomla-field-fancy-select/div/div[2]/input")).sendKeys(Keys.ENTER);
		return this;
	}
	
	public SiteModulesPage save() {
		saveBtn.click();
		return new SiteModulesPage(driver);
	}

}
