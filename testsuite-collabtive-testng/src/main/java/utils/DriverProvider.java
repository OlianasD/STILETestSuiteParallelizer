package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverProvider {

	private static DriverProvider ourInstance = new DriverProvider();

	private DriverProvider() {
		Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
		/*System.setProperty("webdriver.chrome.driver", "/home/anonymous/Downloads/chromedriver");
    	//WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.silentOutput", "true");
        Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);*/
	}

	public WebDriver getDriver() {

		WebDriverManager.firefoxdriver().setup();

		FirefoxProfile profile = new FirefoxProfile();

		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability("marionette", true);
		firefoxOptions.setProfile(profile);

		
		firefoxOptions.setHeadless(true);

		return new FirefoxDriver(firefoxOptions);
	/*	ChromeOptions chromeOptions = new ChromeOptions();
        //if(Boolean.valueOf(Properties.getInstance().getProperty("headless_browser"))){
            chromeOptions.addArguments("--no-sandbox", "--headless", "--disable-gpu", "--window-size=1200x600");
       // }
        return new ChromeDriver(chromeOptions);*/
	}

	public static DriverProvider getInstance() {
		return ourInstance;
	}
}
