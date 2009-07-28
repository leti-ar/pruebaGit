package ar.com.nextel.sfa.test;

import ar.com.nextel.sfa.client.debug.DebugConstants;

/**
 * 
 * @author meli ( no fedara )
 *
 */
public class SSCerradasTest extends SfaSeleniumTest {
	
	public void setUp() throws Exception {
		setUpTestIE();
	}

	public void testVerCambiosSS() throws Exception {
		// Deshabilitado para que en el entorno automatizado ande "a lo que de"
		//selenium.setSpeed("500");
		selenium.open(SFA_APP_URL);
		loginIfNeeded();
		waitWhileCargando();
		selenium.click(DebugConstants.GWT_MENU_SS_BUSCAR);
		selenium.click("//td/button");
		selenium.click("//td[1]/table/tbody/tr/td[1]/button");
		selenium.click("holidaySelectable");
		selenium.click("//div[2]/button[1]");
		waitForElement("//div[2]/table/tbody/tr[2]/td[3]");
		
		selenium.click("//div[2]/table/tbody/tr[2]/td[3]");
		waitWhileCargando();
		selenium.click("//div[2]/table/tbody/tr[3]/td[3]");
		waitWhileCargando();
		selenium.click("//div[2]/table/tbody/tr[4]/td[3]");
		waitWhileCargando();
		selenium.click("//tr[5]/td[3]");
		waitWhileCargando();selenium.setSpeed("500");
		selenium.click("//tr[6]/td[3]");
		waitWhileCargando();
		selenium.click("//tr[7]/td[3]");
		waitWhileCargando();
		selenium.click("//tr[8]/td[3]");
		waitWhileCargando();
		selenium.click("//div[2]/table/tbody/tr[2]/td[3]");
		waitWhileCargando();
		selenium.click("//div[2]/table/tbody/tr[3]/td[3]");
		waitWhileCargando();
		selenium.click("//div[2]/table/tbody/tr[4]/td[3]");
		waitWhileCargando();
		selenium.click("//tr[5]/td[3]");
		waitWhileCargando();
		selenium.click("//tr[6]/td[3]");
		waitWhileCargando();
		selenium.click("//tr[7]/td[3]");
		waitWhileCargando();
		selenium.click("//tr[8]/td[3]");
	}

	
}
