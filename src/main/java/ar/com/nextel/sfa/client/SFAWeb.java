package ar.com.nextel.sfa.client;

import java.util.HashMap;

import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.ClienteNexusDto;
import ar.com.nextel.sfa.client.dto.UserCenterDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.util.HistoryUtils;
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
		
		//MGR - #1050
		cargarInstanciasConocidas();
		
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

		items = cc.getVendedor().isTelemarketing() ? items
				+ HeaderMenu.MENU_CUENTA : items;
		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_BUSCAR_MENU.getValue()) ? items
				+ HeaderMenu.MENU_CUENTA_BUSCAR : items;
		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_CUENTAS_AGREGAR_MENU.getValue()) ? items
				+ HeaderMenu.MENU_CUENTA_AGREGAR : items;
		
		//MGR - Integracion
		items = cc.getVendedor().isTelemarketing() ? items
				+ HeaderMenu.MENU_PROSPECT : items;
		items = cc.getVendedor().isTelemarketing() ? items
				+ HeaderMenu.MENU_CREAR_SS : items;
		

		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_SS_BUTTON.getValue()) ? items
				+ HeaderMenu.MENU_SOLICITUD : items;
		items = cc.getVendedor().isTelemarketing() ? items
				+ HeaderMenu.MENU_VERAZ : items;
		items = cc.checkPermiso(PermisosEnum.ROOTS_MENU_PANEL_BUSQUEDA_OPORTUNIDADES_BUTTON.getValue()) ? items
				+ HeaderMenu.MENU_OPORTUNIDADES : items;
		items = cc.getVendedor().isTelemarketing() ? items
				+ HeaderMenu.MENU_OP_EN_CURSO : items;
		headerMenu.enableMenuItems(items);
	}

	private void cargarMenuConDatosUserCenter() {
		UserCenterRpcService.Util.getInstance().getUserCenter(new DefaultWaitCallback<UserCenterDto>() {
			public void success(UserCenterDto result) {
				setDatosUsuario((UserCenterDto) result);
				//MGR - #1044
				//addHeaderMenu();
			}
		});
	}

	private void cargarMenuConDevUserData() {
		UserCenterRpcService.Util.getInstance().getDevUserData(new DefaultWaitCallback<UserCenterDto>() {
			public void success(UserCenterDto result) {
				setDatosUsuario(result);
				//MGR - #1044
				//addHeaderMenu();
			}
		});
	}
	
	//MGR - #1044
	//Se encarga de buscar la cuenta que le viene desde Nexus y hace la replicacion a SFA si corresponde
	private void cargarCuentaVantive(){
		Boolean vieneDeNexus = Boolean.valueOf(HistoryUtils.getParam("vieneDeNexus"));
		if(vieneDeNexus){
			ClienteNexusDto clienteNexus = new ClienteNexusDto();
			String customerCode = HistoryUtils.getParam("customerCode");
			clienteNexus.setCustomerCode(customerCode);
			clienteNexus.setVieneDeNexus(vieneDeNexus);
			ClientContext.getInstance().setClienteNexus(clienteNexus);
			
			//MGR - Si llega un codigo de cliente, busco el numero de su cuenta es SFA
			if(customerCode != null){
				CuentaRpcService.Util.getInstance().selectCuenta(clienteNexus.getCustomerCode(), 
						new DefaultWaitCallback<Long>() {
					public void success(Long IdCuenta) {
						ClientContext.getInstance().getClienteNexus().setCustomerId(IdCuenta.toString());
						addHeaderMenu();
					}

					public void failure(Throwable caught) {
						addHeaderMenu();
						ErrorDialog.getInstance().show(caught);
					}
				});
			}else{
				addHeaderMenu();
			}
		}else{
			ClientContext.getInstance().setClienteNexus(null);
			addHeaderMenu();
		}
	}
	
	private void setDatosUsuario(UserCenterDto userCenter) {
		ClientContext.getInstance().setUsuario(userCenter.getUsuario());
		ClientContext.getInstance().setMapaPermisos(userCenter.getMapaPermisos());
		ClientContext.getInstance().setVendedor(userCenter.getVendedor());
		//MGR - #1044
		cargarCuentaVantive();
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

	//MGR - #1050
	/**
	 * Este metodo se encarga de cargar todas las instancias de la tabla SFA_KNOWNINSTANCE_ITEM
	 */
	private void cargarInstanciasConocidas() {
		
		UserCenterRpcService.Util.getInstance().getKnownInstance(new DefaultWaitCallback<HashMap<String, Long>>() {
			public void success(HashMap<String, Long> result) {
				ClientContext.getInstance().setKnownInstance(result);
			}
		});
	}
}
