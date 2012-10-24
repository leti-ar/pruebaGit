package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.configurarsucursal.CambiarSucursalUI;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.cuenta.AgregarCuentaUI;
import ar.com.nextel.sfa.client.cuenta.BuscarCuentaUI;
import ar.com.nextel.sfa.client.cuenta.EditarCuentaUI;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.infocom.VerInfocomUI;
import ar.com.nextel.sfa.client.operaciones.OperacionEnCursoUI;
import ar.com.nextel.sfa.client.oportunidad.BuscarOportunidadUI;
import ar.com.nextel.sfa.client.ss.BuscarSSCerradaUI;
import ar.com.nextel.sfa.client.ss.BuscarSSAnalistaCreditosUI;
import ar.com.nextel.sfa.client.ss.EditarSSUI;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.veraz.VerazUI;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Este es un contenedor simple para cargar diferentes modulos de la aplicación. Se basa en un SimplePanel que
 * carga widgets AplicationUI (los cuales ya conoce) y tambien se encarga de la visibilidad del menu
 * principal. Además, al centralizar el manejo de modulos permite, a futuro, una mejor admiistracion de
 * memoria.
 * 
 * @author jlgperez
 * 
 */
public class UILoader extends SimplePanel implements ValueChangeHandler<String> {

	private static UILoader pageLoader;
	private static ApplicationUI[] pages;

	public final static int SOLO_MENU = -1;
	public final static int BUSCAR_CUENTA = 0;
	public final static int AGREGAR_CUENTA = 9;
	//MGR - Integracion
	public final static int AGREGAR_PROSPECT = 1;
	public final static int CREAR_SS = 10;
	
	public static final int BUSCAR_SOLICITUD = 2;
	public static final int AGREGAR_SOLICITUD = 6;
	public static final int VERAZ = 3;
	public static final int BUSCAR_OPP = 4;
	public final static int OP_EN_CURSO = 5;
	public final static int EDITAR_CUENTA = 7;
	public final static int VER_INFOCOM = 8;
	public final static int VER_SOLICITUD = 10;
    public final static int CAMBIAR_SUCURSAL= 12;
	
	private String lastToken = "";

	private UILoader() {
		//MGR - Integracion
		//pages = new ApplicationUI[9];
		pages = new ApplicationUI[13];
		
		History.addValueChangeHandler(this);
		History.fireCurrentHistoryState();
	}

	public static UILoader getInstance() {
		if (pageLoader == null) {
			pageLoader = new UILoader();
		}
		return pageLoader;
	}

	public void setPage(int page) {
		setPage(page, "" + page);
	}

	/**
	 * Oculta la página anterior (Si es que puede), muestra una nueva y oculta el menu.
	 * 
	 * @param page
	 *            Código de la página que desea mostrar.
	 * @param token
	 */
	public void setPage(int page, String historyToken) {
		if (!checkPermission(page)) {
			ErrorDialog.getInstance().show("No posee permisos para acceder a esta funcionalidad.", false);
			History.newItem(lastToken, false);
			return;
		}
		if (getWidget() != null) {
			// Si no puede descargar la página anterior, vueve a poner el último token
			if (!((ApplicationUI) getWidget()).unload(historyToken)) {
				History.newItem(lastToken, false);
				return;
			}
		}
		lastToken = historyToken;
		if (page == SOLO_MENU) {
			// SFAWeb.getHeaderMenu().setVisible(true);
			setWidget(null);
			return;
		}
		// SFAWeb.getHeaderMenu().setVisible(false);
		if (pages[page] == null) {
			createPageWidget(page);
		}
		// if(pages[page] != null){
		setWidget(pages[page]);
		pages[page].loadApplication();
		// } else {
		// GWT.log("Page does not exsist. Check PageLoader.createPageWidget()", null);
		// }
	}

