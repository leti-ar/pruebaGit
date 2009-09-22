package ar.com.nextel.sfa.client.infocom;

import java.util.Date;

import ar.com.nextel.sfa.client.constant.Sfa;
import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.nextel.sfa.client.widget.FormButtonsBar;
import ar.com.nextel.sfa.client.widget.MessageDialog;
import ar.com.nextel.sfa.client.widget.RazonSocialClienteBar;
import ar.com.snoop.gwt.commons.client.util.DateUtil;
import ar.com.snoop.gwt.commons.client.widget.SimpleLink;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
		razonSocialClienteBar.setDisabledSilvioSoldan();
		infocomUI.addStyleName("fondoGris");
		cancelarLink = new SimpleLink(Sfa.constant().cancelar(), "#", true);
		cancelarLink.addClickListener(new ClickListener() {
			public void onClick (Widget arg0) {
				cancelar();			
			}
		});
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
		footerBar = new FormButtonsBar();
		footerBar.addLink(cancelarLink);
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
