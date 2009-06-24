package ar.com.nextel.sfa.test;

import ar.com.nextel.sfa.client.debug.DebugConstants;


public class SSCerradasTest extends SfaSeleniumTest {
	
	public void setUp() throws Exception {
		setUp(NB34_ROOT_URL, FIREFOX);
	}
	
		
	public void testVerCambiosSS() throws Exception {
		selenium.setSpeed("500");
		selenium.open(SFA_APP_URL);
		loginIfNeeded();
		waitWhileCargando(10000);
		selenium.click(DebugConstants.GWT_MENU_SS_BUSCAR);
		//selenium.click("gwt-debug-sfa-MenuItem-SS-buscar");
		selenium.click("//td/button");
		selenium.click("//td[1]/table/tbody/tr/td[1]/button");
		selenium.click("holidaySelectable");
		selenium.click("//div[2]/button[1]");
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (selenium.isElementPresent("//div[2]/table/tbody/tr[2]/td[3]")) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

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
