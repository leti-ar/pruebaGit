package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.widget.ApplicationUI;

/**
 * Esta página contiene el formulario de busquedas de cuentas (BuscarCuentaFilterForm) y la tabla de
 * resultados (BuscarCuentaResultPanel)
 * 
 * @author jlgperez
 * 
 */
public class BuscarSSCerradaUI extends ApplicationUI {

	protected boolean firstLoad = true;
	private BuscarSSCerradasFilterUI buscadorSSCerradasFilterForm;
	private BuscarSSCerradasResultUI buscarSSCerradasResultPanel;

	public BuscarSSCerradaUI() {
		super();
	}

	public void load() {
		if (firstLoad) {
			firstLoad = false;
			buscadorSSCerradasFilterForm = new BuscarSSCerradasFilterUI();
			buscarSSCerradasResultPanel = new BuscarSSCerradasResultUI();
			buscadorSSCerradasFilterForm.setBuscarCuentaResultPanel(buscarSSCerradasResultPanel);

			mainPanel.add(buscadorSSCerradasFilterForm);
			mainPanel.add(buscarSSCerradasResultPanel);
			mainPanel.addStyleName("gwt-central-panel");
		}
	}

	public void unload() {

	}
}
