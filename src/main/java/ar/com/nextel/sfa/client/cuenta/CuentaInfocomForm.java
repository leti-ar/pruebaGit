package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.infocom.InfocomUI;
import ar.com.nextel.sfa.client.util.HistoryUtils;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author eSalvador
 **/
public class CuentaInfocomForm extends Composite {

	private static InfocomUI infocomUI = InfocomUI.getInstance();
	private static CuentaInfocomForm instance = new CuentaInfocomForm();
	private String idCuenta;

	public static CuentaInfocomForm getInstance() {
		if (instance == null) {
			instance = new CuentaInfocomForm();
		}
		return instance;
	}

	private CuentaInfocomForm() {
		init();
	}

	private void init() {
		infocomUI.firstLoad();
		initWidget(infocomUI);
	}

	public void load() {
		//infocomUI = InfocomUI.getInstance();
		infocomUI.load();
	}

	public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}

}
