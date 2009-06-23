package ar.com.nextel.sfa.test;

import junit.framework.TestCase;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

/**
 * Test de Selenium
 * 
 * Define métodos comunes y constantes para los unit tests Selenium de SFA
 * 
 * Prerequisitos:
 * 
 * 1 ) Levantar el Selenium RC
 * 
 * via mvn, con mvn selenium:start-server
 * 
 * O, si lo queremos levantar standalone de linea de comando:
 * "%JAVA_HOME%"\bin\java -cp seleniumrc/selenium-server.jar  org.openqa.selenium.server.SeleniumServer
 * 
 * Por ejemplo:
 * 
 * C:\lib\PushToTest_TestMaker\TestNetwork\TestNode>selenium.cmd
 * 
 * 2) Si se ejecuta desde PTT, recordar que el código va en un jar
 * 
 * Si hacemos algún cambio, recordar recrear el jar con el que lo referenciamos
 * en el script de TestMaker. Por ejemplo:
 * 
 * C:\SFA\LocalSFA_v2\sfa_web2\java\sfa-web2\target\classes>jar -cvf testsfa.jar .
 * 
 * 
 * @author ramiro
 *
 */
public abstract class SfaSeleniumTest extends SeleneseTestCase {

	private static final String SFA_PAGE_TITLE = "SFA :: Revolution";
	private static final String LOGIN_PAGE_TITLE = "Logueo de Usuarios";
	private static final String SFA_PASSWORD = "Abc1234";
	private static final String SFA_USER = "acsa1";
	
	public static final String IEXPLORE = "*iexplore";
	public static final String IEHTA = "*iehta";
	public static final String FIREFOX_CHROME = "*chrome";
	public static final String FIREFOX = "*firefox";

	public static final String BASLIJEPREPROD_ROOT_URL = "http://baslijepreprod.nextel.com.ar:7779/";
	public static final String LOCAL_SFA_ROOT_URL = "http://localhost:8888/";
	public static final String NB34_SFA_ROOT_URL = "http://nb34:8888/";
	public static final String TEST_SFA_ROOT_URL = "http://baslije4.nextel.com.ar:7877/sfa-web2/";
	public static final String WKSRGM_ROOT_URL = "http://wksrgm.nextel.com.ar:8888/";
	
	public static final String SFA_APP_URL = "ar.com.nextel.sfa.SFAWeb/SFAWeb.html";

	
	public SfaSeleniumTest() {
		super();
	}

	public SfaSeleniumTest(String name) {
		super(name);
	}

	/**
	 * Configura para ejecutar los tests contra un server local contra el ambiente de test con IE. Si es necesario cambiarlo,
	 * usar alguna variante del setUp() en las subclases
	 */
	public void setUpTestIE() throws Exception {
		setUp("localhost",4444,TEST_SFA_ROOT_URL, IEXPLORE);
	}
	
	
	/**
	 * Setea el test para poder ejecutar tests remotos
	 * 
	 * TODO No funciona porque heredo de SeleneseTestCase
	 * 
	 * @param serverHost
	 * @param serverPort
	 * @param browserStartCommand
	 * @param browserURL
	 * @throws Exception
	 */
	protected void setUp(String serverHost, int serverPort, String browserStartCommand, String browserURL  ) throws Exception {
		selenium = new DefaultSelenium(serverHost, serverPort, browserStartCommand, browserURL);
	}
	

	protected void loginIfNeeded() {
		String[] windowTitles = selenium.getAllWindowTitles();
		if (windowTitles.length == 0 ) {
			fail("No levantó ninguna página conocida");
		} else {
			String title = windowTitles[0];
			if (title.equals(LOGIN_PAGE_TITLE)) {
				selenium.type("userName", SFA_USER);
				selenium.type("password", SFA_PASSWORD);
				selenium.click("//input[@type='image']");
				selenium.waitForPageToLoad("15000");
			} else { 				
				if (title.equals(SFA_PAGE_TITLE)) {
					// Página de SFA, agregar acá comportamiento o poner un hook a las subclases
				}
				
			}
		}
	}

}