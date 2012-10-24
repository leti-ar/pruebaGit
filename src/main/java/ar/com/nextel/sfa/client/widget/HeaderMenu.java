package ar.com.nextel.sfa.client.widget;


import java.util.List;

import ar.com.nextel.sfa.client.command.OpenPageCommand;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.debug.DebugConstants;
import ar.com.nextel.sfa.client.dto.GrupoSolicitudDto;
import ar.com.nextel.sfa.client.ss.EditarSSUI;

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
	//MGR - Integracion
	private MenuItem agregarProspectMenuItem;
	private MenuItem menuItemCrearSS;
	private MenuItem sucursalMenuItem;
	private MenuItem ssBuscarMenuItem;
	private MenuItem verazMenuItem;
	private MenuItem oppBuscarMenuItem;
	private MenuItem opEnCursoMenuItem;
	public static final int MENU_CUENTA = 1;
	public static final int MENU_CUENTA_BUSCAR = 2;
	public static final int MENU_CUENTA_AGREGAR = 4;
	//MGR - Integracion
	public static final int MENU_PROSPECT = 8;
	public static final int MENU_CREAR_SS = 16;
	
	public static final int MENU_SOLICITUD = 32;//8;
	public static final int MENU_VERAZ = 64;//16;
	public static final int MENU_OPORTUNIDADES = 128;//32;
	public static final int MENU_OP_EN_CURSO = 256;//64;
    public static final int MENU_CAMBIO_SUCURSAL= 512;
	public HeaderMenu() {
		initWidget(mainPanel);
		addStyleName("gwt-HeaderMenu");
		mainMenu.addStyleName("menu");

		SimplePanel menuWrapper = new SimplePanel();
		menuWrapper.setWidget(mainMenu);
		menuWrapper.addStyleName("menuWrapper");

		//MGR - Integracion
		if(!ClientContext.getInstance().vengoDeNexus()){
			mainPanel.add(new Image("images/toolbar/topbanner_final.gif"));
		}
		
		
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
		
		//MGR - #1446
		if(ClientContext.getInstance().vengoDeNexus() && ClientContext.getInstance().soyClienteNexus()){
			cuentasMenuItem = mainMenu.addItem("Cuenta", menuCuentas);
		}else{
			cuentasMenuItem = mainMenu.addItem("Cuentas", menuCuentas);
		}
		cuentasMenuItem.ensureDebugId(DebugConstants.MENU_CUENTAS);

		
		//MGR - #955
		//Si ya se realizo las busqueda de un cliente, creo un link en el boton, sino funciona solo
		//como opcion de menu
		if(ClientContext.getInstance().vengoDeNexus() && ClientContext.getInstance().soyClienteNexus()){
			cuentasMenuItem.setCommand(new OpenPageCommand(UILoader.EDITAR_CUENTA));
		}
		
		
		cuentasBuscarMenuItem = new MenuItem("Buscar", new OpenPageCommand(UILoader.BUSCAR_CUENTA));
		cuentasBuscarMenuItem.ensureDebugId(DebugConstants.MENU_CUENTAS_BUSCAR);
		menuCuentas.addItem(cuentasBuscarMenuItem);

		cuentasAgregarMenuItem = new MenuItem("Agregar", new OpenPageCommand(UILoader.AGREGAR_CUENTA));
		cuentasAgregarMenuItem.ensureDebugId(DebugConstants.MENU_CUENTAS_AGREGAR);
		menuCuentas.addItem(cuentasAgregarMenuItem);
		
		mainMenu.addSeparator();
		
		//MGR - Integracion
		agregarProspectMenuItem = new MenuItem("Agregar Prospect", new OpenPageCommand(UILoader.AGREGAR_PROSPECT));
		agregarProspectMenuItem.ensureDebugId(DebugConstants.MENU_AGREGAR_PROSPECT);
		mainMenu.addItem(agregarProspectMenuItem);
		mainMenu.addSeparator();

		//MGR - Integracion
		MenuBar menuBarCrearSS = new MenuBar(true);
		menuBarCrearSS.addStyleName("submenuPrincipal");
		menuBarCrearSS.setAnimationEnabled(true);
		
		menuItemCrearSS = mainMenu.addItem("Crear SS", menuBarCrearSS);
		menuItemCrearSS.ensureDebugId(DebugConstants.MENU_CREAR_SS);
		crearMenuSS(menuBarCrearSS);
		mainMenu.addSeparator();

		// LF#3
		ssBuscarMenuItem = new MenuItem("SS", new OpenPageCommand(UILoader.BUSCAR_SOLICITUD));
//		ssBuscarMenuItem = new MenuItem(getTitleSS(), new OpenPageCommand(UILoader.BUSCAR_SOLICITUD));
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
		mainMenu.addSeparator();
		sucursalMenuItem = new MenuItem("Configurar Sucursal", new OpenPageCommand(UILoader.CAMBIAR_SUCURSAL));
		mainMenu.addItem(sucursalMenuItem);
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		menuButton.setVisible(!visible);
	}

	public void enableMenuItems(int items) {
		//MGR - Integracion
		String customerCode = null;
		Boolean vieneDeNexus = false;
		if(ClientContext.getInstance().vengoDeNexus()){
			vieneDeNexus = true;
			customerCode = ClientContext.getInstance().getClienteNexus().getCustomerCode();
		}
		
		if(vieneDeNexus && customerCode == null){
			cuentasMenuItem.setVisible(false);
		}else{
			cuentasMenuItem.setVisible((items & MENU_CUENTA) != 0);
		}
		
		cuentasBuscarMenuItem.setVisible((items & MENU_CUENTA_BUSCAR) != 0);
		cuentasAgregarMenuItem.setVisible((items & MENU_CUENTA_AGREGAR) != 0);
		
		//MGR - Integracion
		if(vieneDeNexus && customerCode != null){
			agregarProspectMenuItem.setVisible(false);
		}else{
			agregarProspectMenuItem.setVisible((items & MENU_PROSPECT) != 0);
		}
		
		//MGR - Integracion
		if( (vieneDeNexus && customerCode == null) || !vieneDeNexus){
			menuItemCrearSS.setVisible(false);
		}else{
			menuItemCrearSS.setVisible((items & MENU_CREAR_SS) != 0);
		}
		
		//MGR - #1397
		if( (vieneDeNexus && customerCode == null) || !vieneDeNexus) { //LF#3|| ClientContext.getInstance().getVendedor().getTipoVendedor().getCodigo().equals("ADM")){
			ssBuscarMenuItem.setVisible((items & MENU_SOLICITUD) != 0);
		}else{
			ssBuscarMenuItem.setVisible(false);
		}
		
		verazMenuItem.setVisible((items & MENU_VERAZ) != 0);
		oppBuscarMenuItem.setVisible((items & MENU_OPORTUNIDADES) != 0);
		
		//MGR - Integracion
		if(vieneDeNexus && customerCode != null){
			opEnCursoMenuItem.setVisible(false);
		}else{
			opEnCursoMenuItem.setVisible((items & MENU_OP_EN_CURSO) != 0);
		}
	}
	
	//MGR - Integracion
	//Este metodo crea las opciones del menu "Crear SS" que corresponda para el usuario logeado
	private void crearMenuSS(MenuBar menuBarCrearSS){
		
		String url;
		String customerCode = null;
		Long customerId = null;
		MenuItem item;
		
		if(ClientContext.getInstance().vengoDeNexus()){
			customerCode = ClientContext.getInstance().getClienteNexus().getCustomerCode();
			String aux = ClientContext.getInstance().getClienteNexus().getCustomerId();
			if(aux != null)
				customerId = Long.valueOf(aux);
		}
		
		List<GrupoSolicitudDto> grupos = ClientContext.getInstance().getVendedor().getTipoVendedor().getGrupos();
		
		for(int i=0; i < grupos.size(); i++){
			GrupoSolicitudDto grup = grupos.get(i);
			url = this.getEditarSSUrl(grup.getId(), customerCode, customerId);
			item = new MenuItem(grup.getDescripcion(), new OpenPageCommand(UILoader.AGREGAR_SOLICITUD,url));
			menuBarCrearSS.addItem(item);
		}
	}
	
	private String getEditarSSUrl(Long idGrupo, String codigoVanvite,Long idCuenta) {
		StringBuilder builder = new StringBuilder();
		if (codigoVanvite != null)
			builder.append(EditarSSUI.CODIGO_VANTIVE + "=" + codigoVanvite + "&");
		if (idCuenta != null && idCuenta > 0)
			builder.append(EditarSSUI.ID_CUENTA + "=" + idCuenta + "&");
		if (idGrupo != null)
			builder.append(EditarSSUI.ID_GRUPO_SS + "=" + idGrupo);
		return builder.toString();
	}
	

	//LF#3
//	/**
//	 * Obtengo el titulo de la pesta�a de Solicitud de Servicio. Si el usuario no es analista de creditos devuelve SS. 
//	 * Si el usuario es analista de creditos y cliente de nexus devuel "SS Cliente", si no es cliente retorna "Buscar SS". 
//	 * @return String
//	 */
//	private String getTitleSS(){
//		if(ClientContext.getInstance().getVendedor().isADMCreditos()) {
//			if(ClientContext.getInstance().vengoDeNexus() && ClientContext.getInstance().soyClienteNexus()){
//				return Sfa.constant().ssClienteTitle();
//			} else {
//				return Sfa.constant().buscarSSTitle();
//			}
//		}		
//		return Sfa.constant().ssTitle();
//	}
}
