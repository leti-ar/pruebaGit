package ar.com.nextel.sfa.test;

import ar.com.nextel.sfa.client.debug.DebugConstants;

/**
 * 
 * @author mrial ( no fedara )
 *
 */
public class AgregarContactosTest extends SfaSeleniumTest {
	
	public void setUp() throws Exception {
		setUpTestIE();
	}

	public void testAgregarContacto() throws Exception {
		selenium.setSpeed("500");
		selenium.open(SFA_APP_URL);
		loginIfNeeded();

		waitForElement(DebugConstants.GWT_MENU_CUENTAS);
		selenium.click(DebugConstants.GWT_MENU_CUENTAS);
		selenium.click(DebugConstants.GWT_MENU_CUENTAS_AGREGAR);

		waitForElement("gwt-debug-sfa-agregarCuentas-PopUp-TextBox-numDocId"); // TODO Pasar a constante
		selenium.type("gwt-debug-sfa-agregarCuentas-PopUp-TextBox-numDocId", "28456987");
		waitForElement("gwt-debug-sfa-agregarCuentas-PopUp-Button-aceptarId");
		selenium.click("gwt-debug-sfa-agregarCuentas-PopUp-Button-aceptarId");
		waitForElement("//td[4]/div/div"); // Solapa "contactos"

		selenium.click("//td[4]/div/div");
		waitForElement("//div[3]/table/tbody/tr[1]/td/button"); // Boton Crear nuevo

		selenium.click("//div[3]/table/tbody/tr[1]/td/button"); // Despues de esto, abre la ventana "Crear Contacto". Preguntar por id si la abrio
		selenium.click("link=Cerrar"); // despues de esto, no deberia estar más presente el popup de arriba
		selenium.click("//div[3]/table/tbody/tr[1]/td/button"); // Podemos preguntar de nuevo...
		selenium.type("//div[1]/div/table[1]/tbody/tr[3]/td[2]/input", "meli"); // TODO: id
		selenium.type("//div[1]/div/table[1]/tbody/tr[3]/td[5]/input", "mar"); // TODO: id
		selenium.type("//div[1]/div/table[1]/tbody/tr[1]/td[5]/input", "1111"); // TODO: id
		selenium.click("//div[1]/table/tbody/tr[1]/td/table/tbody/tr/td[3]/div/div"); // TODO: id - solapa domicilios
		selenium.click("//div[1]/table/tbody/tr[2]/td/div/div[2]/div/div/button"); // TODO Cómo le preguto si la solapa esta activa? Si tiene ambas class gwt-TabBarItem gwt-TabBarItem-selected
		selenium.type("//tr[3]/td[3]/input", "calle"); // TODO: id
		selenium.type("//td[2]/div/div/div[1]/table[1]/tbody/tr[3]/td[5]/input", "1"); // TODO: id
		selenium.type("//table[3]/tbody/tr[2]/td[3]/input", "cap"); // TODO: id
		selenium.type("//tr[2]/td[5]/input", "1"); // TODO: id
		selenium.click("//div[4]/a[1]"); // TODO: id - es el boton aceptar
		waitForElement("gwt-debug-sfa-domicilio-PopUp-Button-noNormId"); // Espero por esto porque tiene que aparecer el PopUp de "no pude normalizar"
		selenium.click("gwt-debug-sfa-domicilio-PopUp-Button-noNormId"); // Después de esta linea tiene que aparecer "CALLE 1 cap BUENOS AIRES (1)" cmo primer domicilio
		selenium.click("//div[1]/table/tbody/tr[2]/td/div/div[2]/div/div/button"); // Abajo es similar
		selenium.type("//tr[3]/td[3]/input", "calle2");
		selenium.type("//td[2]/div/div/div[1]/table[1]/tbody/tr[3]/td[5]/input", "2");
		selenium.type("//tr[2]/td[5]/input", "2");
		selenium.type("//table[3]/tbody/tr[2]/td[3]/input", "cap");
		selenium.click("//div[4]/a[1]");
		waitForElement("gwt-debug-sfa-domicilio-PopUp-Button-noNormId");
		selenium.click("gwt-debug-sfa-domicilio-PopUp-Button-noNormId");
		selenium.click("link=Aceptar");  // Despues de esto, verificar que agrego el contacto- buscar el nombre y apellido que le cargamos
		selenium.click("//tr[2]/td/table/tbody/tr[2]/td[1]/div"); // Abre y cierra crear contacto 4 veces
		selenium.click("link=Cerrar");
		selenium.click("//tr[2]/td/table/tbody/tr[2]/td[1]/div");
		selenium.click("link=Cerrar");
		selenium.click("//tr[2]/td/table/tbody/tr[2]/td[1]/div");
		selenium.click("link=Cerrar");
		selenium.click("//tr[2]/td/table/tbody/tr[2]/td[1]/div");
		selenium.click("link=Cerrar"); // Termina acá, no guarda ni cancela. Podriamos clickear en cancelar y despues en aceptar para "limpiar la pantalla"
	}
}