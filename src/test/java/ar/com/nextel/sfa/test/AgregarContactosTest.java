package ar.com.nextel.sfa.test;

import ar.com.nextel.sfa.client.debug.DebugConstants;


public class AgregarContactosTest extends SfaSeleniumTest {
	
	public void setUp() throws Exception {
		setUpTestIE();
	}

	public void testAgregarContacto() throws Exception {
		selenium.setSpeed("900");
		selenium.open(SFA_APP_URL);
		loginIfNeeded();
		waitWhileCargando(10000);

		waitForElement(DebugConstants.GWT_MENU_CUENTAS);

		selenium.click(DebugConstants.GWT_MENU_CUENTAS);
		selenium.click(DebugConstants.GWT_MENU_CUENTAS_AGREGAR);
		selenium.type("gwt-debug-sfa-agregarCuentas-PopUp-TextBox-numDocId", "28456987");
		waitForElement("gwt-debug-sfa-agregarCuentas-PopUp-Button-aceptarId");
		selenium.click("gwt-debug-sfa-agregarCuentas-PopUp-Button-aceptarId");
		waitForElement("//td[4]/div/div");

		selenium.click("//td[4]/div/div");
		waitForElement("//div[3]/table/tbody/tr[1]/td/button");

		selenium.click("//div[3]/table/tbody/tr[1]/td/button");
		selenium.click("link=Cerrar");
		selenium.click("//div[3]/table/tbody/tr[1]/td/button");
		selenium.type("//div[1]/div/table[1]/tbody/tr[3]/td[2]/input", "meli");
		selenium.type("//div[1]/div/table[1]/tbody/tr[3]/td[5]/input", "mar");
		selenium.type("//div[1]/div/table[1]/tbody/tr[1]/td[5]/input", "1111");
		selenium.click("//div[1]/table/tbody/tr[1]/td/table/tbody/tr/td[3]/div/div");
		selenium.click("//div[1]/table/tbody/tr[2]/td/div/div[2]/div/div/button");
		selenium.type("//tr[3]/td[3]/input", "calle");
		selenium.type("//td[2]/div/div/div[1]/table[1]/tbody/tr[3]/td[5]/input", "1");
		selenium.type("//table[3]/tbody/tr[2]/td[3]/input", "cap");
		selenium.type("//tr[2]/td[5]/input", "1");
		selenium.click("//div[4]/a[1]");
		selenium.click("gwt-debug-sfa-domicilio-PopUp-Button-noNormId");
		selenium.click("//div[1]/table/tbody/tr[2]/td/div/div[2]/div/div/button");
		selenium.type("//tr[3]/td[3]/input", "calle2");
		selenium.type("//td[2]/div/div/div[1]/table[1]/tbody/tr[3]/td[5]/input", "2");
		selenium.type("//tr[2]/td[5]/input", "2");
		selenium.type("//table[3]/tbody/tr[2]/td[3]/input", "cap");
		selenium.click("//div[4]/a[1]");
		selenium.click("gwt-debug-sfa-domicilio-PopUp-Button-noNormId");
		selenium.click("link=Aceptar");
		selenium.click("//tr[2]/td/table/tbody/tr[2]/td[1]/div");
		selenium.click("link=Cerrar");
		selenium.click("//tr[2]/td/table/tbody/tr[2]/td[1]/div");
		selenium.click("link=Cerrar");
		selenium.click("//tr[2]/td/table/tbody/tr[2]/td[1]/div");
		selenium.click("link=Cerrar");
		selenium.click("//tr[2]/td/table/tbody/tr[2]/td[1]/div");
		selenium.click("link=Cerrar");
	}
}