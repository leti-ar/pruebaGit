package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.RazonSocialClienteBar;
import ar.com.snoop.gwt.commons.client.widget.dialog.ErrorDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.RootPanel;

public class VerInfocomUI extends ApplicationUI {

	private long cuentaID;
	private static VerInfocomUI instance = new VerInfocomUI();
	InfocomUI infocomUI;
	RazonSocialClienteBar razonSocialClienteBar = new RazonSocialClienteBar();
	
	
	public static VerInfocomUI getInstance() {
		if (instance == null) {
			instance = new VerInfocomUI();
		}
		return instance;
	}
	
	
	public VerInfocomUI() {
		super();
	}


	public void firstLoad() {
		infocomUI = InfocomUI.getInstance();
		//Agrega la barra de razon social y cliente
		mainPanel.add(razonSocialClienteBar);
		infocomUI.addStyleName("fondoGris");
	}

	public boolean load() {
		infocomUI.loadApplication();
		DeferredCommand.addCommand(new IncrementalCommand() {
			public boolean execute() {
				if(infocomUI.getRazonSocial() == null){
					return true;
				}
				// cargo la barra;
				razonSocialClienteBar.setRazonSocial(infocomUI.getRazonSocial());
				razonSocialClienteBar.setCliente(infocomUI.getIdCliente());
				return false;
			}
		});
		mainPanel.add(infocomUI);
		return true;
	}


	@Override
	public boolean unload(String token) {
		// TODO Auto-generated method stub
		return true;
	}


	public long getCuentaID() {
		return cuentaID;
	}


	public void setCuentaID(long cuentaID) {
		this.cuentaID = cuentaID;
	}

	
}
