package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.infocom.InfocomUI;

import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author eSalvador
 **/
public class CuentaInfocomForm extends SimplePanel {

	private static InfocomUI infocomUI = InfocomUI.getInstance();
	private static CuentaInfocomForm instance = new CuentaInfocomForm();
	private String idCuenta;

	public static CuentaInfocomForm getInstance() {
		if (instance == null) {
			instance = new CuentaInfocomForm();
		}
		return instance;
	}

//	private CuentaInfocomForm() {
//		init();
//	}

//	private void init() {
//		infocomUI.firstLoad();
//		setWidget(infocomUI);
//	}

	public void load() {
		infocomUI.loadApplication();
		//infocomUI.load();
		setWidget(infocomUI);
	}

//	public String getIdCuenta() {
//		return idCuenta;
//	}
//
//	public void setIdCuenta(String idCuenta) {
//		this.idCuenta = idCuenta;
//	}

}
