package ar.com.nextel.sfa.client;

import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.UserCenterDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.widget.HeaderMenu;
import ar.com.nextel.sfa.client.widget.LoadingModalDialog;
import ar.com.nextel.sfa.client.widget.LoadingPopup;
import ar.com.nextel.sfa.client.widget.UILoader;
import ar.com.snoop.gwt.commons.client.service.CallListener;
import ar.com.snoop.gwt.commons.client.service.CallListenerCollection;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;
import ar.com.snoop.gwt.commons.client.window.WaitWindow;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point principal de la aplicación web SFA Revolution
 */
public class SFAWeb implements EntryPoint {

	private static HeaderMenu headerMenu;
	private static LoadingPopup loadingPopup;

	private boolean usarUserCenter = true;

	public void onModuleLoad() {
		ErrorDialog.getInstance().setDialogTitle(ErrorDialog.AVISO);
		loadingPopup = new LoadingPopup();
		WaitWindow.callListenerCollection = new CallListenerCollection();
		WaitWindow.callListenerCollection.add(new CargandoBigPanelListener());
		if (usarUserCenter) {
			cargarMenuConDatosUserCenter();
		} else {
			cargarMenuConDevUserData();
		}
	}

	private void addHeaderMenu() {
		SFAWeb.headerMenu = new HeaderMenu();
		setPermisosMenu(SFAWeb.headerMenu);
		RootPanel.get().add(SFAWeb.headerMenu);
		RootPanel.get().add(UILoader.getInstance());
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				ErrorDialog.getInstance().show(e);
			}
		});
	}

	private void setPermisosMenu(HeaderMenu headerMenu) {
		int items = 0;
		ClientContext cc = ClientContext.getInstance();
		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_BUTTON_MENU.getValue()) ? items
				+ HeaderMenu.MENU_CUENTA : items;
		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_BUSCAR_MENU.getValue()) ? items
				+ HeaderMenu.MENU_CUENTA_BUSCAR : items;
		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_AGREGAR_MENU.getValue()) ? items
				+ HeaderMenu.MENU_CUENTA_AGREGAR : items;
		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_SS_BUTTON.getValue()) ? items
				+ HeaderMenu.MENU_SOLICITUD : items;
		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_VERAZ_BUTTON.getValue()) ? items
				+ HeaderMenu.MENU_VERAZ : items;
		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_BUSQUEDA_OPORTUNIDADES_BUTTON.getValue()) ? items
				+ HeaderMenu.MENU_OPORTUNIDADES
				: items;
		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_OPERACIONES_EN_CURSO_BUTTON.getValue()) ? items
				+ HeaderMenu.MENU_OP_EN_CURSO : items;
		headerMenu.enableMenuItems(items);
	}

	private void cargarMenuConDatosUserCenter() {
		UserCenterRpcService.Util.getInstance().getUserCenter(new DefaultWaitCallback<UserCenterDto>() {
			public void success(UserCenterDto result) {
				setDatosUsuario((UserCenterDto) result);
				addHeaderMenu();
			}
		});
	}

	private void cargarMenuConDevUserData() {
		UserCenterRpcService.Util.getInstance().getDevUserData(new DefaultWaitCallback<UserCenterDto>() {
			public void success(UserCenterDto result) {
				setDatosUsuario(result);
				addHeaderMenu();
			}
		});
	}

	private void setDatosUsuario(UserCenterDto userCenter) {
		ClientContext.getInstance().setUsuario(userCenter.getUsuario());
		ClientContext.getInstance().setMapaPermisos(userCenter.getMapaPermisos());
		ClientContext.getInstance().setVendedor(userCenter.getVendedor());
	}

	public static HeaderMenu getHeaderMenu() {
		return headerMenu;
	}

	private class CargandoBigPanelListener implements CallListener {
		public void onFinishCall() {
			loadingPopup.hide();
		}

		public void onStartCall() {
			if (!LoadingModalDialog.getInstance().isShowing()) {
				loadingPopup.show();
				loadingPopup.center();
			}
		}
	}

}
