package po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ArticlePage extends LoggedNavBar {
	
	@FindBy(xpath= "//*[@id=\"content\"]/div[3]/div[1]/h1")
	protected WebElement title;
	
	@FindBy(xpath = "//*[@id=\"content\"]/div[3]/dl/dd/span[2]")
	protected WebElement author;
	
	@FindBy(xpath = "/html/body/div/div/div/main/div[3]/div[2]/p")
	protected WebElement body;
	
	@FindBy(className = "next")
	public WebElement nextBtn;
	
	@FindBy(xpath = "//*[@id=\"content\"]/div[3]/ul/li/a")
	protected WebElement tagBtn;

	public ArticlePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	public String getTitle() {
		return title.getText();
	}
	
	public String getAuthor() {
		return author.getText();
	}
	
	public String getBody() {
		return driver.findElement(By.className("com-content-article__body")).getText();
	}
	
	public ArticlePage goToNext() {
		nextBtn.click();
		return new ArticlePage(driver);
	}
	
	public String getTag() {
		return tagBtn.getText();
	}
	

}
