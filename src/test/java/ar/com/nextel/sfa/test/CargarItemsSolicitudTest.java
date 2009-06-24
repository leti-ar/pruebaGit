package ar.com.nextel.sfa.test;

import ar.com.nextel.sfa.client.debug.DebugConstants;

public class CargarItemsSolicitudTest extends SfaSeleniumTest {

	public void setUp() throws Exception {
		setUp(WKSRGM_ROOT_URL, FIREFOX_CHROME);
	}

	/**
	 * 
	 * Este método es usado para correr el test desde PushToTest
	 */
	public void runAllTestsStandalone() {
		CargarItemsSolicitudTest cist = new CargarItemsSolicitudTest();
		try {
			cist.setUp();
			cist.testCargarItemsSolicitud();
			cist.tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				if (selenium.isElementPresent("//select[@id='"
						+ DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS + "']/option[@value='1']"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		selenium.focus(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS);
		selenium.select(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS, "label=Ctas. propias");
		selenium.focus(DebugConstants.GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR);
		selenium.click(DebugConstants.GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR);

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
				if (selenium.isElementPresent("//table[@id='"
						+ DebugConstants.GWT_BUSQUEDA_CUENTAS_TABLE_RESULT + "']/tbody/tr[4]/td[5]"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		selenium.mouseOver("//table[@id='" + DebugConstants.GWT_BUSQUEDA_CUENTAS_TABLE_RESULT
				+ "']/tbody/tr[" + (row + 1) + "]/td[5]");
		selenium.click("//table[@id='" + DebugConstants.GWT_BUSQUEDA_CUENTAS_TABLE_RESULT + "']/tbody/tr["
				+ (row + 1) + "]/td[5]");
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

		selenium.click("link=Equipos/Accesorios");

		waitWhileCargando(5000);

		try {
			if (selenium
					.isElementPresent("//div[@class='gwt-DialogBox gwt-CustomDialogBox gwt-ErrorDialog']")) {
				selenium.click("link=cerrar");
				return;
			}
		} catch (Exception e) {
		}

		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		waitWhileCargando(3000);
		selenium.click(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		waitWhileCargando(3000);

		// Creo un Alquiler

		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM,
				"label=Kit i880 Motorola");
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_CANTIDAD, "2");
		waitWhileCargando(3000);
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando(3000);
		selenium.click("//div[3]/table/tbody/tr[3]/td[3]");
		waitWhileCargando(3000);
		selenium.click(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		// Creo una venta

		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_ORDEN,
				"label=Venta (AMBA)");
		waitWhileCargando(3000);
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM,
				"label=Kit i570 Motorola");
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_CANTIDAD, "1");
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando(3000);
		selenium.click("//div[3]/table/tbody/tr[4]/td[3]");
		waitWhileCargando(3000);
		selenium.click(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		// Creo venta de Accesorios

		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_ORDEN,
				"label=Venta de Accesorios");
		waitWhileCargando(10000);
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM,
				"label=NTN2306 - Tapa i833");
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_CANTIDAD, "1");
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando(3000);
		selenium.click("//div[3]/table/tbody/tr[5]/td[3]");
		waitWhileCargando(3000);
		selenium.click(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (selenium.isElementPresent(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		// Creo Activacion

		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_ORDEN,
				"label=Activación");
		waitWhileCargando(3000);
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_IMEI, "123456789012345");
		selenium.click("//table[2]/tbody/tr[1]/td[3]/div/div");
		waitWhileCargando(3000);
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_MODELO,
				"label=Equipo BB7520 RIM");
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_SIM, "123456789012345");
		waitWhileCargando(3000);
		selenium.click("//td[5]/div/div");
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_PIN, "12345678");
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_PLAN,
				"label=Plan No Vigente");
		waitWhileCargando(3000);
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_PLAN, "label=NEXTEL 400");
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando(3000);
		selenium.click(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);

		// Creo Venta Licencias BlackBerry

		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_ORDEN,
				"label=Venta Licencias BB");
		waitWhileCargando(3000);
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_CANTIDAD, "2");
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
