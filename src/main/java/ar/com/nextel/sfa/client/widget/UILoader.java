package ar.com.nextel.sfa.client.widget;

import ar.com.nextel.sfa.client.SFAWeb;
import ar.com.nextel.sfa.client.cuenta.AgregarCuentaUI;
import ar.com.nextel.sfa.client.cuenta.BuscarCuentaUI;
import ar.com.nextel.sfa.client.cuenta.OperacionEnCursoUI;
import ar.com.nextel.sfa.client.oportunidad.BuscarOportunidadUI;
import ar.com.nextel.sfa.client.veraz.VerazUI;

import com.google.gwt.core.client.GWT;
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
public class UILoader extends SimplePanel {

	private static UILoader pageLoader;
	private static ApplicationUI[] pages;

	public final static int SOLO_MENU = -1;
	public final static int BUSCAR_CUENTA = 0;
	public final static int AGREGAR_CUENTA = 1;
	public static final int SOLICITUD = 2;
	public static final int VERAZ = 3;
	public static final int BUSCAR_OPP = 4;
	public final static int OP_EN_CURSO = 5;

	private UILoader() {
	}

	public static UILoader getInstance() {
		if (pageLoader == null) {
			pageLoader = new UILoader();
			pages = new ApplicationUI[7];
		}
		return pageLoader;
	}

	/**
	 * Muestra una página y oculta el menu.
	 * 
	 * @param page
	 *            Código de la página que desea mostrar.
	 */
	public void setPage(int page) {
		if (getWidget() != null) {
			((ApplicationUI) getWidget()).unload();
		}
		if (page == SOLO_MENU) {
			SFAWeb.getHeaderMenu().setVisible(true);
			setWidget(null);
			return;
		}
		SFAWeb.getHeaderMenu().setVisible(false);
		if (pages[page] == null) {
			createPageWidget(page);
		}
		// if(pages[page] != null){
		setWidget(pages[page]);
		pages[page].load();
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
			pages[AGREGAR_CUENTA] = new AgregarCuentaUI();
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
		default:
			GWT.log("Page not found. Check PageLoader.createPageWidget()", null);
			break;
		}
	}
}
