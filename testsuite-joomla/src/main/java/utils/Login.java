package utils;

import org.openqa.selenium.WebDriver;

import po.BaseNavBar;
import po.ProfilePageInfo;

public class Login {
	public static ProfilePageInfo loginAsAdmin(WebDriver driver) {
		return new BaseNavBar(driver)
			.authorLogin()
			.setUsername("administrator")
			.setPassword("dodicicaratteri")
			.login();
	}
}
