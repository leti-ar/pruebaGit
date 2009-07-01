package ar.com.nextel.sfa.test;
import ar.com.nextel.sfa.client.debug.DebugConstants;

/**
 * Test de la funcionalidad de búsqueda de cuentas
 * 
 * @author ramiro
 *
 */
public class BuscarCuentasTest extends SfaSeleniumTest {
	
	/**
	 * 
	 */
	public void setUp() throws Exception {
		// Ejecutamos en forma remota
		setUpTestIE();
		
		// Y que me imprima el tiempo de espera por los "cargando"
		SfaSeleniumTest.DEBUG_PRINT_WAIT_TIMES = true;
	}
	
	public void testBusquedaYRecorrido() throws Exception {
		selenium.setSpeed("500");
		selenium.open(SFA_APP_URL);
		
		for (int i = 0; i < 3; i++) {
			loginIfNeeded();
									
			waitWhileCargando(10000);

			selenium.click(DebugConstants.GWT_MENU_CUENTAS);
			selenium.click(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);
			waitWhileCargando(10000);
	
			selenium.highlight(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS);
			selenium.select(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS, "label=Últimas consultadas");
			assertTrue(selenium.getSelectedLabel(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS).equalsIgnoreCase("Últimas consultadas"));
			selenium.fireEvent(DebugConstants.GWT_BUSQUEDA_CUENTAS_COMBO_PREDEFINIDAS, "blur");
			
			selenium.highlight(DebugConstants.GWT_BUSQUEDA_CUENTAS_TEXTBOX_FLOTAID);
			// Tres formas de hacer lo mismo
// solo ie			assertEquals(selenium.getEval("this.browserbot.findElement(\"gwt-debug-sfa-busquedaCuentas-flotaId\").attributes.getNamedItem(\"disabled\").value"),"true");
// solo ie			assertEquals(selenium.getAttribute("gwt-debug-sfa-busquedaCuentas-flotaId@disabled"),"true");
// solo ie			assertFalse(selenium.isEditable("gwt-debug-sfa-busquedaCuentas-flotaId"));
			
			selenium.highlight(DebugConstants.GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR);
			selenium.click(DebugConstants.GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR);
			waitWhileCargando(10000);
	/*
			System.out.println(selenium.getTable("gwt-debug-sfa-buscarCuentasResultTable.1.3"));
			System.out.println(selenium.getTable("gwt-debug-sfa-buscarCuentasResultTable.0.3"));
			System.out.println(selenium.getTable("gwt-debug-sfa-buscarCuentasResultTable.2.4"));
			System.out.println(selenium.getTable("gwt-debug-sfa-buscarCuentasResultTable.3.5"));
		*/	
			selenium.highlight(DebugConstants.GWT_TABLE_PAGE_BAR_NEXT);
			selenium.click(DebugConstants.GWT_TABLE_PAGE_BAR_NEXT);
			waitWhileCargando(10000);

			selenium.highlight(DebugConstants.GWT_TABLE_PAGE_BAR_NEXT);
			selenium.click(DebugConstants.GWT_TABLE_PAGE_BAR_NEXT);
			waitWhileCargando(10000);

			selenium.highlight(DebugConstants.GWT_TABLE_PAGE_BAR_PREV);
			selenium.click(DebugConstants.GWT_TABLE_PAGE_BAR_PREV);
			waitWhileCargando(10000);

			selenium.highlight(DebugConstants.GWT_TABLE_PAGE_BAR_PREV);
			selenium.click(DebugConstants.GWT_TABLE_PAGE_BAR_PREV);
			waitWhileCargando(10000);
		
		}
	}

	public void testBusquedaSinCriterio() throws Exception {
		selenium.setSpeed("500");
		selenium.open(SFA_APP_URL);
		loginIfNeeded();
		waitWhileCargando(10000);

		selenium.click(DebugConstants.GWT_MENU_CUENTAS);
		selenium.click(DebugConstants.GWT_MENU_CUENTAS_BUSCAR);
		waitWhileCargando(10000);

		selenium.click(DebugConstants.GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR);
		// assertTrue(selenium.isTextPresent("ERROR")); Esto falla cno un null, por qué?
		assertTrue(selenium.isTextPresent("Por favor ingrese por lo menos un criterio de busqueda."));
	}
	
	
	
	public static void main(String args[] ) {
		BuscarCuentasTest bct = new BuscarCuentasTest();
		try {
			bct.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bct.testBusquedaYRecorrido();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Este método es usado para correr el test desde PushToTest
	 */
	public void runAllTestsStandalone() {
		BuscarCuentasTest bct = new BuscarCuentasTest();
		try {
			bct.setUp();
			bct.testBusquedaYRecorrido();
			bct.testBusquedaSinCriterio();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
