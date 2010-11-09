package ar.com.nextel.sfa.client.veraz;

import ar.com.nextel.sfa.client.context.ClientContext;
import ar.com.nextel.sfa.client.enums.SexoEnum;
import ar.com.nextel.sfa.client.enums.TipoDocumentoEnum;
import ar.com.nextel.sfa.client.widget.ApplicationUI;

/**
 * 
 * @author mrial
 * 
 */

public class VerazUI extends ApplicationUI {

	private VerazFilterUI verazForm;
	private VerazResultUI verazResultUI;

	public VerazUI() {
		super();
	}

	public void firstLoad() {
		verazForm = new VerazFilterUI();
		verazResultUI = new VerazResultUI();
		verazForm.setVerazResultUI(verazResultUI);
		//MGR - #960
		if( (ClientContext.getInstance().vengoDeNexus() && !ClientContext.getInstance().soyClienteNexus())
				|| !ClientContext.getInstance().vengoDeNexus()){
			mainPanel.add(verazForm);
		}
 
		mainPanel.add(verazResultUI);
		//MGR - #960
		if( (ClientContext.getInstance().vengoDeNexus() && !ClientContext.getInstance().soyClienteNexus())
				|| !ClientContext.getInstance().vengoDeNexus()){
			mainPanel.add(verazForm.getFooter());
		}
		mainPanel.addStyleName("gwt-central-panel");
	}

	public boolean load() {
		verazResultUI.setVisible(false);
		
		//MGR - #960
		if(ClientContext.getInstance().vengoDeNexus() && ClientContext.getInstance().soyClienteNexus()){
			verazResultUI.searchVeraz(ClientContext.getInstance().getClienteNexus().getCustomerCode());
		}else{
			verazForm.getVerazEditor().getNumeroDocTextBox().setText("");
			verazForm.getVerazEditor().getTipoDocListBox().selectByValue(TipoDocumentoEnum.DNI.toString());
			verazForm.getVerazEditor().getSexoListBox().selectByText(SexoEnum.MASCULINO.getDescripcion());
		}
		return true;
	}

	public boolean unload(String token) {
		return true;
	}

}
