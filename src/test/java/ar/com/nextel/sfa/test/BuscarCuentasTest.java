package ar.com.nextel.sfa.test;
import ar.com.nextel.sfa.client.debug.DebugConstants;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

import org.apache.tools.ant.taskdefs.WaitFor;

/**
 * Test de Selenium
 * 
 * Prerequisitos:
 * 
 * 1 ) levantar el server antes!
 * 
 * via mvn, con mvn selenium:start-server
 * 
 * O si lo queremos levantar standalone de linea de comando:
 * "%JAVA_HOME%"\bin\java -cp seleniumrc/selenium-server.jar  org.openqa.selenium.server.SeleniumServer
 * 
 * Por ejemplo:
 * 
 * C:\lib\PushToTest_TestMaker\TestNetwork\TestNode>selenium.cmd
 * 
 * 2) La app debe estar compilada
 * 
 * 
 * Si se ejecuta desde PTT, incluyendo el código como un jar, y si hacemos algún cambio, 
 * recordar recrear el jar.
 * Por ejemplo:
 *
 * 
 * C:\SFA\LocalSFA_v2\sfa_web2\java\sfa-web2\target\classes>c:\jdk1.6.0_10\bin\jar -cvf testsfa.jar .
 * 
 * @author ramiro
 *
 */
public class BuscarCuentasTest extends SeleneseTestCase {
	
	private static final String IEXPLORE = "*iexplore";
	private static final String IEHTA = "*iehta";
	private static final String FIREFOX_CHROME = "*chrome";
	private static final String FIREFOX = "*firefox";
	
	public static String LOCAL_SFA_ROOT_URL = "http://localhost:8888/";
	public static String NB34_SFA_ROOT_URL = "http://nb34:8888/";
	public static String TEST_SFA_ROOT_URL = "http://baslije4.nextel.com.ar:7877/sfa-web2/";
	
	public static String SFA_APP_URL = "ar.com.nextel.sfa.SFAWeb/SFAWeb.html";
	
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

	public void setUp() throws Exception {
		System.out.println("BuscarCuentasTest.setUp");
		setUp(NB34_SFA_ROOT_URL, IEXPLORE);
	}

	public void testBusquedaYRecorrido() throws Exception {
		//selenium.setSpeed("500");
		System.out.println("BuscarCuentasTest.testBusquedaYRecorrido");
		selenium.open(SFA_APP_URL);
		for (int i = 0; i < 3; i++) {
			selenium.click("gwt-uid-1");
			selenium.click("gwt-uid-6");
			while (selenium.isTextPresent("Cargando")){}
	
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
			while (selenium.isTextPresent("Cargando")){}
	/*
			System.out.println(selenium.getTable("gwt-debug-sfa-buscarCuentasResultTable.1.3"));
			System.out.println(selenium.getTable("gwt-debug-sfa-buscarCuentasResultTable.0.3"));
			System.out.println(selenium.getTable("gwt-debug-sfa-buscarCuentasResultTable.2.4"));
			System.out.println(selenium.getTable("gwt-debug-sfa-buscarCuentasResultTable.3.5"));
		*/	
			selenium.highlight(DebugConstants.GWT_TABLE_PAGE_BAR_NEXT);
			selenium.click(DebugConstants.GWT_TABLE_PAGE_BAR_NEXT);
			while (selenium.isTextPresent("Cargando")){}

			selenium.highlight(DebugConstants.GWT_TABLE_PAGE_BAR_NEXT);
			selenium.click(DebugConstants.GWT_TABLE_PAGE_BAR_NEXT);
			while (selenium.isTextPresent("Cargando")){}

			selenium.highlight(DebugConstants.GWT_TABLE_PAGE_BAR_PREV);
			selenium.click(DebugConstants.GWT_TABLE_PAGE_BAR_PREV);
			while (selenium.isTextPresent("Cargando")){}

			selenium.highlight(DebugConstants.GWT_TABLE_PAGE_BAR_PREV);
			selenium.click(DebugConstants.GWT_TABLE_PAGE_BAR_PREV);
			while (selenium.isTextPresent("Cargando")){}
		
			//Thread.sleep(3000);
		}
	}
	
	public void testBusquedaSinCriterio() throws Exception {
		selenium.setSpeed("500");
		selenium.open(SFA_APP_URL);
		selenium.click("gwt-uid-1");
		selenium.click("gwt-uid-6");
		while (selenium.isTextPresent("Cargando")){}

		selenium.click(DebugConstants.GWT_BUSQUEDA_CUENTAS_BOTON_BUSCAR);
		// assertTrue(selenium.isTextPresent("ERROR")); Esto falla cno un null, por qué?
		assertTrue(selenium.isTextPresent("Por favor ingrese por lo menos un criterio de busqueda."));
	}
	
	
	
}
