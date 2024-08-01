package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateSiteModulePage extends PageObject {
	
	@FindBy(xpath = "//*[@id=\"comModulesSelectResultsContainer\"]/a[8]/span")
	protected WebElement createBannerModuleBtn;

	public CreateSiteModulePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateBannerModulePage createBannerModule() {
		createBannerModuleBtn.click();
		return new CreateBannerModulePage(driver);
	}

}
