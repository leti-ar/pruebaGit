package ar.com.nextel.sfa.test;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class PreProdTest extends SeleneseTestCase {
	
	public void setUp() throws Exception {
		setUp("http://baslijepreprod.nextel.com.ar:7779/", "*iexplore");
		// setUp("http://basliap3.nextel.com.ar:7777/", "*chrome");
		// http://baslijepreprod.nextel.com.ar:7779/sfa/SFAWeb.html
	}

	public void testNew() throws Exception {
		//selenium.open("/uclogin/html/login.html");
		selenium.open("sfa/SFAWeb.html");
		selenium.waitForPageToLoad("30000");
		if ( selenium.isElementPresent("userName")) {
			selenium.type("userName", "acsa1");
			selenium.type("password", "Abc1234");
			selenium.click("//input[@type='image']");
		}
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='menu']/div/div/div/a");
		selenium.click("//div[@class='dropDownStyle']/div/div/div/a");
		//selenium.fireEvent("//div[@id='menu']/div/div/div/a", "click");
		//selenium.click("//img[@alt='Ingresar']");
		
		Thread.sleep(15000);
		selenium
				.select(
						"//div[@id='displayArea']/div[2]/div/div[1]/div/div[1]/div[10]/select",
						"label=Últimas consultadas");
		Thread.sleep(5000);
		for (int i = 0; i < 500; i++) {
			selenium.click("//div[@id='menu']/div/div/div/a");
			selenium.click("//div[@class='dropDownStyle']/div/div/div/a");
			Thread.sleep(10000);
		}
	}
}
