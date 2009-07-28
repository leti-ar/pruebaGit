package ar.com.nextel.sfa.test;
import java.util.HashMap;
import java.util.Map;

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
		setUpTestIE();
		// Y que me imprima el tiempo de espera por los "cargando"
		SfaSeleniumTest.DEBUG_PRINT_WAIT_TIMES = true;
	}
	
	public void testAgregarCuenta() throws Exception {
		Map <String,String>mapDatos = getMapaDatos();
		
		// Deshabilitado para que en el entorno automatizado ande "a lo que de"
		//selenium.setSpeed("500");
		selenium.open(SFA_APP_URL);

		loginIfNeeded();

		waitWhileCargando();

		waitForElement(DebugConstants.GWT_MENU_CUENTAS);
		hlClick(DebugConstants.GWT_MENU_CUENTAS);
		hlClick(DebugConstants.GWT_MENU_CUENTAS_AGREGAR);


		waitForElement(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_COMBO_TIPO_DOC_ID);
		selenium.select(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_COMBO_TIPO_DOC_ID, mapDatos.get(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_COMBO_TIPO_DOC_ID));
		selenium.type(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_TEXTBOX_NUM_DOC_ID, mapDatos.get(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_TEXTBOX_NUM_DOC_ID));

		//prueba cancelar 
		//            hlClick(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_BUTTON_CERRAR_ID);
		//            hlClick(DebugConstants.GWT_MENU_BOTON_ABRIR);
		//            hlClick(DebugConstants.GWT_MENU_CUENTAS);
		//            hlClick(DebugConstants.GWT_MENU_CUENTAS_AGREGAR);
		//            hlSelect(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_COMBO_TIPO_DOC_ID, "label=DNI");
		//            hlType(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_TEXTBOX_NUM_DOC_ID, "14333444");

		hlClick(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_BUTTON_ACEPTAR_ID);
		waitWhileCargando();

		//pestaña datos
		hlType("Nombre", mapDatos.get("Nombre"));
		hlType("Apellido", mapDatos.get("Apellido"));
		//cambio focos para que se ejecute el evento 
		selenium.focus("Nombre");
		selenium.focus("Apellido");

		hlSelect("//select[@name='Prov. Ant.']"                  , mapDatos.get("ProvAnt"));
		hlType("//input[@name='Teléfono/Fax Principal Cod área']", mapDatos.get("TelefonoFaxPrincipalCodarea"));
		hlType("//input[@name='Teléfono/Fax Principal Número']"  , mapDatos.get("TelefonoFaxPrincipalNumero"));
		hlType("//input[@name='Teléfono/Fax Principal Interno']" , mapDatos.get("TelefonoFaxPrincipalInterno"));
		hlType("//input[@name='Teléfono/Fax Adicional Cod área']", mapDatos.get("TelefonoFaxAdicionalCodarea"));
		hlType("//input[@name='Teléfono/Fax Adicional Número']"  , mapDatos.get("TelefonoFaxAdicionalNumero"));
		hlType("//input[@name='Teléfono/Fax Adicional Interno']" , mapDatos.get("TelefonoFaxAdicionalInterno"));
		hlType("//input[@name='Teléfono/Fax Celular Cod área']"  , mapDatos.get("TelefonoFaxCelularCodarea"));
		hlType("//input[@name='Teléfono/Fax Celular Número']"    , mapDatos.get("TelefonoFaxCelularNumero"));
		hlType("//input[@name='Teléfono/Fax Fax Cod área']"      , mapDatos.get("TelefonoFaxFaxCodarea"));
		hlType("//input[@name='Teléfono/Fax Fax Número']"        , mapDatos.get("TelefonoFaxFaxNumero"));
		hlType("//input[@name='Teléfono/Fax Fax Interno']"       , mapDatos.get("TelefonoFaxFaxInterno"));
		hlType("//input[@name='E-Mail Personal']"                , mapDatos.get("E-MailPersonal"));
		hlType("//input[@name='E-Mail Laboral']"                 , mapDatos.get("E-MailLaboral"));
		selenium.select("//td/table/tbody/tr[1]/td[2]/select"    , mapDatos.get("LabelFormaPago"));
		hlType("CBU"                                             , mapDatos.get("CBU"));
		hlSelect("//tr[1]/td[5]/select"                          , mapDatos.get("LabelTipoCtaBancaria"));

		//GUARDAR
		//hlClick("link=guardar");
		//waitWhileCargando(10000);
		
        //pestaña domicilios
		hlClick("//td[3]/div/div");
		hlClick("//div[1]/button");
		hlType("//tr[3]/td[3]/input"                          , mapDatos.get("DomicilioCalle"));
		hlType("//div/div[1]/table[1]/tbody/tr[3]/td[5]/input", mapDatos.get("DomicilioAltura"));
		hlType("//table[3]/tbody/tr[2]/td[3]/input"           , mapDatos.get("DomicilioLocalidad"));
		hlType("//tr[2]/td[5]/input"                          , mapDatos.get("DomicilioCP"));
		hlType("//tr[2]/td[2]/textarea"                       , mapDatos.get("DomicilioObs"));
		hlClick("link=Aceptar");
		waitWhileCargando();
		hlClick(DebugConstants.GWT_DOMICILIO_POPUP_BUTTON_NO_NORMALIZAR_ID);

		for(int i=1;i<11;i++) {
			//contactos datos
			hlClick("//td[4]/div/div"); //tab contactos
			hlClick("//div[3]/table/tbody/tr[1]/td/button");
			hlType("//div[1]/div/table[1]/tbody/tr[3]/td[2]/input",           mapDatos.get("ContactoNombre")+getIndice(i));
			hlType("//div[1]/div/table[1]/tbody/tr[1]/td[5]/input",           mapDatos.get("ContactoNumDoc")+i);
			hlType("//div[1]/div/table[1]/tbody/tr[3]/td[5]/input",           mapDatos.get("ContactoApellido")+getIndice(i));
			selenium.select("//div[1]/div/table[1]/tbody/tr[4]/td[2]/select", mapDatos.get("ContactoSexo"));

			//contacto telefonos
			selenium.click("//div[1]/table/tbody/tr[1]/td/table/tbody/tr/td[4]/div/div"); //tab contactos telefonos
			selenium.type("//div[3]/div/table/tbody/tr[2]/td[2]/div/input[1]", mapDatos.get("ContactoTelParArea")+i);
			selenium.type("//div[3]/div/table/tbody/tr[2]/td[2]/div/input[2]", mapDatos.get("ContactoTelParNum")+i);
			selenium.type("//tr[2]/td[2]/div/input[3]"                       , mapDatos.get("ContactoTelParInt")+i);
			selenium.type("//div[3]/div/table/tbody/tr[2]/td[4]/div/input[1]", mapDatos.get("ContactoTelAdiArea")+i);
			selenium.type("//div[3]/div/table/tbody/tr[2]/td[4]/div/input[2]", mapDatos.get("ContactoTelAdiNum")+i);
			selenium.type("//div[3]/div/table/tbody/tr[2]/td[4]/div/input[3]", mapDatos.get("ContactoTelAdiInt")+i);
			selenium.type("//tr[3]/td[2]/div/input[1]"                       , mapDatos.get("ContactoTelCelArea")+i);
			selenium.type("//tr[3]/td[2]/div/input[2]"                       , mapDatos.get("ContactoTelCelNum")+i);
			selenium.type("//tr[3]/td[4]/div/input[1]"                       , mapDatos.get("ContactoFaxArea")+i);
			selenium.type("//tr[3]/td[4]/div/input[2]"                       , mapDatos.get("ContactoFaxNum")+i);
			selenium.type("//tr[3]/td[4]/div/input[3]"                       , mapDatos.get("ContactoFaxInt")+i);
			selenium.type("//tr[5]/td[2]/input"                              , mapDatos.get("ContactoEmailPer"));
			selenium.type("//tr[5]/td[4]/input"                              , mapDatos.get("ContactoEmailLab"));
			hlClick("link=Aceptar");

			//contactos domicilioss
			hlClick("//tr[2]/td/table/tbody/tr[2]/td[1]/div");
			hlClick("//div[1]/table/tbody/tr[1]/td/table/tbody/tr/td[3]/div/div");
			hlClick("//div[1]/table/tbody/tr[2]/td/div/div[2]/div/div/button");
			hlType("//tr[3]/td[3]/input"                                    , mapDatos.get("ContactoDomCalle")+getIndice(i));
			hlType("//td[2]/div/div/div[1]/table[1]/tbody/tr[3]/td[5]/input", mapDatos.get("ContactoDomAltura")+i);
			hlType("//table[3]/tbody/tr[2]/td[3]/input"                     , mapDatos.get("ContactoDomLocalidad")+getIndice(i));
			hlType("//tr[2]/td[5]/input"                                    , mapDatos.get("ContactoDomCP")+i);
			hlType("//td[3]/select"                                         , mapDatos.get("ContactoDomProv"));
			hlClick("//div[4]/a[1]");
			hlClick("link=Aceptar");
			waitWhileCargando();
			hlClick(DebugConstants.GWT_DOMICILIO_POPUP_BUTTON_NO_NORMALIZAR_ID);
		}
		//guardar todo
		//hlClick("link=guardar");
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
	
	private String getIndice(int i) {
		Map <Integer,String>indice = null;
		if (indice==null) {
			indice = new HashMap<Integer,String>();
			indice.put(1 ,"-I");
			indice.put(2 ,"-II");
			indice.put(3 ,"-III");
			indice.put(4 ,"-IV");
			indice.put(5 ,"-V");
			indice.put(6 ,"-VI");
			indice.put(7 ,"-VII");
			indice.put(8 ,"-VIII");
			indice.put(9 ,"-IX");
			indice.put(10,"-X");
		}
		return indice.get(i);
	}
	private Map<String,String> getMapaDatos() {
	     Map <String,String>datos = new HashMap<String,String>();
	     datos.put(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_COMBO_TIPO_DOC_ID , "label=DNI");
	     datos.put(DebugConstants.GWT_AGREGAR_CUENTAS_POPUP_TEXTBOX_NUM_DOC_ID, "14333555");
	     
	     datos.put("Nombre"  , "Pepe");
	     datos.put("Apellido", "Putin");

	     datos.put("ProvAnt"                    , "label=MOVISTAR");
	     datos.put("TelefonoFaxPrincipalCodarea", "111");
	     datos.put("TelefonoFaxPrincipalNumero" , "1111111");
	     datos.put("TelefonoFaxPrincipalInterno", "111");
	     datos.put("TelefonoFaxAdicionalCodarea", "222");
	     datos.put("TelefonoFaxAdicionalNumero" , "2222222");
	     datos.put("TelefonoFaxAdicionalInterno", "222");
	     datos.put("TelefonoFaxCelularCodarea"  , "333");
	     datos.put("TelefonoFaxCelularNumero"   , "3333333");
	     datos.put("TelefonoFaxFaxCodarea"      , "444");
	     datos.put("TelefonoFaxFaxNumero"       , "4444444");
	     datos.put("TelefonoFaxFaxInterno"      , "444");
	     datos.put("E-MailPersonal"             , "pepe@hotass.com");
	     datos.put("E-MailLaboral"              , "popo@hotline.com");
	     datos.put("LabelFormaPago"             , "label=Débito directo-Cuenta Bancaria");
	     datos.put("CBU"                        , "1111111111111111111111");
	     datos.put("LabelTipoCtaBancaria"       , "label=Caja de ahorros en pesos");

	     datos.put("DomicilioCalle"      , "calle");
	     datos.put("DomicilioAltura"     , "432");
	     datos.put("DomicilioLocalidad"  , "localidad");
	     datos.put("DomicilioCP"         , "3333");
	     datos.put("DomicilioObs"        , "comentarios");

	     datos.put("ContactoNombre"      , "marta");
	     datos.put("ContactoNumDoc"      , "43242342");
	     datos.put("ContactoApellido"    , "cachucha");
	     datos.put("ContactoSexo"        , "label=Masculino");

	     datos.put("ContactoTelParArea"  , "111");
	     datos.put("ContactoTelParNum"   , "1212121");
	     datos.put("ContactoTelParInt"   , "222");
	     datos.put("ContactoTelAdiArea"  , "333");
	     datos.put("ContactoTelAdiNum"   , "3131313");
	     datos.put("ContactoTelAdiInt"   , "333");
	     datos.put("ContactoTelCelArea"  , "444");
	     datos.put("ContactoTelCelNum"   , "4141414");
	     datos.put("ContactoFaxArea"     , "555");
	     datos.put("ContactoFaxNum"      , "5151515");
	     datos.put("ContactoFaxInt"      , "555");
	     datos.put("ContactoEmailPer"    , "marta@hotline.com");
	     datos.put("ContactoEmailLab"    , "marta@hotass.com");
	     
	     datos.put("ContactoDomCalle"    , "calle cont");
	     datos.put("ContactoDomAltura"   , "43242");
	     datos.put("ContactoDomLocalidad", "localidad cont");
	     datos.put("ContactoDomCP"       , "1111");
	     datos.put("ContactoDomProv"     , "label=CAPITAL FEDERAL");
	     
	     return datos;
	}
	
}
