package ar.com.nextel.sfa.test;

import ar.com.nextel.sfa.client.debug.DebugConstants;


public class DomicilioUITestCase extends SfaSeleniumTest {
	public void setUp() throws Exception {
		setUpTestIE();
	}
	public void testDomicilioUITestCase() throws Exception {
		selenium.setSpeed("500");
		selenium.open(SFA_APP_URL);

		loginIfNeeded();

		waitUntilElementPresent(DebugConstants.GWT_MENU_CUENTAS);
		
		selenium.click(DebugConstants.GWT_MENU_CUENTAS);
		selenium.click(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);

		waitUntilElementPresent(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS);
		selenium.focus(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS);
		selenium.select(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS, "label=Ctas. propias");
		selenium.fireEvent(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS, "blur");

		selenium.focus(DebugConstants.GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR);
		selenium.click(DebugConstants.GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR);

		waitWhileCargando();

		waitUntilElementPresent(DebugConstants.GWT_BUSQUEDA_CUENTAS_TABLE_RESULT);

		selenium.click("//table[@id='" + DebugConstants.GWT_BUSQUEDA_CUENTAS_TABLE_RESULT + "']/tbody/tr[5]/td[1]/div");

		waitUntilElementPresent("//input[@name='Teléfono/Fax Celular Cod área']");
	
		selenium.click("//td[3]/div/div");
		selenium.click("//div[1]/button");
		selenium.type("//td[3]/input", "C1023AAS");
		selenium.type("//tr[3]/td[3]/input", "AV CALLAO");
		selenium.type("//div/div[1]/table[1]/tbody/tr[3]/td[5]/input", "1234");
		selenium.focus("//div/div[1]/table[1]/tbody/tr[3]/td[5]/input");
		selenium.type("//table[3]/tbody/tr[2]/td[3]/input", "CIUDAD AUTONOMA BUENOS AIRES");
		selenium.type("//tr[2]/td[5]/input", "1023");
		selenium.select("//tr[4]/td[3]/select", "label=Si");
		selenium.select("//tr[4]/td[5]/select", "label=No");
		selenium.type("//tr[2]/td[2]/textarea", "test1");
		selenium.click("gwt-uid-6");
		selenium.click("link=Aceptar");
		
		waitUntilElementPresent("//table[@id='"+ DebugConstants.GWT_TABLE_DOMICILIO_NORMALIZADO + "']/tbody/tr[2]/td");
		
		selenium.click("link=No Normalizar");
		selenium.click("//div[2]/div/table/tbody/tr[2]/td[1]/div");
		selenium.select("//tr[4]/td[3]/select", "label=Principal");
		selenium.select("//tr[4]/td[5]/select", "label=Principal");
		selenium.click("link=Aceptar");
		
		waitUntilElementPresent("//table[@id='" + DebugConstants.GWT_TABLE_DOMICILIO_NORMALIZADO + "']/tbody/tr[2]/td");
		
		selenium.click("link=No Normalizar");
		selenium.click("//div[2]/div/table/tbody/tr[2]/td[2]/div");
		selenium.select("//tr[4]/td[3]/select", "label=Principal");
		selenium.type("//tr[2]/td[2]/textarea", "test2");
		selenium.type("//div/div[1]/table[1]/tbody/tr[3]/td[5]/input", "1235");
		selenium.click("link=Aceptar");
		
		waitUntilElementPresent("//table[@id='" + DebugConstants.GWT_TABLE_DOMICILIO_NORMALIZADO + "']/tbody/tr[2]/td");
		
		selenium.click("link=No Normalizar");
		selenium.click("link=aceptar");
		selenium.click("//div[4]/a[3]");
		selenium.select("//tr[4]/td[5]/select", "label=Principal");
		selenium.select("//tr[4]/td[3]/select", "label=No");
		selenium.type("//tr[2]/td[2]/textarea", "test3");
		selenium.click("link=Aceptar");
		
		waitUntilElementPresent("//table[@id='" + DebugConstants.GWT_TABLE_DOMICILIO_NORMALIZADO + "']/tbody/tr[2]/td");
		
		selenium.click("link=No Normalizar");
		selenium.click("link=aceptar");
		selenium.click("//div[4]/a[3]");
		selenium.select("//tr[4]/td[5]/select", "label=Si");
		selenium.click("link=Aceptar");
		
		waitUntilElementPresent("//table[@id='" + DebugConstants.GWT_TABLE_DOMICILIO_NORMALIZADO + "']/tbody/tr[2]/td");
		
		selenium.click("link=No Normalizar");
		selenium.click("//div[2]/div/table/tbody/tr[2]/td[1]/div");
		selenium.select("//tr[4]/td[5]/select", "label=Si");
		selenium.type("//tr[2]/td[2]/textarea", "test4");
		selenium.click("link=Aceptar");
		
		waitUntilElementPresent("//table[@id='" + DebugConstants.GWT_TABLE_DOMICILIO_NORMALIZADO + "']/tbody/tr[2]/td");

		selenium.click("link=No Normalizar");
		selenium.click("//tr[3]/td[2]/div");
		selenium.select("//tr[4]/td[5]/select", "label=Principal");
		selenium.select("//tr[4]/td[3]/select", "label=Si");
		selenium.type("//tr[2]/td[2]/textarea", "test5");
		selenium.type("//div/div[1]/table[1]/tbody/tr[3]/td[5]/input", "1230");
		selenium.click("gwt-uid-6");
		selenium.click("link=Aceptar");
		
		waitUntilElementPresent("//table[@id='" + DebugConstants.GWT_TABLE_DOMICILIO_NORMALIZADO + "']/tbody/tr[2]/td");
		
		selenium.click("link=No Normalizar");
		selenium.click("//tr[4]/td[2]/div");
		selenium.type("//tr[2]/td[2]/textarea", "test6");
		selenium.click("link=Aceptar");
		
		waitUntilElementPresent("//table[@id='" + DebugConstants.GWT_TABLE_DOMICILIO_NORMALIZADO + "']/tbody/tr[2]/td");
		
		selenium.click("link=No Normalizar");
		selenium.click("//div[2]/div/table/tbody/tr[5]/td[1]/div");
		selenium.select("//tr[4]/td[3]/select", "label=Principal");
		selenium.type("//tr[2]/td[2]/textarea", "test7");
		selenium.click("link=Aceptar");
		
		waitUntilElementPresent("//table[@id='" + DebugConstants.GWT_TABLE_DOMICILIO_NORMALIZADO + "']/tbody/tr[2]/td");

		selenium.click("link=No Normalizar");
		selenium.click("link=aceptar");
		selenium.click("//div[4]/a[3]");
		selenium.select("//tr[4]/td[3]/select", "label=No");
		selenium.select("//tr[4]/td[5]/select", "label=No");
		selenium.click("link=Aceptar");
		waitUntilElementPresent("//table[@id='" + DebugConstants.GWT_TABLE_DOMICILIO_NORMALIZADO + "']/tbody/tr[2]/td");


		selenium.click("link=No Normalizar");
		selenium.click("//tr[5]/td[3]/div");
		selenium.click("link=no");
		selenium.click("//tr[5]/td[3]/div");
		selenium.click("link=si");
		selenium.click("link=guardar");

		waitUntilElementPresent("link=aceptar");
		
		selenium.click("link=aceptar");
		selenium.click("//tr[4]/td[3]/div");
		selenium.click("link=si");
		selenium.click("//tr[3]/td[3]/div");
		selenium.click("link=si");
		selenium.click("//tr[2]/td[3]/div");
		selenium.click("link=si");
		selenium.click("link=guardar");
		
		waitUntilElementPresent("link=aceptar");

		selenium.click("link=aceptar");
	}
}
