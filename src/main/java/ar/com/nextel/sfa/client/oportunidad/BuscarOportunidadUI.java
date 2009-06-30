package ar.com.nextel.sfa.client.oportunidad;

import ar.com.nextel.sfa.client.widget.ApplicationUI;

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
		return true;
	}

	public boolean unload(String token) {
		return true;
	}
}
