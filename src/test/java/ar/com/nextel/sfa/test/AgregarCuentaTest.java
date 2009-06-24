package ar.com.nextel.sfa.test;
import ar.com.nextel.sfa.client.debug.DebugConstants;

/**
 * Test de la funcionalidad de Agregar cuentas
 * 
 * @author ramiro / rgrippo
 *
 */
public class AgregarCuentaTest extends SfaSeleniumTest {
	
	/**
	 * 
	 */
	public void setUp() throws Exception {
		// Ejecutamos en forma remota
		setUp(NB34_ROOT_URL,FIREFOX);
		
		// Y que me imprima el tiempo de espera por los "cargando"
		SfaSeleniumTest.DEBUG_PRINT_WAIT_TIMES = true;
	}
	
	public void testAgregarCuenta() throws Exception {
		selenium.setSpeed("500");
		selenium.open(SFA_APP_URL);
		
//		for (int i = 0; i < 2; i++) {
			loginIfNeeded();
									
			waitWhileCargando(10000);

			hlClick(DebugConstants.GWT_MENU_CUENTAS);
			hlClick(DebugConstants.GWT_MENU_CUENTAS_AGREGAR);
			waitWhileCargando(10000);
	
			selenium.select(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_COMBO_TIPO_DOC_ID, "label=PAS");
			selenium.type(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_TEXTBOX_NUM_DOC_ID, "14333445");
            
			//prueba cancelar 
//            hlClick(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_BUTTON_CERRAR_ID);
//            hlClick(DebugConstants.GWT_MENU_BOTON_ABRIR);
//            hlClick(DebugConstants.GWT_MENU_CUENTAS);
//            hlClick(DebugConstants.GWT_MENU_CUENTAS_AGREGAR);
//            hlSelect(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_COMBO_TIPO_DOC_ID, "label=DNI");
//            hlType(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_TEXTBOX_NUM_DOC_ID, "14333444");
			
            hlClick(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_BUTTON_ACEPTAR_ID);
   		    waitWhileCargando(10000);
            
            selenium.select("//td/table/tbody/tr[1]/td[2]/select", "label=Débito directo-Cuenta Bancaria");
            selenium.select("//td/table/tbody/tr/td[2]/select", "label=Efectivo");
   		    
   		    hlType("Nombre", "Roberto");
   		    hlType("Apellido", "Putin");
            
   		    //cambio focos para que se ejecute el evento 
            selenium.focus("Nombre");
            selenium.focus("Apellido");
            
            hlSelect("//select[@name='Prov. Ant.']", "label=MOVISTAR");
            hlType("//input[@name='Teléfono/Fax Principal Cod área']", "1111");
            hlType("//input[@name='Teléfono/Fax Principal Número']", "11111111");
            hlType("//input[@name='Teléfono/Fax Principal Interno']", "1111");
            hlType("//input[@name='Teléfono/Fax Adicional Cod área']", "2222");
            hlType("//input[@name='Teléfono/Fax Adicional Número']", "2222222");
            hlType("//input[@name='Teléfono/Fax Adicional Interno']", "2222");
            hlType("//input[@name='Teléfono/Fax Celular Cod área']", "33333");
            hlType("//input[@name='Teléfono/Fax Celular Número']", "33333333");
            hlType("//input[@name='Teléfono/Fax Fax Cod área']", "44444");
            hlType("//input[@name='Teléfono/Fax Fax Número']", "44444444");
            hlType("//input[@name='Teléfono/Fax Fax Interno']", "4444");
            hlType("//input[@name='E-Mail Personal']", "pepe@hotass.com");
            hlType("//input[@name='E-Mail Laboral']", "popo@hotline.com");
            hlType("//td/table/tbody/tr[1]/td[2]/select", "label=Débito directo-Cuenta Bancaria");
            selenium.select("//td/table/tbody/tr[1]/td[2]/select", "label=Débito directo-Cuenta Bancaria");
            hlType("CBU", "1111111111111111111111");
            hlSelect("//tr[1]/td[5]/select", "label=Caja de ahorros en pesos");
            
            //GUARDAR
            //hlClick("link=guardar");
   		    //waitWhileCargando(10000);
            
            
            hlClick("//td[3]/div/div");
            hlClick("//div[1]/button");
            hlType("//tr[3]/td[3]/input", "fda");
            hlType("//div/div[1]/table[1]/tbody/tr[3]/td[5]/input", "432");
            hlType("//table[3]/tbody/tr[2]/td[3]/input", "fffaa");
            hlType("//tr[2]/td[5]/input", "3333");
            hlType("//tr[2]/td[2]/textarea", "reqr");
            hlClick("link=Aceptar");
            waitWhileCargando(10000);
            hlClick(DebugConstants.GWT_DOMICILIO_POPUP_BUTTON_NO_NORMALIZAR_ID);
            hlClick("//td[4]/div/div");
            hlClick("//div[3]/table/tbody/tr[1]/td/button");
            hlType("//div[1]/div/table[1]/tbody/tr[3]/td[2]/input", "rerqerq");
            hlType("//div[1]/div/table[1]/tbody/tr[1]/td[5]/input", "43242342");
            hlType("//div[1]/div/table[1]/tbody/tr[3]/td[5]/input", "ffadfaaf");
            selenium.select("//div[1]/div/table[1]/tbody/tr[4]/td[2]/select", "label=Masculino");
            hlClick("link=Aceptar");
            hlClick("//tr[2]/td/table/tbody/tr[2]/td[1]/div");
            hlClick("//div[1]/table/tbody/tr[1]/td/table/tbody/tr/td[3]/div/div");
            hlClick("//div[1]/table/tbody/tr[2]/td/div/div[2]/div/div/button");
            hlType("//tr[3]/td[3]/input", "dfadfa");
            hlType("//td[2]/div/div/div[1]/table[1]/tbody/tr[3]/td[5]/input", "43242");
            hlType("//table[3]/tbody/tr[2]/td[3]/input", "fdsfa");
            hlType("//tr[2]/td[5]/input", "43242");
            hlType("//td[3]/select", "label=CAPITAL FEDERAL");
            hlClick("//div[4]/a[1]");
            hlClick("link=Aceptar");
            waitWhileCargando(10000);
            hlClick(DebugConstants.GWT_DOMICILIO_POPUP_BUTTON_NO_NORMALIZAR_ID);
            hlClick("link=guardar");
            //hlClick("link=Mostrar detalle");
         //   hlClick("link=cerrar");
   		    
   		    
   		    Thread.sleep(10000);
          
			
	}

	public static void main(String args[] ) {
		AgregarCuentaTest bct = new AgregarCuentaTest();
		try {
			bct.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bct.testAgregarCuenta();
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
		AgregarCuentaTest act = new AgregarCuentaTest();
		try {
			act.setUp();
			act.testAgregarCuenta();
			//act.testBusquedaSinCriterio();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void hlClick (String obj) {
		selenium.highlight(obj);
		selenium.click(obj);
	}
	private void hlType (String obj,String value) {
		selenium.highlight(obj);
		selenium.type(obj,value);
	}
	private void hlSelect (String obj,String value) {
		selenium.highlight(obj);
		selenium.select(obj,value);
	}
	
}
