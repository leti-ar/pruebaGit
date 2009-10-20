package ar.com.nextel.sfa.client;

import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.UserCenterDto;
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
		RootPanel.get().add(SFAWeb.headerMenu);
		RootPanel.get().add(UILoader.getInstance());
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				ErrorDialog.getInstance().show(e);
			}
		});
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
