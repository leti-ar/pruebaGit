package ar.com.nextel.sfa.client.cuenta;

import ar.com.nextel.sfa.client.widget.ApplicationUI;

/**
 * Esta página contiene el formulario de busquedas de cuentas (BuscarCuentaFilterForm) y la tabla de
 * resultados (BuscarCuentaResultPanel)
 * 
 * @author jlgperez
 * 
 */
public class BuscarCuentaUI extends ApplicationUI implements BuscarCuentaController{

	protected boolean firstLoad = true;
	private BuscarCuentaFilterUI buscadorCuentaFilterForm;
	private BuscarCuentaResultUI buscarCuentaResultPanel;

	public BuscarCuentaUI() {
		super();
	}

	public void load() {
		if (firstLoad) {
			firstLoad = false;
			buscadorCuentaFilterForm = new BuscarCuentaFilterUI(this);
			buscarCuentaResultPanel = new BuscarCuentaResultUI(this);
			buscadorCuentaFilterForm.setBuscarCuentaResultPanel(buscarCuentaResultPanel);

			mainPanel.add(buscadorCuentaFilterForm);
			mainPanel.add(buscarCuentaResultPanel);
			mainPanel.addStyleName("gwt-central-panel");
		}
	}

	public void unload() {

	}

	public void searchCuentas(BuscarCuentaFilterUIData buscadorCuentasFilterEditor) {
		buscarCuentaResultPanel.searchCuentas(buscadorCuentasFilterEditor.getCuentaSearch());
	}
}
