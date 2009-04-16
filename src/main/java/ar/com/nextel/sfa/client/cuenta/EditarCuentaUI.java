package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.widget.ApplicationUI;

public class EditarCuentaUI extends ApplicationUI {

	public EditarCuentaUI() {
		super();
	}
	
	public void load() {
       mainPanel.add(CuentaEdicionTabPanel.getInstance().getCuentaEdicionPanel());    
	}

	public void unload() {
	}

}
