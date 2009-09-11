package ar.com.nextel.sfa.client.oportunidad;

import java.util.Date;

import ar.com.nextel.sfa.client.widget.ApplicationUI;
import ar.com.snoop.gwt.commons.client.util.DateUtil;

/**
 * 
 * @author mrial
 *
 */
public class BuscarOportunidadUI extends ApplicationUI {

	private BuscarOportunidadFilterUI buscarOportunidadFilterForm;
	private BuscarOportunidadResultUI buscarOportunidadResultPanel;

	public BuscarOportunidadUI() {
		super();
	}

	public void firstLoad() {
		buscarOportunidadFilterForm = new BuscarOportunidadFilterUI();
		buscarOportunidadResultPanel = new BuscarOportunidadResultUI();
		buscarOportunidadFilterForm.setBuscarOportunidadResultPanel(buscarOportunidadResultPanel);

		mainPanel.add(buscarOportunidadFilterForm);
		mainPanel.add(buscarOportunidadResultPanel);
		mainPanel.addStyleName("gwt-central-panel");
	}
	
	public boolean load() {		
		buscarOportunidadFilterForm.cleanAndEnableFields();
		buscarOportunidadResultPanel.setVisible(false);
		//formButtonsBar.setVisible(false);
		return true;
	}

	public boolean unload(String token) {
		return true;
	}
}
