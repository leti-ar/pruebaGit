package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.widget.ApplicationUI;

/**
 * Esta página contiene el formulario de busquedas de SS cerradas, la tabla de
 * resultados y la tabla informativa de cambios.
 * 
 * @author juliovesco
 * 
 */
public class BuscarSSCerradaUI extends ApplicationUI {

	private BuscarSSCerradasFilterUI buscadorSSCerradasFilterForm;
	private BuscarSSCerradasResultUI buscarSSCerradasResultPanel;
	private BuscarSSTotalesResultUI buscarSSTotalesResultUI;
	private CambiosSSCerradasResultUI cambiosSSCerradasResultUI;

	public BuscarSSCerradaUI() {
		super();
	}

	public void firstLoad() {
		buscadorSSCerradasFilterForm = new BuscarSSCerradasFilterUI();
		buscarSSCerradasResultPanel = new BuscarSSCerradasResultUI();
		buscarSSTotalesResultUI = new BuscarSSTotalesResultUI();
		cambiosSSCerradasResultUI = new CambiosSSCerradasResultUI();
		buscarSSTotalesResultUI.setVisible(false);
		buscadorSSCerradasFilterForm.setBuscarCuentaResultPanel(buscarSSCerradasResultPanel);
		buscarSSCerradasResultPanel.setBuscarSSTotalesResultUI(buscarSSTotalesResultUI);
		cambiosSSCerradasResultUI.setVisible(false);
		buscarSSCerradasResultPanel.setCambiosSSCerradasResultUI(cambiosSSCerradasResultUI);
		
		mainPanel.add(buscadorSSCerradasFilterForm);
		mainPanel.add(buscarSSCerradasResultPanel);
		mainPanel.add(buscarSSTotalesResultUI);
		mainPanel.add(cambiosSSCerradasResultUI);
		mainPanel.addStyleName("gwt-central-panel");
	}
	
	public boolean load() {
		return true;
	}

	public boolean unload(String token) {
		return true;
	}
}
