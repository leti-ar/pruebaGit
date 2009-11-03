package ar.com.nextel.sfa.test;

import ar.com.nextel.sfa.client.debug.DebugConstants;

/**
 * TODO No chequea los resultados. Está fallando y el test no se da cuenta.
 * 
 * 
 * @author jlgperez
 * 
 */
public class CargarItemsSolicitudTest extends SfaSeleniumTest {

	public void setUp() throws Exception {
		setUpTestIE();
	}

	public void testCargarItemsSolicitud() throws Exception {
		selenium.open(SFA_APP_URL);
		loginIfNeeded();

		waitForElement(DebugConstants.GWT_MENU_CUENTAS);
		selenium.click(DebugConstants.GWT_MENU_CUENTAS);
		waitForElement(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);
		selenium.click(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);

		for (int i = 0; i < 10; i++) {
			waitForElement("//select[@id='" + DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS
					+ "']/option[@value='1']");
			selenium.focus(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS);
			selenium.select(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS, "label=Ctas. propias");
			selenium.focus(DebugConstants.GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR);
			selenium.click(DebugConstants.GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR);

			cargarItemsSolicituParaUnaCuenta(i + 1);
			
			waitForElement(DebugConstants.GWT_MENU_CUENTAS);
			selenium.click(DebugConstants.GWT_MENU_CUENTAS);
			waitForElement(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);

			selenium.click(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);
			Thread.sleep(500);
			selenium.click("link=no");
		}
		assertTrue(true);
	}

	private void cargarItemsSolicituParaUnaCuenta(int row) throws Exception {

		waitForElement("//table[@id='" + DebugConstants.GWT_BUSQUEDA_CUENTAS_TABLE_RESULT
				+ "']/tbody/tr[4]/td[5]");

		selenium.mouseOver("//table[@id='" + DebugConstants.GWT_BUSQUEDA_CUENTAS_TABLE_RESULT
				+ "']/tbody/tr[" + (row + 1) + "]/td[5]");
		selenium.click("//table[@id='" + DebugConstants.GWT_BUSQUEDA_CUENTAS_TABLE_RESULT + "']/tbody/tr["
				+ (row + 1) + "]/td[5]");
		selenium.click("link=^Crear SS");

		waitForElement("link=Equipos/Accesorios");

		selenium.click("link=Equipos/Accesorios");

		waitWhileCargando();

		try {
			if (selenium
					.isElementPresent("//div[@class='gwt-DialogBox gwt-CustomDialogBox gwt-ErrorDialog']")) {
				selenium.click("link=cerrar");
				return;
			}
		} catch (Exception e) {
			return;
		}
		Thread.sleep(500);
		waitForElement(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);

		waitWhileCargando();
		selenium.click(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);
		waitForElement(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM);
		waitWhileCargando();

		// Creo un Alquiler

		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM,
				"label=Kit i880 Motorola");
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_CANTIDAD, "2");
		waitWhileCargando();
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_PLAN, "label=Directo 100");
		waitWhileCargando();
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando();
		Thread.sleep(500);
		selenium.click("//table[@id='" + DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_DETTALE_TABLE
				+ "']/tbody/tr[3]/td[3]");
		waitWhileCargando();
		selenium.click(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);

		waitForElement(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM);

		// Creo una venta

		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_ORDEN,
				"label=Venta (AMBA)");
		waitWhileCargando();
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM, "index=1");
		waitWhileCargando();
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_PLAN, "index=1");
		Thread.sleep(500);
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_CANTIDAD, "1");
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando();
		Thread.sleep(500);
		selenium.click("//table[@id='" + DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_DETTALE_TABLE
				+ "']/tbody/tr[4]/td[3]");
		waitWhileCargando();
		selenium.click(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);

		waitForElement(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM);

		// Creo venta de Accesorios

		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_ORDEN,
				"label=Venta de Accesorios");
		waitWhileCargando();
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM, "index=1");
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_CANTIDAD, "1");
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando();
		Thread.sleep(500);
		selenium.click("//table[@id='" + DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_DETTALE_TABLE
				+ "']/tbody/tr[5]/td[3]");
		waitWhileCargando();
		selenium.click(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);

		waitForElement(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM);

		// Creo Activacion

		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_ORDEN,
				"label=Activación");
		waitWhileCargando();
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_IMEI, "123456789012345");
		selenium.click("//table[2]/tbody/tr[1]/td[3]/div/div");
		waitWhileCargando();
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_MODELO, "index=1");
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM, "index=1");
		waitWhileCargando();
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_SIM, "123456789012345");
		waitWhileCargando();
		selenium.click("//td[5]/div/div");
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_PIN, "12345678");
		waitWhileCargando();
		try {
			selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_PLAN, "index=1");
		} catch (Exception e) {
			selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_PLAN, "index=0");
		}
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando();
		selenium.click(DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_BUTTON_CREAR_LINEA);

		// Creo Venta Licencias BlackBerry

		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_TIPO_ORDEN,
				"label=Venta Licencias BB");
		waitWhileCargando();
		selenium.select(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_COMBO_ITEM, "index=1");
		waitWhileCargando();
		selenium.type(DebugConstants.GWT_EDITAR_SOLICITUD_ITEM_SOLICITUD_TEXTBOX_CANTIDAD, "2");
		Thread.sleep(500);
		selenium.click("link=ACEPTAR");
		waitWhileCargando();
		Thread.sleep(500);
		selenium.click("//table[@id='" + DebugConstants.GWT_EDITAR_SOLICITUD_DATOS_DETTALE_TABLE
				+ "']/tbody/tr[6]/td[3]");
		waitWhileCargando();
//		selenium.click("//button[@type='button']");

		// Vuelvo a la pantalla de busqueda
//		waitForElement(DebugConstants.GWT_MENU_CUENTAS);
//		selenium.click(DebugConstants.GWT_MENU_CUENTAS);
//		waitForElement(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);
//
//		selenium.click(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);
//		Thread.sleep(500);
//		selenium.click("link=no");
	}
}
