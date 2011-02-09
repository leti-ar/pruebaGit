package ar.com.nextel.sfa.client.infocom;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.dto.VendedorDto;
import ar.com.nextel.sfa.client.enums.PermisosEnum;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.RazonSocialClienteBar;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class VerInfocomUI extends ApplicationUI {

	private long cuentaID;
	private static VerInfocomUI instance = new VerInfocomUI();
	private FormButtonsBar footerBar;
	private SimpleLink cancelarLink;
	InfocomUI infocomUI;
	RazonSocialClienteBar razonSocialClienteBar;
	
	
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
		razonSocialClienteBar = new RazonSocialClienteBar();
		VendedorDto vendedor = ClientContext.getInstance().getVendedor();
		if(vendedor.isTelemarketing()||vendedor.isDealer()||vendedor.isEECC()||vendedor.isLap()||vendedor.isADMCreditos()||vendedor.isAP()){
			razonSocialClienteBar.setEnabledSilvioSoldan();
		} else {
			razonSocialClienteBar.setDisabledSilvioSoldan();
		}
		footerBar = new FormButtonsBar();
		infocomUI = InfocomUI.getInstance();
		//Agrega la barra de razon social y cliente
		mainPanel.add(razonSocialClienteBar);
		//razonSocialClienteBar.setDisabledSilvioSoldan();
		infocomUI.addStyleName("fondoGris");
		cancelarLink = new SimpleLink(Sfa.constant().cancelar(), "#", true);
		cancelarLink.addClickListener(new ClickListener() {
			public void onClick (Widget arg0) {
				cancelar();			
			}
		});
		footerBar.addLink(cancelarLink);
	}

	private void cancelar() {
		MessageDialog.getInstance().hide();
		History.newItem("");
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
		mainPanel.add(getFooter());
		return true;
	}
	
	public FormButtonsBar getFooter() {
		return footerBar;
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
