package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.command.OpenPageCommand;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
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
		RootPanel.get().add(menuButton);
		menuButton.addClickListener(new ClickListener() {
			public void onClick(Widget button) {
				button.setVisible(false);
				setVisible(true);
			}
		});

		initMenu();
	}

	private void initMenu() {
		MenuBar menuCuentas = new MenuBar(true);
		menuCuentas.addStyleName("submenuPrincipal");
		menuCuentas.setAnimationEnabled(true);

		mainMenu.addItem("Cuentas", menuCuentas);
		mainMenu.addSeparator();
		mainMenu.addItem("SS", new OpenPageCommand(UILoader.SOLICITUD));
		mainMenu.addSeparator();
		mainMenu.addItem("Veraz", new OpenPageCommand(UILoader.VERAZ));
		mainMenu.addSeparator();
		mainMenu.addItem("OPP", new OpenPageCommand(UILoader.BUSCAR_OPP));
		mainMenu.addSeparator();
		mainMenu.addItem("Op. en Curso", new OpenPageCommand(UILoader.OP_EN_CURSO));

		menuCuentas.addItem("Buscar", new OpenPageCommand(UILoader.BUSCAR_CUENTA));
		menuCuentas.addItem("Agregar", new OpenPageCommand(UILoader.AGREGAR_CUENTA));

	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		menuButton.setVisible(!visible);
	}
}
