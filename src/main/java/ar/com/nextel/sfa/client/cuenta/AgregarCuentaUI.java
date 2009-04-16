package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.NextelDialog;

import com.google.gwt.user.client.ui.Widget;

public class AgregarCuentaUI extends ApplicationUI {

	protected boolean firstLoad = true;
    private static AgregarCuentaUI instance = new AgregarCuentaUI();;
    NextelDialog buscadorDocumentoPopup;
    
	private AgregarCuentaUI() {
		super();
		init();
	}
	
	public static AgregarCuentaUI getInstance() {
		return instance;
	}

	public void init() {
//		if (firstLoad) {
			buscadorDocumentoPopup = new BuscadorDocumentoPopup(Sfa.constant().agregar());
			//mainPanel.add(CuentaEdicionTabPanel.getInstance().getCuentaEdicionPanel());
//      }
	}
	public void load() {
		buscadorDocumentoPopup.showAndCenter();
	}
	public void unload() {

	}

}
