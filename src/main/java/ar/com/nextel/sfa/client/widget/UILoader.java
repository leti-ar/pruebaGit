package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.SFAWeb;
import ar.com.nextel.sfa.client.cuenta.AgregarCuentaUI;
import ar.com.nextel.sfa.client.cuenta.BuscarCuentaUI;
import ar.com.nextel.sfa.client.cuenta.EditarCuentaUI;
import ar.com.nextel.sfa.client.operaciones.OperacionEnCursoUI;
import ar.com.nextel.sfa.client.oportunidad.BuscarOportunidadUI;
import ar.com.nextel.sfa.client.ss.BuscarSSCerradaUI;
import ar.com.nextel.sfa.client.ss.EditarSSUI;
import ar.com.nextel.sfa.client.util.HistoryUtils;
import ar.com.nextel.sfa.client.veraz.VerazUI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
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
public class UILoader extends SimplePanel implements HistoryListener {

	private static UILoader pageLoader;
	private static ApplicationUI[] pages;

	public final static int SOLO_MENU = -1;
	public final static int BUSCAR_CUENTA = 0;
	public final static int AGREGAR_CUENTA = 1;
	public static final int BUSCAR_SOLICITUD = 2;
	public static final int AGREGAR_SOLICITUD = 6;
	public static final int VERAZ = 3;
	public static final int BUSCAR_OPP = 4;
	public final static int OP_EN_CURSO = 5;
	public final static int EDITAR_CUENTA = 7;

	private String lastToken = "";

	private UILoader() {
		pages = new ApplicationUI[8];
		History.addHistoryListener(this);
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
			pages[BUSCAR_SOLICITUD] = new BuscarSSCerradaUI();
			break;
		case AGREGAR_SOLICITUD:
			pages[AGREGAR_SOLICITUD] = new EditarSSUI();
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
		default:
			GWT.log("Page not found. Check PageLoader.createPageWidget()", null);
			break;
		}
	}

	public void onHistoryChanged(String historyToken) {
		String token = HistoryUtils.getToken(historyToken);
		if (token == null || "".equals(token)) {
			setPage(SOLO_MENU, historyToken);
		} else {
			int nToken = Integer.parseInt(token);
			setPage(nToken, historyToken);
		}
	}
}
