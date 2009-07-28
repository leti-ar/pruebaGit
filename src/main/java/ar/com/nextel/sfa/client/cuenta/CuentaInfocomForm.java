package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.infocom.InfocomUI;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * @author eSalvador 
 **/
public class CuentaInfocomForm  extends Composite {

	private FlexTable mainPanel = new FlexTable();
	private InfocomUI infocomUI;
	private static CuentaInfocomForm instance = new CuentaInfocomForm();
	
	
	public static CuentaInfocomForm getInstance() {
		return instance;
	}
	
	public CuentaInfocomForm() {
		init();
	}
	
	private void init() {
		infocomUI = new InfocomUI();
		infocomUI.firstLoad();
//		infocomUI = new InfocomUI();
		initWidget(mainPanel);
		mainPanel.setWidth("100%");
//		mainPanel.setWidget(0, 0, infocomUI.firstLoad());
//		mainPanel.add(creditoFidelizacionPanel);
//		mainPanel.add(cuentaCorrientePanel);
	}
	
//	private void createDatosInfocom() {
//		creditoFidelizacionPanel = new FidelizacionInfocomUI();
//		cuentaCorrientePanel = new CCInfocomUI();
//	}
//	
//	public void setFidelizacionPanel(FidelizacionInfocomUI fidelizacionInfocomUI) {
//		this.creditoFidelizacionPanel = fidelizacionInfocomUI;
//
//	}
//	
//	public void setCCPanel(CCInfocomUI ccInfocomUI) {
//		this.cuentaCorrientePanel = ccInfocomUI;
//
//	}
}
