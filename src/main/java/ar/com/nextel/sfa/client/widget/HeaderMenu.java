package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.command.OpenPageCommand;
import ar.com.nextel.sfa.client.debug.DebugConstants;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

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

	public HeaderMenu() {
		initWidget(mainPanel);
		addStyleName("gwt-HeaderMenu");
		mainMenu.addStyleName("menu");

		SimplePanel menuWrapper = new SimplePanel();
		menuWrapper.setWidget(mainMenu);
		menuWrapper.addStyleName("menuWrapper");

		mainPanel.add(new Image("images/toolbar/topbanner_final_01.gif"));
		mainPanel.add(menuWrapper);
		menuButton.setVisible(false);
		menuButton.addStyleName("btn-menu");
		menuButton.ensureDebugId(DebugConstants.MENU_BOTON_ABRIR);
		RootPanel.get().add(menuButton);
		menuButton.addClickListener(new ClickListener() {
			public void onClick(Widget button) {
				button.setVisible(false);
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
		
		// NOTA: si se establecen debug IDs, setear primero los de los Menues y después lo de los submenues. rgm
		MenuItem cuentasMenuItem = mainMenu.addItem("Cuentas", menuCuentas);
		cuentasMenuItem.ensureDebugId(DebugConstants.MENU_CUENTAS);

		MenuItem cuentasBuscar = new MenuItem("Buscar", new OpenPageCommand(UILoader.BUSCAR_CUENTA));
		cuentasBuscar.ensureDebugId(DebugConstants.MENU_CUENTAS_BUSCAR);
		menuCuentas.addItem(cuentasBuscar);

		MenuItem cuentasAgregar = new MenuItem("Agregar", new OpenPageCommand(UILoader.AGREGAR_CUENTA));
		cuentasAgregar.ensureDebugId(DebugConstants.MENU_CUENTAS_AGREGAR);
		menuCuentas.addItem(cuentasAgregar);

		
		mainMenu.addSeparator();
		
		MenuItem ssBuscar = new MenuItem("SS", new OpenPageCommand(UILoader.BUSCAR_SOLICITUD));
		ssBuscar.ensureDebugId(DebugConstants.MENU_SS_BUSCAR);
		mainMenu.addItem(ssBuscar);
		
		mainMenu.addSeparator();
		
		MenuItem verazVeraz = new MenuItem("Veraz", new OpenPageCommand(UILoader.VERAZ));
		verazVeraz.ensureDebugId(DebugConstants.MENU_VERAZ_VERAZ);
		mainMenu.addItem(verazVeraz);
		
		mainMenu.addSeparator();
		mainMenu.addItem("OPP", new OpenPageCommand(UILoader.BUSCAR_OPP));
//		mainMenu.addSeparator();
//		mainMenu.addItem("Op. en Curso", new OpenPageCommand(UILoader.OP_EN_CURSO));


	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		menuButton.setVisible(!visible);
	}
}
