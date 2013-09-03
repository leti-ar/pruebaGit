package ar.com.nextel.sfa.client.configurarsucursal;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.NextelDialog;



public class CambiarSucursalUI extends ApplicationUI {

	private static CambiarSucursalUI instance = new CambiarSucursalUI();;
	NextelDialog cambioDeSucursalPopup;

	public CambiarSucursalUI() {
		super();
		init();
	}

	public static CambiarSucursalUI getInstance() {
		return instance;
	}

	public void init() {
		// if (firstLoad) {
		cambioDeSucursalPopup = new CambioDeSucursalPopup(Sfa.constant().configurarSucursal());
		// mainPanel.add(CuentaEdicionTabPanel.getInstance().getCuentaEdicionPanel());
		// }
	}

	public boolean load() {
		((CambioDeSucursalPopup)cambioDeSucursalPopup).fromMenu = true;
		((CambioDeSucursalPopup)cambioDeSucursalPopup).inicializarCombo();
		cambioDeSucursalPopup.showAndCenter();
		return true;
	}

	public void firstLoad() {
	}

	public boolean unload(String token) {
		((CambioDeSucursalPopup)cambioDeSucursalPopup).cleanForm();
		return true;
	}

}