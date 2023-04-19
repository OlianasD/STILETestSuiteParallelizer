package utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class XpathGenerator {
	public String generateXpath(WebElement childElement, String current) {
	    String childTag = childElement.getTagName();
	    if(childTag.equals("html")) {
	        return "/html[1]"+current;
	    }
	    WebElement parentElement = childElement.findElement(By.xpath("..")); 
	    List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
	    int count = 0;
	    for(int i=0;i<childrenElements.size(); i++) {
	        WebElement childrenElement = childrenElements.get(i);
	        String childrenElementTag = childrenElement.getTagName();
	        if(childTag.equals(childrenElementTag)) {
	            count++;
	        }
	        if(childElement.equals(childrenElement)) {
	            return generateXpath(parentElement, "/" + childTag + "[" + count + "]"+current);
	        }
	    }
	    return null;
	}
}
