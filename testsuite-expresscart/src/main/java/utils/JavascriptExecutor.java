package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavascriptExecutor {

    private org.openqa.selenium.JavascriptExecutor js;

    public JavascriptExecutor(WebDriver driver){
        this.js = (org.openqa.selenium.JavascriptExecutor) driver;
    }

    public void click(WebElement element) {
        this.js.executeScript("arguments[0].click()", element);
    }
    
    public void scrollTo(WebElement element) {
    	this.js.executeScript("arguments[0].scrollIntoView(false);", element);
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public WebElement getParent(WebElement element) {
    	return (WebElement) this.js.executeScript("return arguments[0].parentNode;", element);
    }
}
