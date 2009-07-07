package ar.com.nextel.sfa.test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

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

	/**
	 * ¿Imprimo lo que espera cada vez que aparece la ventana "Cargando"?
	 */
	public static boolean DEBUG_PRINT_WAIT_TIMES = false;

	/**
	 * Usuario que se loguea a UserCenter
	 */
	public  static final String SFA_USER = "acsa1";

	/**
	 * Password del usuario que se loguea a UserCenter
	 */
	public static final String SFA_PASSWORD = "Abc1234";
	
	private static final String SFA_PAGE_TITLE = "SFA :: Revolution";
	private static final String LOGIN_PAGE_TITLE = "Logueo de Usuarios";
	
	public static final String IEXPLORE = "*iexplore";
	public static final String IEHTA = "*iehta";
	public static final String FIREFOX_CHROME = "*chrome";
	public static final String FIREFOX = "*firefox";

	public static final String BASLIJEPREPROD_ROOT_URL = "http://baslijepreprod.nextel.com.ar:7779/";
	public static final String LOCAL_SFA_ROOT_URL = "http://localhost:8888/";
	public static final String NB34_SFA_ROOT_URL = "http://nb34:8888/";
	public static final String TEST_SFA_ROOT_URL = "http://baslije4.nextel.com.ar:7877/sfa-web2/";
	public static final String WKSRGM_ROOT_URL = "http://wksrgm.nextel.com.ar:8888/";
	public static final String NB34_ROOT_URL = "http://nb34.nextel.com.ar:8888/";
	public static final String D2_ROOT_URL = "http://baslije24.nextel.com.ar:7877/sfa-web2/";
	
	// Default wait timeout de 30 segundos
	public static final long DEFAULT_WAIT_TIMEOUT = 30000;
	
	// Usado por las subclases para entrar al server
	public static final String TEST_ROOT_URL = D2_ROOT_URL;
	
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
	 * 
	 * TODO: la implementación de los 4 parámetros no anduvo
	 */
	public void setUpTestIE() throws Exception {
//		setUp("localhost",4444,TEST_ROOT_URL, IEXPLORE);
		setUp(TEST_ROOT_URL, IEXPLORE);
	}
	
	
	/**
	 * Setea el test para poder ejecutar tests remotos
	 * 
	 * FIXME No funciona porque heredo de SeleneseTestCase
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
	
	/**
	 * Loguea al usuario en UserCenter si el selenium está en la aplicación de login
	 * 
	 * TODO Completar los hooks para otras páginas si es necesario? rgm
	 * TODO Qué pasa si da un timeut? Ver la doc de selenium como "retorna el error"
	 */
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
				selenium.waitForPageToLoad(""+DEFAULT_WAIT_TIMEOUT);
			} else { 				
				if (title.equals(SFA_PAGE_TITLE)) {
					// Página de SFA, agregar acá comportamiento o poner un hook a las subclases
				}
				
			}
		}
	}

	/**
	 * Espera a continuar mientras en la página se muestre el cartel "cargando"
	 * Falla el test si transcurre más de DEFAULT_WAIT_TIMEOUT
	 * 
	 */
	protected void waitWhileCargando() {
		waitWhileCargando(DEFAULT_WAIT_TIMEOUT);
	}
	
	/**
	 * Espera que ocurra un timeout en milisegundos para preguntar si un determinado elemento 
	 * ya está presente en la pantalla
	 * 
	 * @param timeout
	 * @param elemento
	 * 
	 */		
	protected void waitForElement(long timeout, String element) {
		for (int second = 0;; second++) {
			if (second >= timeout) fail("timeout");
			try { 
				if (selenium.isElementPresent(element)) 
					break; 
			} catch (Exception e) {}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Espera mientras en la página se muestre el cartel "Cargando", o que ocurra un timeout en milisegundos,
	 * lo que ocurra primero.
	 * Si ocurre el timeout falla el test
	 * 
	 * @param timeout
	 * @return el tiempo transcurrido en espera ( redondeado a timeut/10 )
	 * 
	 */
	protected long waitWhileCargando(long timeout) {

		long init = System.currentTimeMillis();
		long deadline =  init + timeout;
		boolean timeleft = true;
		boolean cartelPrendido = false;

		// TODO Esta constante "Cargando" sale de gwtcommons?? .rgm
		while ((cartelPrendido = selenium.isTextPresent("Cargando"))
				&& (timeleft = System.currentTimeMillis() < deadline)) {
			try {
				Thread.sleep(timeout / 1000);
			} catch (InterruptedException e) {
				// No me importa
			}
		}
		if (!timeleft && cartelPrendido) {
			fail("Timeout de " + ( timeout / 1000) + " segundos excedido, y sigue el cartel de \"Cargando\"" );
		}
		if ( DEBUG_PRINT_WAIT_TIMES) System.out.println("Waited for: " + (System.currentTimeMillis() - init) + " ms.");
		return System.currentTimeMillis() - init;
	}

	/**
	 * Espera a que aparezca el elemento en la página, o que ocurra un timeout en milisegundos,
	 * lo que ocurra primero.
	 * 
	 * @param timeout
	 * @param element El elemento a buscar
	 * @return el tiempo transcurrido en espera ( redondeado a timeut/10 )
	 * @throws TimeoutExceededException cuando se pasa del timeout y el cartel sigue prendido
	 */
	protected long waitUntilElementPresent(String element, long timeout) {

		long init = System.currentTimeMillis();
		long deadline =  init + timeout;
		boolean timeleft = true;
		boolean elementAusente = false;

		while ((elementAusente = !selenium.isElementPresent(element))
				&& (timeleft = System.currentTimeMillis() < deadline)) {
			try {
				Thread.sleep(timeout / 10);
			} catch (InterruptedException e) {
				// No me importa
			}

		}
		if (!timeleft && elementAusente) {
			fail("Timeout de " + ( timeout / 1000) + " segundos excedido, sin encontrar el elemento " + element );
		}
		if ( DEBUG_PRINT_WAIT_TIMES) System.out.println("Waited for: " + (System.currentTimeMillis() - init) + " ms.");
		return System.currentTimeMillis() - init;
	}


	/**
	 * Espera a que aparezca el elemento en la página, o que ocurra un timeout de DEFAULT_WAIT_TIMEOUT milisegundos,
	 * lo que ocurra primero.
	 * 
	 * @param timeout
	 * @param element El elemento a buscar
	 * @return el tiempo transcurrido en espera ( redondeado a timeut/10 )
	 * @throws TimeoutExceededException cuando se pasa del timeout y el cartel sigue prendido
	 */
	protected long waitUntilElementPresent(String element)
			throws TimeoutExceededException {
		return waitUntilElementPresent(element, DEFAULT_WAIT_TIMEOUT);
	}
	
	/**
	 * Espera a que aparezca el elemento, o que transcurran 60 segundos.
	 * Si ocurre el timeout el test fall
	 * 
	 * Deprecado. usar waitUntilPresent
	 * 
	 * @param element
	 * @throws InterruptedException
	 */
	@Deprecated
	protected void waitForElement(String element) throws InterruptedException {
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (selenium.isElementPresent(element)) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}
	}

}