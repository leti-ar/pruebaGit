package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.infocom.InfocomUI;
import ar.com.nextel.sfa.client.util.HistoryUtils;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author eSalvador
 **/
public class CuentaInfocomForm extends Composite {

	private InfocomUI infocomUI;
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
		//infocomUI = new InfocomUI();
		infocomUI = InfocomUI.getInstance();
		Long cuentaID = Long.parseLong(HistoryUtils.getParam("cuenta_id"));
		infocomUI.setIdCuenta(cuentaID.toString());
		infocomUI.getInstance().firstLoad();
		initWidget(infocomUI);
	}

	public void load() {
		InfocomUI.getInstance().setIdCuenta(idCuenta);
		InfocomUI.getInstance().load();
	}

	public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}

}
