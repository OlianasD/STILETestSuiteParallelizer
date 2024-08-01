package po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TrackingPage extends SiteAdminPageObject {
	
	@FindBy(xpath = "//*[@id=\"j-main-container\"]/table/tbody/tr/th")
	protected WebElement firstTrackingName;

	public TrackingPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public String getFirstTrackingName() {
		return firstTrackingName.getText();
	}

}
