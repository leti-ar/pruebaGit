package ar.com.nextel.sfa.test;

import ar.com.nextel.sfa.client.debug.DebugConstants;

public class CargarItemsSolicitudTest extends SfaSeleniumTest {

	public void setUp() throws Exception {
		setUp(WKSRGM_ROOT_URL, FIREFOX_CHROME);
	}

	public void testCargarItemsSolicitud() throws Exception {
		selenium.open("/ar.com.nextel.sfa.SFAWeb/SFAWeb.html");
		loginIfNeeded();

		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent(DebugConstants.GWT_MENU_CUENTAS))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		selenium.click(DebugConstants.GWT_MENU_CUENTAS);
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent(DebugConstants.GWT_MENU_CUENTAS_BUSCAR))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		selenium.click(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium
						.isElementPresent("//select[@id='gwt-debug-sfa-busquedaCuentas-Combo-predefinidas']/option[@value='1']"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		selenium.focus("gwt-debug-sfa-busquedaCuentas-Combo-predefinidas");
		selenium.select("gwt-debug-sfa-busquedaCuentas-Combo-predefinidas", "label=Ctas. propias");
		selenium.focus("gwt-debug-sfa-busquedaCuentas-Button-buscar");
		selenium.click("gwt-debug-sfa-busquedaCuentas-Button-buscar");

		for (int i = 0; i < 10; i++) {
			cargarItemsSolicituParaUnaCuenta(i + 1);
		}

		assertTrue(true);
	}

	private void cargarItemsSolicituParaUnaCuenta(int row) throws Exception {

		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium
						.isElementPresent("//table[@id='gwt-debug-sfa-busquedaCuentas-Table-result']/tbody/tr[4]/td[5]"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		selenium.mouseOver("//table[@id='gwt-debug-sfa-busquedaCuentas-Table-result']/tbody/tr[" + (row + 1)
				+ "]/td[5]");
		selenium.click("//table[@id='gwt-debug-sfa-busquedaCuentas-Table-result']/tbody/tr[" + (row + 1)
				+ "]/td[5]");
		selenium.click("link=^Crear SS");
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent("link=Equipos/Accesorios"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent("link=Equipos/Accesorios"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		selenium.click("link=Equipos/Accesorios");

		waitWhileCargando(5000);

		try {
			if (selenium.isElementPresent("//div[@class='gwt-DialogBox gwt-CustomDialogBox gwt-ErrorDialog']")) {
				selenium.click("link=cerrar");
				return;
			}
		} catch (Exception e) {
		}

		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent("gwt-debug-EditarSS-Datos-CrearLinea"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		waitWhileCargando(3000);
		selenium.click("gwt-debug-EditarSS-Datos-CrearLinea");
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent("gwt-debug-EditarSS-ItemSolicitud-Item"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		waitWhileCargando(3000);

		// Creo un Alquiler

		selenium.select("gwt-debug-EditarSS-ItemSolicitud-Item", "label=Kit i880 Motorola");
		selenium.type("gwt-debug-EditarSS-ItemSolicitud-Cantidad", "2");
		waitWhileCargando(3000);
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando(3000);
		selenium.click("//div[3]/table/tbody/tr[3]/td[3]");
		waitWhileCargando(3000);
		selenium.click("gwt-debug-EditarSS-Datos-CrearLinea");
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent("gwt-debug-EditarSS-ItemSolicitud-Item"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		// Creo una venta

		selenium.select("gwt-debug-EditarSS-ItemSolicitud-TipoOrden", "label=Venta (AMBA)");
		waitWhileCargando(3000);
		selenium.select("gwt-debug-EditarSS-ItemSolicitud-Item", "label=Kit i570 Motorola");
		selenium.type("gwt-debug-EditarSS-ItemSolicitud-Cantidad", "1");
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando(3000);
		selenium.click("//div[3]/table/tbody/tr[4]/td[3]");
		waitWhileCargando(3000);
		selenium.click("gwt-debug-EditarSS-Datos-CrearLinea");
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent("gwt-debug-EditarSS-ItemSolicitud-Item"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		// Creo venta de Accesorios

		selenium.select("gwt-debug-EditarSS-ItemSolicitud-TipoOrden", "label=Venta de Accesorios");
		waitWhileCargando(10000);
		selenium.select("gwt-debug-EditarSS-ItemSolicitud-Item", "label=NTN2306 - Tapa i833");
		selenium.type("gwt-debug-EditarSS-ItemSolicitud-Cantidad", "1");
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando(3000);
		selenium.click("//div[3]/table/tbody/tr[5]/td[3]");
		waitWhileCargando(3000);
		selenium.click("gwt-debug-EditarSS-Datos-CrearLinea");
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent("gwt-debug-EditarSS-ItemSolicitud-Item"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		// Creo Activacion

		selenium.select("gwt-debug-EditarSS-ItemSolicitud-TipoOrden", "label=Activación");
		waitWhileCargando(3000);
		selenium.type("gwt-debug-EditarSS-ItemSolicitud-Imei", "123456789012345");
		selenium.click("//table[2]/tbody/tr[1]/td[3]/div/div");
		waitWhileCargando(3000);
		selenium.select("gwt-debug-EditarSS-ItemSolicitud-Modelo", "label=Equipo BB7520 RIM");
		selenium.type("gwt-debug-EditarSS-ItemSolicitud-Sim", "123456789012345");
		waitWhileCargando(3000);
		selenium.click("//td[5]/div/div");
		selenium.type("gwt-debug-EditarSS-ItemSolicitud-Pin", "12345678");
		selenium.select("gwt-debug-EditarSS-ItemSolicitud-TipoPlan", "label=Plan No Vigente");
		waitWhileCargando(3000);
		selenium.select("gwt-debug-EditarSS-ItemSolicitud-Plan", "label=NEXTEL 400");
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando(3000);
		selenium.click("gwt-debug-EditarSS-Datos-CrearLinea");

		// Creo Venta Licencias BlackBerry

		selenium.select("gwt-debug-EditarSS-ItemSolicitud-TipoOrden", "label=Venta Licencias BB");
		waitWhileCargando(3000);
		selenium.type("gwt-debug-EditarSS-ItemSolicitud-Cantidad", "2");
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando(3000);
		selenium.click("//div[3]/table/tbody/tr[6]/td[3]");
		selenium.click("//tr[7]/td[3]");
		selenium.click("//button[@type='button']");

		// Vuelvo a la pantalla de busqueda

		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent(DebugConstants.GWT_MENU_CUENTAS))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		selenium.click(DebugConstants.GWT_MENU_CUENTAS);
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent(DebugConstants.GWT_MENU_CUENTAS_BUSCAR))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		selenium.click(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);
		Thread.sleep(500);
		selenium.click("link=no");
	}

}
