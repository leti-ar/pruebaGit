package ar.com.nextel.sfa.test;

import ar.com.nextel.sfa.client.debug.DebugConstants;


public class SSCerradasTest extends SfaSeleniumTest {
	
	public void setUp() throws Exception {
		setUp(NB34_ROOT_URL, FIREFOX);
	}
	
	/**
	 * 
	 * Este método es usado para correr el test desde PushToTest
	 */
	public void runAllTestsStandalone() {
		SSCerradasTest ssct = new SSCerradasTest();
		try {
			ssct.setUp();
			ssct.testVerCambiosSS();
			ssct.tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
		
	public void testVerCambiosSS() throws Exception {
		selenium.setSpeed("500");
		selenium.open(SFA_APP_URL);
		loginIfNeeded();
		waitWhileCargando(10000);
		selenium.click(DebugConstants.GWT_MENU_SS_BUSCAR);
		selenium.click("//td/button");
		selenium.click("//td[1]/table/tbody/tr/td[1]/button");
		selenium.click("holidaySelectable");
		selenium.click("//div[2]/button[1]");
		waitForElement(60, "//div[2]/table/tbody/tr[2]/td[3]");
		
		selenium.click("//div[2]/table/tbody/tr[2]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//div[2]/table/tbody/tr[3]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//div[2]/table/tbody/tr[4]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//tr[5]/td[3]");
		waitWhileCargando(3000);selenium.setSpeed("500");
		selenium.click("//tr[6]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//tr[7]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//tr[8]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//div[2]/table/tbody/tr[2]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//div[2]/table/tbody/tr[3]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//div[2]/table/tbody/tr[4]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//tr[5]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//tr[6]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//tr[7]/td[3]");
		waitWhileCargando(3000);
		selenium.click("//tr[8]/td[3]");
	}

	
}
