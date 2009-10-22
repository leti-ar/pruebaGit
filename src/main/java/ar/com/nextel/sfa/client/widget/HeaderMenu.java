package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.command.OpenPageCommand;
import ar.com.nextel.sfa.client.debug.DebugConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Menu principal de la aplicación. Trabaja en conjunto con el UILoader.
 * 
 * @author jlgperez
 * 
 */
public class HeaderMenu extends Composite {

	private FlowPanel mainPanel = new FlowPanel();
	private MenuBar mainMenu = new MenuBar();
	private Button menuButton = new Button();
	private MenuItem cuentasMenuItem;
	private MenuItem cuentasBuscarMenuItem;
	private MenuItem cuentasAgregarMenuItem;
	private MenuItem ssBuscarMenuItem;
	private MenuItem verazMenuItem;
	private MenuItem oppBuscarMenuItem;
	private MenuItem opEnCursoMenuItem;
	public static final int MENU_CUENTA = 1;
	public static final int MENU_CUENTA_BUSCAR = 2;
	public static final int MENU_CUENTA_AGREGAR = 4;
	public static final int MENU_SOLICITUD = 8;
	public static final int MENU_VERAZ = 16;
	public static final int MENU_OPORTUNIDADES = 32;
	public static final int MENU_OP_EN_CURSO = 64;

	public HeaderMenu() {
		initWidget(mainPanel);
		addStyleName("gwt-HeaderMenu");
		mainMenu.addStyleName("menu");

		SimplePanel menuWrapper = new SimplePanel();
		menuWrapper.setWidget(mainMenu);
		menuWrapper.addStyleName("menuWrapper");

		mainPanel.add(new Image("images/toolbar/topbanner_final.gif"));
		mainPanel.add(menuWrapper);
		menuButton.setVisible(false);
		menuButton.addStyleName("btn-menu");
		menuButton.ensureDebugId(DebugConstants.MENU_BOTON_ABRIR);
		RootPanel.get().add(menuButton);
		menuButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				menuButton.setVisible(false);
				setVisible(true);
			}
		});

		initMenu();
	}

	/**
	 * Crea el menú de la aplicación, configurando los submenúes
	 * 
	 */
	private void initMenu() {
		MenuBar menuCuentas = new MenuBar(true);
		menuCuentas.addStyleName("submenuPrincipal");
		menuCuentas.setAnimationEnabled(true);

		// NOTA: si se establecen debug IDs, setear primero los de los Menues y después lo de los submenues.
		// rgm
		cuentasMenuItem = mainMenu.addItem("Cuentas", menuCuentas);
		cuentasMenuItem.ensureDebugId(DebugConstants.MENU_CUENTAS);

		cuentasBuscarMenuItem = new MenuItem("Buscar", new OpenPageCommand(UILoader.BUSCAR_CUENTA));
		cuentasBuscarMenuItem.ensureDebugId(DebugConstants.MENU_CUENTAS_BUSCAR);
		menuCuentas.addItem(cuentasBuscarMenuItem);

		cuentasAgregarMenuItem = new MenuItem("Agregar", new OpenPageCommand(UILoader.AGREGAR_CUENTA));
		cuentasAgregarMenuItem.ensureDebugId(DebugConstants.MENU_CUENTAS_AGREGAR);
		menuCuentas.addItem(cuentasAgregarMenuItem);

		mainMenu.addSeparator();

		ssBuscarMenuItem = new MenuItem("SS", new OpenPageCommand(UILoader.BUSCAR_SOLICITUD));
		ssBuscarMenuItem.ensureDebugId(DebugConstants.MENU_SS_BUSCAR);
		mainMenu.addItem(ssBuscarMenuItem);

		mainMenu.addSeparator();

		verazMenuItem = new MenuItem("Veraz", new OpenPageCommand(UILoader.VERAZ));
		verazMenuItem.ensureDebugId(DebugConstants.MENU_VERAZ_VERAZ);
		mainMenu.addItem(verazMenuItem);

		mainMenu.addSeparator();
		oppBuscarMenuItem = new MenuItem("OPP", new OpenPageCommand(UILoader.BUSCAR_OPP));
		mainMenu.addItem(oppBuscarMenuItem);
		mainMenu.addSeparator();
		opEnCursoMenuItem = new MenuItem("Op. en Curso", new OpenPageCommand(UILoader.OP_EN_CURSO));
		mainMenu.addItem(opEnCursoMenuItem);

	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		menuButton.setVisible(!visible);
	}

	public void enableMenuItems(int items) {
		cuentasMenuItem.setVisible((items & MENU_CUENTA) != 0);
		cuentasBuscarMenuItem.setVisible((items & MENU_CUENTA_BUSCAR) != 0);
		cuentasAgregarMenuItem.setVisible((items & MENU_CUENTA_AGREGAR) != 0);
		ssBuscarMenuItem.setVisible((items & MENU_SOLICITUD) != 0);
		verazMenuItem.setVisible((items & MENU_VERAZ) != 0);
		oppBuscarMenuItem.setVisible((items & MENU_OPORTUNIDADES) != 0);
		opEnCursoMenuItem.setVisible((items & MENU_OP_EN_CURSO) != 0);
	}
}
