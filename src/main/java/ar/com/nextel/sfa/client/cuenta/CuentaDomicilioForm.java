package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.CuentaRpcService;
import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.dto.PersonaDto;
import ar.com.nextel.sfa.client.widget.DualPanel;
import ar.com.nextel.sfa.client.widget.TelefonoTextBox;
import ar.com.nextel.sfa.client.widget.TitledPanel;
import ar.com.snoop.gwt.commons.client.service.DefaultWaitCallback;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;


public class CuentaDomicilioForm extends Composite {

	private static CuentaDomicilioForm instance = new CuentaDomicilioForm();
	private FlexTable mainPanel;

	public static CuentaDomicilioForm getInstance() {
		return instance;
	}
	
	private CuentaDomicilioForm() {
		mainPanel    = new FlexTable();
		initWidget(mainPanel);
		mainPanel.addStyleName("gwt-BuscarCuentaResultTable");
		mainPanel.setWidget(0,0,new HTML("DOMICILIO"));
		
	}
}
