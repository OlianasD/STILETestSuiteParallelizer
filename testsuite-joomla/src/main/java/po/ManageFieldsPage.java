package po;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.JavascriptExecutor;

public class ManageFieldsPage extends SiteAdminPageObject {
	
	@FindBy(xpath = "//*[@id=\"toolbar-new\"]/button")
	protected WebElement createFieldBtn;
	
	@FindBy(xpath = "//*[@id=\"toolbar-status-group\"]/button")
	protected WebElement actionsBtn;
	
	public ManageFieldsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public EditFieldPage createField() {
		createFieldBtn.click();
		return new EditFieldPage(driver);
	}
	
	public boolean isFieldPresent(String field) {
		try {
			return driver.findElement(By.id("fieldList")).getText().contains(field);
		} catch(NoSuchElementException e) {
			if(driver.findElement(By.xpath("//*[@id=\"j-main-container\"]/div[2]")).getText().contains("No Matching Results")) {
				return false;
			}
			else {
				throw e;
			}
			
		}
	}
	
	public boolean isFieldPresentAtFirstRow(String field) {
		try {
			return driver.findElement(By.xpath("//*[@id=\"fieldList\"]/tbody/tr[1]/th/div/a")).getText().contains(field);
		} catch(NoSuchElementException e) {
			if(driver.findElement(By.xpath("//*[@id=\"j-main-container\"]/div[2]")).getText().contains("No Matching Results")) {
				return false;
			}
			else {
				throw e;
			}
			
		}
	}
	
	public EditFieldPage goToField(String field) {
		WebElement fLink = driver.findElement(By.linkText(field));
		new JavascriptExecutor(driver).scrollTo(fLink);
		fLink.click();
		return new EditFieldPage(driver);
	}
	
	public boolean fieldHasGroup(int field, String group) {
		return driver.findElement(By.xpath("//*[@id=\"fieldList\"]/tbody/tr["+field+"]/td[5]")).getText().contains(group);
	}
	
	public ManageFieldsPage selectIthField(int i) {
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/section/div/div/main/form/div/div/div/table/tbody/tr["+i+"]/td[1]/input")).click();
		return this;
	}
	
	public ManageFieldsPage deleteSelectedField() {
		actionsBtn.click();
		driver.findElement(By.xpath("//*[@id=\"status-group-children-trash\"]/button")).click();
		return new ManageFieldsPage(driver);
		
	}

}
