package ar.com.nextel.sfa.client.ss;

import ar.com.nextel.sfa.client.widget.ApplicationUI;

import com.google.gwt.user.client.ui.TabPanel;

public class CrearSSUI extends ApplicationUI {

	protected boolean firstLoad = true;
	private TabPanel tabs;
	private DatosSSUI datos;
	private VariosSSUI varios;
	private CrearSSUIData crearSSUIData;

	public CrearSSUI() {
		super();
	}

	public void load() {
		if (firstLoad) {
			firstLoad = false;
			init();
		}
//		HistoryUtils
	}

	private void init() {
		tabs = new TabPanel();
		mainPanel.add(tabs);
		crearSSUIData = new CrearSSUIData();
		tabs.add(datos = new DatosSSUI(crearSSUIData), "Datos");
		tabs.add(varios = new VariosSSUI(crearSSUIData), "Varios");
	}

	public void unload() {

	}

}