	/**
	 * Crea el widget de una pagina particular
	 * 
	 * @param pageCode
	 *            Código de la página que desea crear.
	 */
	private void createPageWidget(int pageCode) {
		switch (pageCode) {
		case BUSCAR_CUENTA:
			pages[BUSCAR_CUENTA] = new BuscarCuentaUI();
			break;
		case AGREGAR_CUENTA:
			pages[AGREGAR_CUENTA] = AgregarCuentaUI.getInstance();
			break;
		case BUSCAR_SOLICITUD:
//		LF#3
//		if(ClientContext.getInstance().getVendedor().isADMCreditos()) {
//				pages[BUSCAR_SOLICITUD] = new BuscarSSAnalistaCreditosUI();
//			} else {
				pages[BUSCAR_SOLICITUD] = new BuscarSSCerradaUI();	
//			}
			break;
		//MGR - Integracion
		case AGREGAR_PROSPECT:
			pages[AGREGAR_PROSPECT] = AgregarCuentaUI.getInstance();
			break;
			
		case AGREGAR_SOLICITUD:
			pages[AGREGAR_SOLICITUD] = new EditarSSUI(true);
			break;
		case VERAZ:
			pages[VERAZ] = new VerazUI();
			break;
		case BUSCAR_OPP:
			pages[BUSCAR_OPP] = new BuscarOportunidadUI();
			break;
		case OP_EN_CURSO:
			pages[OP_EN_CURSO] = new OperacionEnCursoUI();
			break;
		case EDITAR_CUENTA:
			pages[EDITAR_CUENTA] = new EditarCuentaUI();
			break;
		case VER_INFOCOM:
			pages[VER_INFOCOM] = new VerInfocomUI();
			break;
		case VER_SOLICITUD:
			pages[VER_SOLICITUD] = new EditarSSUI(false);
			break;	
		case CAMBIAR_SUCURSAL:
			pages[CAMBIAR_SUCURSAL] = new CambiarSucursalUI();
			break;	
		default:
			GWT.log("Page not found. Check PageLoader.createPageWidget()", null);
			break;
		}
	}

	private boolean checkPermission(int pageCode) {
		boolean authorized;
		switch (pageCode) {
		case BUSCAR_CUENTA:
			authorized = ClientContext.getInstance().checkPermiso(
					PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_BUTTON_MENU.getValue())
						&& ClientContext.getInstance().checkPermiso(
								PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_BUSCAR_MENU.getValue());
			break;
		case AGREGAR_CUENTA:
			authorized = ClientContext.getInstance().checkPermiso(
					PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_BUTTON_MENU.getValue())
						&& ClientContext.getInstance().checkPermiso(
								PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_AGREGAR_MENU.getValue());
			break;
		
		//MGR - Integracion
		case AGREGAR_PROSPECT:
			authorized = ClientContext.getInstance().checkPermiso(
					PermisosEnum.ROOTS_MENU_PANEL_AGREGAR_PROSPECT.getValue());
			break;
		case CREAR_SS:
			authorized = ClientContext.getInstance().checkPermiso(
					PermisosEnum.CREAR_NUEVA_SS.getValue());
			break;
			
		case BUSCAR_SOLICITUD:
				authorized = ClientContext.getInstance().checkPermiso(
						PermisosEnum.ROOTS_MENU_PANEL_SS_BUTTON.getValue());
			break;
		case AGREGAR_SOLICITUD:
			authorized = ClientContext.getInstance().checkPermiso(PermisosEnum.CREAR_NUEVA_SS.getValue());
			break;
		case VERAZ:
			authorized = ClientContext.getInstance().checkPermiso(
					PermisosEnum.ROOTS_MENU_PANEL_VERAZ_BUTTON.getValue());
			break;
		case BUSCAR_OPP:
				authorized = ClientContext.getInstance().checkPermiso(
						PermisosEnum.ROOTS_MENU_PANEL_BUSQUEDA_OPORTUNIDADES_BUTTON.getValue());
			break;
		case OP_EN_CURSO:
			authorized = ClientContext.getInstance().checkPermiso(
					PermisosEnum.ROOTS_MENU_PANEL_OPERACIONES_EN_CURSO_BUTTON.getValue());
			break;
		case EDITAR_CUENTA:
			//MGR - Integracion
//				authorized = ClientContext.getInstance().checkPermiso(
//						PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_BUTTON_MENU.getValue())
//						&& ClientContext.getInstance().checkPermiso(
//								PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_AGREGAR_MENU.getValue());
			authorized = ClientContext.getInstance().checkPermiso(
							PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_EDITAR.getValue());
			break;
		case VER_INFOCOM:
			authorized = true;
			break;
		default:
			authorized = true;
			break;
		}
		return authorized;
	}

	public void onValueChange(ValueChangeEvent<String> valueChangeEvent) {
		String historyToken = valueChangeEvent.getValue();
		String token = HistoryUtils.getToken(historyToken);
		if (token == null || "".equals(token)) {
			setPage(SOLO_MENU, historyToken);
		} else {
			int nToken = Integer.parseInt(token);
			setPage(nToken, historyToken);
		}
	}
}
