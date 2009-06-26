package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.infocom.CCInfocomUI;
import ar.com.nextel.sfa.client.infocom.FidelizacionInfocomUI;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * @author eSalvador 
 **/
public class CuentaInfocomForm  extends Composite {

	private FlexTable mainPanel = new FlexTable();
	private FidelizacionInfocomUI creditoFidelizacionPanel;
	private CCInfocomUI cuentaCorrientePanel;
	
	private static CuentaInfocomForm instance = new CuentaInfocomForm();
	
	public static CuentaInfocomForm getInstance() {
		return instance;
	}
	
	private CuentaInfocomForm() {
		initWidget(mainPanel);
		mainPanel.setWidth("100%");
		createDatosInfocom();
		mainPanel.setWidget(0,0,creditoFidelizacionPanel);
		mainPanel.setWidget(1,0,cuentaCorrientePanel);
	}
	
	private void createDatosInfocom() {
		creditoFidelizacionPanel = new FidelizacionInfocomUI();
		cuentaCorrientePanel = new CCInfocomUI();
	}
}
