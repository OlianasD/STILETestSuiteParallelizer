package po;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManageCategoriesPage extends SiteAdminPageObject {
	
	@FindBy(className = "button-new")
	protected WebElement addCategoryBtn;
	
	public ManageCategoriesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public CreateCategoryPage addCategory() {
		addCategoryBtn.click();
		return new CreateCategoryPage(driver);
	}
	
	public boolean containsCategory(String title) {
		return driver.findElement(By.id("categoryList")).getText().contains(title);
	}
	
	public boolean containsCategoryAtThirdRow(String title) {
		try {
			return driver.findElement(By.xpath("//*[@id=\"categoryList\"]/tbody/tr[3]/th/a")).getText().contains(title);
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public ManageCategoriesPage selectThirdCategory() {
		driver.findElement(By.id("cb2")).click();
		return this;
	}
	
	public ManageCategoriesPage deleteSelectedCategory() {
		driver.findElement(By.xpath("//*[@id=\"toolbar-status-group\"]/button")).click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-trash\"]/button")).click();
		return new ManageCategoriesPage(driver);
	}
	
	public String getAlertMessage() {
		return driver.findElement(By.className("alert-message")).getText();
	}

}
