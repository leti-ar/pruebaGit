package ar.com.nextel.sfa.test;


public class PreProdTest extends SfaSeleniumTest {
	
	public void setUp() throws Exception {
		setUp(BASLIJEPREPROD_ROOT_URL, IEXPLORE);
	}

	public void testNew() throws Exception {

		selenium.open("sfa/SFAWeb.html");
		loginIfNeeded();
		
		selenium.click("//div[@id='menu']/div/div/div/a");
		selenium.click("//div[@class='dropDownStyle']/div/div/div/a");
		
		Thread.sleep(15000);
		selenium
				.select(
						"//div[@id='displayArea']/div[2]/div/div[1]/div/div[1]/div[10]/select",
						"label=Últimas consultadas");
		Thread.sleep(5000);
		for (int i = 0; i < 5; i++) {
			selenium.click("//div[@id='menu']/div/div/div/a");
			selenium.click("//div[@class='dropDownStyle']/div/div/div/a");
			Thread.sleep(10000);
		}
	}
}
